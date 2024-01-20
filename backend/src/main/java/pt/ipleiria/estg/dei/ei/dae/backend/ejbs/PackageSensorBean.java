package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.compositeKeys.PackageSensorId;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorReadingsEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Stateless
public class PackageSensorBean extends AbstractBean<PackageSensorEntity>{

    @EJB
    private PackageBean packageBean;

    @EJB
    private SensorBean sensorBean;
    @EJB
    private OrderBean orderBean;

    @EJB
    private PackageSensorReadingsBean readingsBean;


    public PackageSensorBean() {
        super(PackageSensorEntity.class);
    }

    public PackageSensorEntity findByCompositeKey(Long packageId, Long sensorId) {
        TypedQuery<PackageSensorEntity> query = em.createQuery(
                "SELECT pse FROM PackageSensorEntity pse " +
                        "WHERE pse.packageEntity.id = :packageId AND pse.sensorEntity.id = :sensorId",
                PackageSensorEntity.class);
        query.setParameter("packageId", packageId);
        query.setParameter("sensorId", sensorId);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            throw new IllegalStateException("More than one PackageSensorEntity found for the composite key.");
        }
    }

    public void setSensorsToPackage(Long packageId, List<Long> sensorIds) {
        PackageEntity packageEntity = packageBean.find(packageId);
        if (packageEntity == null) {
            return;
        }

        //clear all sensors from package
        List<PackageSensorEntity> packageSensorEntities = packageEntity.getPackageSensors();
        if (packageSensorEntities != null) {
            for (PackageSensorEntity packageSensorEntity : packageSensorEntities) {
                PackageSensorId packageSensorId = new PackageSensorId(packageEntity.getId(), packageSensorEntity.getSensorEntity().getId());

                PackageSensorEntity entity = find(packageSensorId);
                if (entity != null) {
                    em.remove(entity);
                }
            }
        }

        if (sensorIds == null || sensorIds.isEmpty()) {
            return;
        }

        List<SensorEntity> sensorEntities = sensorBean.findAllById(sensorIds);
        if (sensorEntities == null) {
            return;
        }

        for (SensorEntity sensorEntity : sensorEntities) {
            PackageSensorEntity packageSensorEntity = new PackageSensorEntity(packageEntity, sensorEntity);
            create(packageSensorEntity);
        }

    }

    public List<SensorEntity> importSensorReadings(InputStream xmlInputStream) throws XMLParseException, IOException, SAXException, ParserConfigurationException {
        List<SensorEntity> sensorReadings = new ArrayList<>();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlInputStream);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Reading");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Element element = (Element) nList.item(temp);

                // Here you'd check if the sensorId and packageId match your criteria
                Long xmlSensorId = Long.parseLong(element.getElementsByTagName("sensorId").item(0).getTextContent());
                Long xmlPackageId = Long.parseLong(element.getElementsByTagName("packageId").item(0).getTextContent());
                Long xmlOrderId = Long.parseLong(element.getElementsByTagName("orderId").item(0).getTextContent());

                String xmlValue = element.getElementsByTagName("value").item(0).getTextContent();
                Date xmlTimestamp = parseTimestampFromElement(element.getElementsByTagName("timestamp").item(0).getTextContent());

                if(xmlValue == null || xmlTimestamp == null){
                    throw new XMLParseException();
                }

                PackageSensorReadingsEntity reading = new PackageSensorReadingsEntity();
                reading.setValue(xmlValue);
                reading.setRecordingTimeStamp(xmlTimestamp);
                reading.setOrderEntity(orderBean.find(xmlOrderId));
                readingsBean.createReading(reading, xmlPackageId, xmlSensorId);
            }

        return sensorReadings;
    }

    public void importCsvSensorReadings(InputStream csvInputStream) throws IOException, CsvValidationException, ParseException {
        // Wrap the InputStream with a BufferedReader to read text from the character-input stream
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvInputStream, StandardCharsets.UTF_8));
             CSVReader csvReader = new CSVReader(reader)) {

            String[] record;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while ((record = csvReader.readNext()) != null) {
                if (record.length == 5 && isNumeric(record[0]) && isNumeric(record[1]) && isNumeric(record[2])) {
                    Long sensorId = Long.parseLong(record[0].trim());
                    Long packageId = Long.parseLong(record[1].trim());
                    Long orderId = Long.parseLong(record[2].trim());
                    String value = record[3].trim();
                    Date timestamp = parseTimestampFromElement(record[4]);

                    PackageSensorReadingsEntity reading = new PackageSensorReadingsEntity();
                    reading.setValue(value);
                    reading.setRecordingTimeStamp(timestamp);
                    reading.setOrderEntity(orderBean.find(orderId));
                    readingsBean.createReading(reading, packageId, sensorId);
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


//    public void importCsvSensorReadings(InputStream csvInputStream) throws IOException, CsvException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(csvInputStream, StandardCharsets.UTF_8));
//        StringBuilder csvContent = new StringBuilder();
//
//        while (reader.readLine() != null) {
//            csvContent.append(reader.readLine()).append("\n");
//        }
//        CSVReader csvReader = new CSVReader(new StringReader(csvContent.toString()));
//        for (String[] record : csvReader.readAll()) {
//            System.out.println("CSV Record: " + String.join(", ", record));
//            Long sensorId = Long.parseLong(record[0]);
//            Long packageId = Long.parseLong(record[1]);
//            Long orderId = Long.parseLong(record[2]);
//            String value = record[3];
//            Date timestamp = parseTimestampFromElement(record[5]);
//
//            PackageSensorReadingsEntity reading = new PackageSensorReadingsEntity();
//            reading.setValue(value);
//            reading.setRecordingTimeStamp(timestamp);
//            reading.setOrderEntity(orderBean.find(orderId));
//            readingsBean.createReading(reading, packageId, sensorId);
//        }
//    }

    public Date parseTimestampFromElement(String timestampString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date xmlTimestamp = null;

        try {
            xmlTimestamp = dateFormat.parse(timestampString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xmlTimestamp;
    }

}

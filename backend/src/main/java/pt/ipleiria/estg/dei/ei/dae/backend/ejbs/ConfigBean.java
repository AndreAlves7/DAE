package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorReadingsEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.UserType;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageType;

import java.util.*;
import java.util.stream.Collectors;


import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UserEntity;

@Singleton
@Startup
public class ConfigBean {

    @EJB
    private OrderBean orderBean;

    @EJB
    private PackageBean packageBean;

    @EJB
    private ProductBean productBean;

    @EJB
    private SensorBean sensorBean;

    @EJB
    private UserBean userBean;
    @EJB
    private PackageSensorReadingsBean readingsBean;


    @PostConstruct
    public void populateDB() {
        //ORDER IS IMPORTANT

        //FIRST STEP ===========
        populateSensors();
        populateProducts();
        populateUsers();

        //SECOND STEP ===========
        populatePackages();

        populateReadings();

        //THIRD STEP ===========
        populateOrders();
    }

    private void populateUsers(){
        for (int i = 1; i <= 8; i++) {
            String username = "Consumer" + i;
            String name = "consumer" + i;
            String password = "123";
            String email = "consumer" + i + "@mail.pt";
            Consumer consumer = new Consumer(username, password, name, email);
            userBean.create(consumer);
        }

        Manufacturer manufacturer = new Manufacturer("manufacturer1", "123",
                "manufacturer1", "manufacturer@mail.pt");
        userBean.create(manufacturer);

        Operator operator = new Operator("operator1", "123",
                "operator1", "operator@mail.pt");
        userBean.create(operator);
    }

    private void populateSensors(){
        for (int i = 1; i <= 10; i++) {
            SensorEntity sensor = new SensorEntity();
            sensor.setName("Sensor" + i);

//            sensor.setPackageSensors(new ArrayList<PackageSensorEntity>());

            sensorBean.create(sensor);
        }
    }

    private void populateProducts() {
        for (int i = 1; i <= 10; i++) {
            ProductEntity product = new ProductEntity();
            product.setCode("Product" + i);
            product.setName("ProductName" + i);
            product.setDescription("ProductDescription" + i);

            product.setPhotoBase64("https://img.freepik.com/premium-vector/piece-cheese-pizza-pixel-art-style_475147-1272.jpg?w=740");

            product.setPackageEntity(Collections.singletonList(packageBean.find((long ) i)));

            productBean.create(product);
        }
    }


    private void populatePackages(){
        for (int i = 1; i <= 10; i++) {
            PackageEntity packageEntity = new PackageEntity();
            packageEntity.setCode("Package" + i);
            packageEntity.setPackageMaterial(PackageMaterialType.CARDBOARD);
            packageEntity.setPackageType(PackageType.PRIMARY);

            packageEntity.setProduct(productBean.find((long ) i));
            packageEntity.setOuterPackage(null);

            packageBean.create(packageEntity);
        }
        //Associate Sensors
        for (int i = 1; i <= 10; i++) {
            sensorBean.associatePackagesToSensor(i,packageBean.findAll().stream().map(PackageEntity::getId).collect(Collectors.toList()));
//            packageBean.associateSensorsToPackages(i,packageBean.findAll().stream().map(PackageEntity::getId).collect(Collectors.toList()));
        }
    }

    private void populateReadings(){
        for (int i = 1; i <= 20; i++) {
            PackageSensorReadingsEntity readingsEntity = new PackageSensorReadingsEntity();

            readingsEntity.setRecordingTimeStamp(new Date());

            Random random = new Random();
            int randomNumber = random.nextInt(10) + 1;
            int randomNumber2 = random.nextInt(10) + 1;
            int randomNumber3 = random.nextInt(1000) + 1;

            readingsEntity.setValue(String.valueOf(randomNumber3));

            readingsBean.createReading(readingsEntity, (long)randomNumber , (long)randomNumber2);
        }
    }

    private void populateOrders(){
        for (int i = 1; i <= 10; i++) {
            OrderEntity order = new OrderEntity();
            order.setCode("Order" + i);

            orderBean.create(order);
        }
        Map<Long, Integer> packageToRandomCount = new HashMap<>();
        Random random = new Random();

        for (int i = 1; i <= 10; i++) {
            PackageEntity packageEntity = packageBean.find((long)i);
            int randomNumber = random.nextInt(100) + 1;
            packageToRandomCount.put(packageEntity.getId(), randomNumber);
        }

        for (int i = 1; i <= 10; i++) {
//            orderBean.associatePackagesToOrder((long)i,packageToRandomCount);
            packageBean.associateOrdersToPackage((long)i,packageToRandomCount);
        }
    }
}
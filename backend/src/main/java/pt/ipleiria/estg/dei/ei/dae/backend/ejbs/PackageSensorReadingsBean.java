package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorReadingsEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class PackageSensorReadingsBean extends AbstractBean<PackageSensorReadingsEntity>{
    @EJB
    private PackageSensorBean packageSensorBean;

    public PackageSensorReadingsBean() {
        super(PackageSensorReadingsEntity.class);
    }


    public void createReading(PackageSensorReadingsEntity entity, Long packageId, Long sensorId) {

        PackageSensorEntity packageSensorEntity = packageSensorBean.findByCompositeKey(packageId, sensorId);
        if (packageSensorEntity == null) {
            throw new IllegalArgumentException("No PackageSensorEntity found for given IDs");
        }
        entity.setPackageSensorEntity(packageSensorEntity);

        super.create(entity);

    }

    public void updateReading(PackageSensorReadingsEntity entity, Long packageId, Long sensorId) {
        PackageSensorEntity packageSensorEntity = packageSensorBean.findByCompositeKey(packageId, sensorId);
        if (packageSensorEntity == null) {
            throw new IllegalArgumentException("No PackageSensorEntity found for given IDs");
        }
        entity.setPackageSensorEntity(packageSensorEntity);
        super.update(entity);
    }

    public List<PackageSensorReadingsEntity> findReadingsBySensorId(Long sensorId) {
        TypedQuery<PackageSensorReadingsEntity> query = em.createQuery(
                "SELECT reading FROM PackageSensorReadingsEntity reading " +
                        "WHERE reading.packageSensorEntity.sensorEntity.id = :sensorId",
                PackageSensorReadingsEntity.class);
        query.setParameter("sensorId", sensorId);

        return new ArrayList<>(query.getResultList());
    }


    @Override
    @Deprecated
    public void create(PackageSensorReadingsEntity entity) {
    }
    @Override
    @Deprecated
    public PackageSensorReadingsEntity update(PackageSensorReadingsEntity entity) {
        return null;
    }

    public Map<String, Double> getAverageReadingBySensorForOrder(Long orderId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<PackageSensorReadingsEntity> reading = cq.from(PackageSensorReadingsEntity.class);

        Join<PackageSensorReadingsEntity, PackageSensorEntity> packageSensorJoin = reading.join("packageSensorEntity");
        Join<PackageSensorEntity, SensorEntity> sensor = packageSensorJoin.join("sensorEntity");

        // Assuming that PackageSensorReadingsEntity has a field named 'orderEntity' that refers to the OrderEntity
        Predicate byOrderId = cb.equal(reading.get("orderEntity").get("id"), orderId);

        // Select the sensor code and the average of the reading value
        cq.multiselect(sensor.get("name").as(String.class), cb.avg(reading.get("value").as(Double.class)))
                .where(byOrderId)
                .groupBy(sensor.get("name"));

        List<Object[]> results = em.createQuery(cq).getResultList();

        // Map the results to a Map of Sensor code (String) to the average reading value
        return results.stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0],
                        result -> (Double) result[1]
                ));
    }
}

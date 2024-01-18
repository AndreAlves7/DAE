package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderPackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SensorBean extends AbstractBean<SensorEntity>{

    public SensorBean() {
        super(SensorEntity.class);
    }
    @EJB
    private PackageBean packageBean;

    @EJB
    private PackageSensorBean packageSensorBean;



    public void associatePackagesToSensor(long sensorId, List<Long> packageIds) {
        SensorEntity sensor = find(sensorId);

        if (sensor == null) {
            System.out.println("No sensor found for given ID");
            return;
        }

        List<PackageEntity> packageEntities = packageBean.findAllById(packageIds);

        // Instead of replacing the list, modify the existing one
        List<PackageSensorEntity> existingPackageSensors = sensor.getPackageSensors();
        if (existingPackageSensors == null) {
            existingPackageSensors = new ArrayList<>();
            sensor.setPackageSensors(existingPackageSensors);
        }

        for (PackageEntity packageEntity : packageEntities) {
            // Create and add new PackageSensorEntity instances to the existing list
            PackageSensorEntity packageSensor = new PackageSensorEntity();
            packageSensor.setSensorEntity(sensor);
            packageSensor.setPackageEntity(packageEntity);

            existingPackageSensors.add(packageSensor);
            packageSensorBean.create(packageSensor);
        }
    }


    public SensorEntity findByName(String name) {
        TypedQuery<SensorEntity> query = em.createQuery(
                "SELECT s FROM SensorEntity s WHERE s.name = :name", SensorEntity.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public List<SensorEntity> findAllByPackageId(Long packageId) {
        TypedQuery<SensorEntity> query = em.createQuery(
                "SELECT s FROM SensorEntity s " +
                        "JOIN PackageSensorEntity ps ON ps.sensorEntity.id = s.id " +
                        "JOIN PackageEntity package ON ps.packageEntity.id = package.id " +
                        "WHERE package.id = :packageId", SensorEntity.class);
        query.setParameter("packageId", packageId);

        return new ArrayList<>(query.getResultList());
    }
}

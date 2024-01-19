package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;

import java.util.List;

@Stateless
public class PackageSensorBean extends AbstractBean<PackageSensorEntity>{

    @EJB
    private PackageBean packageBean;

    @EJB
    private SensorBean sensorBean;


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

    public boolean setSensorsToPackage(Long packageId, List<Long> sensorIds) {
        PackageEntity packageEntity = packageBean.find(packageId);
        if (packageEntity == null) {
            return false;
        }

        //clear all sensors from package
        List<PackageSensorEntity> packageSensorEntities = packageEntity.getPackageSensors();
        if (packageSensorEntities != null) {
            for (PackageSensorEntity packageSensorEntity : packageSensorEntities) {
                delete((long)packageSensorEntity.getId().hashCode());
            }
        }

        List<SensorEntity> sensorEntities = sensorBean.findAllById(sensorIds);
        if (sensorEntities == null) {
            return false;
        }

        for (SensorEntity sensorEntity : sensorEntities) {
            PackageSensorEntity packageSensorEntity = new PackageSensorEntity(packageEntity, sensorEntity);
            create(packageSensorEntity);
        }

        return true;
    }
}

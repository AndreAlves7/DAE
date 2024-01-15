package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.persistence.TypedQuery;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;

public class SensorBean extends AbstractBean<SensorEntity>{

    @EJB
    private PackageBean packageBean;

    public SensorBean(Class<SensorEntity> entityClass) {
        super(entityClass);
    }

    @Override
    public SensorEntity update(SensorEntity entity) {
        return null;
    }

    public void associateSensor(String sensorName, Long packageId){
        PackageEntity packageEntity = packageBean.find(packageId);
        SensorEntity sensorEntity = this.findByName(sensorName);

        PackageSensorEntity packageSensorEntity = new PackageSensorEntity();
        packageSensorEntity.setSensorEntity(sensorEntity);
        packageSensorEntity.setPackageEntity(packageEntity);
        em.persist(packageSensorEntity);
    }

    public SensorEntity findByName(String name) {
        TypedQuery<SensorEntity> query = em.createQuery(
                "SELECT s FROM SensorEntity s WHERE s.name = :name", SensorEntity.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}

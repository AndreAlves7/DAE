package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;
@Stateless
public class SensorBean extends AbstractBean<SensorEntity>{

    public SensorBean() {
        super(SensorEntity.class);
    }
    @EJB
    private PackageBean packageBean;

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

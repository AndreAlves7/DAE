package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorReadingsEntity;

import java.util.ArrayList;
import java.util.List;

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
}

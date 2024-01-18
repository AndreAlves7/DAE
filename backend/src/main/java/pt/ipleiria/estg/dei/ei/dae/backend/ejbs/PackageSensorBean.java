package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;

@Stateless
public class PackageSensorBean extends AbstractBean<PackageSensorEntity>{
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
}

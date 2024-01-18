package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;

@Stateless
public class PackageSensorBean extends AbstractBean<PackageSensorEntity>{
    public PackageSensorBean() {
        super(PackageSensorEntity.class);
    }
}

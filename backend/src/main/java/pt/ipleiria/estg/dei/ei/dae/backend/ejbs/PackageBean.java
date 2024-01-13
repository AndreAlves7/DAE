package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.AbstractEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.packages.PackageEntity;

public class PackageBean extends AbstractBean<PackageEntity> {
    public PackageBean(Class<PackageEntity> entityClass) {
        super(entityClass);
    }
}

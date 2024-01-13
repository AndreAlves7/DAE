package pt.ipleiria.estg.dei.ei.dae.backend.entities.packages;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PrimarySensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.ProductEntity;

@Entity
public class PrimaryPackageEntity extends PackageEntity {

    @OneToOne
    private ProductEntity productEntity;

    @OneToOne
    private PrimarySensorEntity sensorData;


}

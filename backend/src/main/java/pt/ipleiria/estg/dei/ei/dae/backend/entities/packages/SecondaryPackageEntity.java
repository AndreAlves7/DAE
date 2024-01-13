package pt.ipleiria.estg.dei.ei.dae.backend.entities.packages;

import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SecondarySensorEntity;

import java.util.List;

@Entity
public class SecondaryPackageEntity extends PackageEntity {

    @OneToMany(mappedBy = "secondaryPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrimaryPackageEntity> packages;

    @OneToOne
    private SecondarySensorEntity sensorData;

}

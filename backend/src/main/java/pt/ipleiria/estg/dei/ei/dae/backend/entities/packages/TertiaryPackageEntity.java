package pt.ipleiria.estg.dei.ei.dae.backend.entities.packages;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.TertiarySensorEntity;

import java.util.List;

@Entity
public class TertiaryPackageEntity extends PackageEntity {

    @OneToMany(mappedBy = "tertiaryPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SecondaryPackageEntity> packages;

    @OneToOne
    private TertiarySensorEntity sensorData;
}

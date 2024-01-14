package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.AbstractEntity;

@Entity
@Getter
@Setter
public class PackageSensorReadingsEntity extends AbstractEntity {

    @ManyToOne
    private PackageSensorEntity packageSensorEntity;

    @Column
    private String value;
}

package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.AbstractEntity;

@Entity
public class PackageSensorReadings extends AbstractEntity {

    @ManyToOne
    private PackageSensor packageSensor;

    @Column
    private String value;
}

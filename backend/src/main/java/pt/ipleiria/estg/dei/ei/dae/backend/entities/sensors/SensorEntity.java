package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.AbstractEntity;

import java.util.List;

@Entity
public class SensorEntity extends AbstractEntity {
    @Column
    private String name;

    @OneToMany(mappedBy = "sensorEntity")
    private List<PackageSensor> jointable;
}

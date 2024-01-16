package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.AbstractEntity;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sensors")
public class SensorEntity extends AbstractEntity {
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "sensorEntity")
    private List<PackageSensorEntity> packageSensors;
}

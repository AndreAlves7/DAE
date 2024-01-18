package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.AbstractEntity;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sensors")
@NoArgsConstructor
@AllArgsConstructor
public class SensorEntity extends AbstractEntity {

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "sensorEntity", cascade = CascadeType.REMOVE)
    private List<PackageSensorEntity> packageSensors;

    @Override
    protected void onCreate() {

    }
}

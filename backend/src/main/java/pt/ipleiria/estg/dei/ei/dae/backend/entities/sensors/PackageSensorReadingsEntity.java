package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.AbstractEntity;

@Entity
@Getter
@Setter
@Table(name = "package_sensor_readings")
@AttributeOverride(name = "id", column = @Column(name = "reading_id"))
public class PackageSensorReadingsEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "package_sensor_id")
    private PackageSensorEntity packageSensorEntity;

    @Column
    private String value;
}

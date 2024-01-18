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
    @JoinColumns({
            @JoinColumn(name = "package_id", referencedColumnName = "package_id"),
            @JoinColumn(name = "sensor_id", referencedColumnName = "sensor_id")
    })
    private PackageSensorEntity packageSensorEntity;

    @Column
    private String value;

    @Override
    protected void onCreate() {

    }
}

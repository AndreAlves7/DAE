package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.AbstractEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;

import java.util.List;

//This is a join table for the N x M relationship between PackageEntity and SensorEntity
//Each entry here as a set of readings with history
@Entity
@Getter
@Setter
@Table(name = "package_sensor")
public class PackageSensorEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "package_id")
    private PackageEntity packageEntity;
    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private SensorEntity sensorEntity;

    @OneToMany(mappedBy = "packageSensorEntity")
    private List<PackageSensorReadingsEntity> readings;
}

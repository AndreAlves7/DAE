package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.compositeKeys.PackageSensorId;

import java.io.Serializable;
import java.util.List;

//This is a join table for the N x M relationship between PackageEntity and SensorEntity
//Each entry here as a set of readings with history
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "package_sensor_link")
@IdClass(PackageSensorId.class)
public class PackageSensorEntity implements Serializable {
    @EmbeddedId
    private PackageSensorId id;

    @Id
    @ManyToOne
    @JoinColumn(name = "package_id")
    private PackageEntity packageEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private SensorEntity sensorEntity;

    @OneToMany(mappedBy = "packageSensorEntity", cascade = CascadeType.REMOVE)
    private List<PackageSensorReadingsEntity> readings;

    public PackageSensorEntity(PackageEntity packageEntity, SensorEntity sensorEntity) {
        this.packageEntity = packageEntity;
        this.sensorEntity = sensorEntity;
        this.id = new PackageSensorId(packageEntity.getId(), sensorEntity.getId());
    }
}

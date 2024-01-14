package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.AbstractEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;

import java.util.List;

//This is a join table for the N x M relationship between PackageEntity and SensorEntity
//Each entry here as a set of readings with history
@Entity
public class PackageSensor extends AbstractEntity {

    @ManyToOne
    private PackageEntity packageEntity;
    @ManyToOne
    private SensorEntity sensorEntity;

    @OneToMany(mappedBy = "readings")
    private List<PackageSensorReadings> readings;
}

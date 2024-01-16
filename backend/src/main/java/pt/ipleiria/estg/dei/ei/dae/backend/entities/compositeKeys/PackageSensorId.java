package pt.ipleiria.estg.dei.ei.dae.backend.entities.compositeKeys;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class PackageSensorId implements Serializable {
    private Long packageEntity; // corresponds to the PK of PackageEntity
    private Long sensorEntity;  // corresponds to the PK of SensorEntity

    public PackageSensorId() {
    }

    public PackageSensorId(Long packageEntity, Long sensorEntity) {
        this.packageEntity = packageEntity;
        this.sensorEntity = sensorEntity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageSensorId that = (PackageSensorId) o;
        return Objects.equals(packageEntity, that.packageEntity) &&
                Objects.equals(sensorEntity, that.sensorEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageEntity, sensorEntity);
    }
}
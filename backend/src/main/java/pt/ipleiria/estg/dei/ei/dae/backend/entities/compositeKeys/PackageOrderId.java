package pt.ipleiria.estg.dei.ei.dae.backend.entities.compositeKeys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PackageOrderId implements Serializable {
    private Long packageEntity;
    private Long orderEntity;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageOrderId that = (PackageOrderId) o;
        return Objects.equals(packageEntity, that.packageEntity) &&
                Objects.equals(orderEntity, that.orderEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageEntity, orderEntity);
    }
}
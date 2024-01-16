package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "order_package_id"))
public class OrderPackageEntity extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "package_id")
    private PackageEntity packageEntity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @Column(name = "quantity")
    private int quantity;

    @Override
    protected void onCreate() {

    }
}

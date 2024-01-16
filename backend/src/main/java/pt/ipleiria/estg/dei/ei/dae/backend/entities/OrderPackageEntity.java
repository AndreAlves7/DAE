package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.compositeKeys.PackageOrderId;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_package_link")
@IdClass(PackageOrderId.class)
public class OrderPackageEntity implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "package_id")
    private PackageEntity packageEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @Column(name = "quantity")
    private int quantity;
}

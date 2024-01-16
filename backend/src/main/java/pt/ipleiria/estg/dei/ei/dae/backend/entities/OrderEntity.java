package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity extends AbstractEntity {

    @Column
    private String code;

    //orphanRemoval true will delete the OrderPackageEntities when you delete a normal OrderEntity
    @OneToMany(mappedBy = "orderEntity" , cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderPackageEntity> orderPackages;

    @Override
    protected void onCreate() {

    }
}

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

    @OneToMany(mappedBy = "orderEntity" , cascade = CascadeType.REMOVE)
    private List<OrderPackageEntity> orderPackages;

    @Override
    protected void onCreate() {

    }
}

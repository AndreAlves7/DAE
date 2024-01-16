package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class OrderEntity extends AbstractEntity {

    @Column
    private String code;

    @ManyToMany(mappedBy = "orders")
    private List<PackageEntity> packageEntities;
}

package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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

    @ManyToMany(mappedBy = "orders")
    private List<PackageEntity> packageEntities;
}

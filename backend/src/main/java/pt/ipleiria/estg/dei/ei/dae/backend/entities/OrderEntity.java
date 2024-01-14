package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class OrderEntity extends AbstractEntity {

    @Column
    private String code;

    @ManyToMany
    private List<PackageEntity> packageEntities;
}

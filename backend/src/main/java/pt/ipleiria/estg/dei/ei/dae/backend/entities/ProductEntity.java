package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "products")
public class ProductEntity extends AbstractEntity{

    @Column
    private String code;

    @OneToMany(mappedBy = "product")
    private List<PackageEntity> packageEntity;
}

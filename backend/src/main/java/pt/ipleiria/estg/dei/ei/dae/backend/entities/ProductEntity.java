package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class ProductEntity extends AbstractEntity{

    @Column
    private String code;

    @OneToMany
    private List<PackageEntity> packageEntity;
}

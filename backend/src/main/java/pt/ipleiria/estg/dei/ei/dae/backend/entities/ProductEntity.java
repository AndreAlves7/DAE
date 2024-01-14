package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class ProductEntity extends AbstractEntity{

    @OneToMany
    private List<PackageEntity> packageEntity;
}

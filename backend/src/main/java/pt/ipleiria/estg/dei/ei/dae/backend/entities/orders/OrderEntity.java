package pt.ipleiria.estg.dei.ei.dae.backend.entities.orders;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.AbstractEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;

import java.util.List;

@Entity
public class OrderEntity extends AbstractEntity {

    @ManyToMany
    private List<PackageEntity> packageEntities;
}

package pt.ipleiria.estg.dei.ei.dae.backend.entities.packages;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.AbstractEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageMaterialType;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PackageEntity extends AbstractEntity {

    @Column
    private int weight;
    @Column
    private int initialQuantity;
    @Column
    private int currentQuantity;
    @Enumerated(EnumType.STRING)
    private PackageMaterialType packageMaterial;
}

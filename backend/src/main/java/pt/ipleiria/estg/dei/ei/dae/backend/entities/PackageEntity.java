package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageType;

import java.util.List;

@Entity
@Getter
@Setter
public class PackageEntity extends AbstractEntity{


    @Enumerated(EnumType.STRING)
    private PackageType packageType;

    @Enumerated(EnumType.STRING)
    private PackageMaterialType packageMaterial;


}

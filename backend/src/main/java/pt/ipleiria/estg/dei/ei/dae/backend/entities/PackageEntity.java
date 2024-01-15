package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageType;

import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PackageEntity extends AbstractEntity {

    @Column
    private String code;

    @Enumerated(EnumType.STRING)
    private PackageMaterialType packageMaterial;
    @Enumerated(EnumType.STRING)
    private PackageType packageType;
    @Column
    private boolean isTransportPackage;


    @ManyToMany
    private List<OrderEntity> order;

    //If is a transport package, no product relationship will exist
    @ManyToOne
    private ProductEntity product;

    //If is not a transport package, there may be a related transport package
    //store the related package id here
    @ManyToOne
    private PackageEntity packagesForTransportEntity;

    @OneToMany(mappedBy = "packageEntity")
    private List<PackageSensorEntity> packageSensors;
}

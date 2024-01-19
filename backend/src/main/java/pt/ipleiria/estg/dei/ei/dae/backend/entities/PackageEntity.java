package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageType;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "packages")
@NoArgsConstructor
@AllArgsConstructor
public class PackageEntity extends AbstractEntity {

    @Column
    private String code;

    @Column(name = "package_material")
    @Enumerated(EnumType.STRING)
    private PackageMaterialType packageMaterial;
    @Column(name = "package_type")
    @Enumerated(EnumType.STRING)
    private PackageType packageType;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product; //If is a transport package, no product relationship will exist

    @ManyToOne
    @JoinColumn(name = "outer_package_id")
    private PackageEntity outerPackage;

    @OneToMany(mappedBy = "packageEntity", cascade = CascadeType.REMOVE)
    private List<OrderPackageEntity> orderPackages;

    @OneToMany(mappedBy = "packageEntity", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<PackageSensorEntity> packageSensors;

    @Override
    protected void onCreate() {

    }
}

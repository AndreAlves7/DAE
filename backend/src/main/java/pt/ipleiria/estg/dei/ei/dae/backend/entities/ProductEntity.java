package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends AbstractEntity{


    public ProductEntity(String code, String photoBase64) {
        this.code = code;
        this.photoBase64 = photoBase64;
    }

    @Column
    private String code;

    @Column(name = "product_base64", columnDefinition = "TEXT")
    private String photoBase64;

    @OneToMany(mappedBy = "product")
    private List<PackageEntity> packageEntity;

    @Override
    public void onCreate() {

    }
}

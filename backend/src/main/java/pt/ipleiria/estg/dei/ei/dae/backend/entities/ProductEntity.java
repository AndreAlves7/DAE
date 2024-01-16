package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "products")
public class ProductEntity extends AbstractEntity{

    @Override
    public void onCreate() {
        if (this.photoBase64 != null && this.photoBase64.length() > 0) {
           this.encodePhoto(this.photoBase64);
        }
    }

    @Column
    private String code;

    public String getPhotoBase64() {
        return decodePhoto();
    }

    @Column(name = "product_base64", columnDefinition = "TEXT")
    private String photoBase64;

    @OneToMany(mappedBy = "product")
    private List<PackageEntity> packageEntity;

    public String decodePhoto() {
        return new String(Base64.getDecoder().decode(photoBase64), StandardCharsets.UTF_8);
    }

    public void encodePhoto(String image) {
        this.photoBase64 = Base64.getEncoder().encodeToString(image.getBytes(StandardCharsets.UTF_8));
    }
}

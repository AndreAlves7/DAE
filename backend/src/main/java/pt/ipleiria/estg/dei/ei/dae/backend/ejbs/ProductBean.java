package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.ProductEntity;

import java.util.List;

@Stateless
public class ProductBean extends AbstractBean<ProductEntity>{
    public ProductBean() {
        super(ProductEntity.class);
    }

    @EJB
    private PackageBean packageBean;

    public void associateProductToPackage(Long productId, Long packageId) {
        ProductEntity product = find(productId);
        PackageEntity packageEntity = packageBean.find(packageId);

        if (product == null || packageEntity == null) {
            throw new EntityNotFoundException();
        }

        packageEntity.setProduct(product);
        product.getPackageEntity().add(packageEntity);

        packageBean.update(packageEntity);
        update(product);
    }
}

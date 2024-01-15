package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.ProductEntity;
@Stateless
public class PackageBean extends AbstractBean<PackageEntity> {
    public PackageBean() {
        super(PackageEntity.class);
    }

    @EJB
    private ProductBean productBean;

    @Override
    public PackageEntity update(PackageEntity entity) {
        return null;
    }

    public void create(PackageEntity packageEntity, Long productId){
        ProductEntity productEntity = productBean.find(productId);
        packageEntity.setProduct(productEntity);

        em.persist(packageEntity);
    }

    public PackageEntity updatePackage(PackageEntity packageEntity, Long productId) {
        ProductEntity productEntity = productBean.find(productId);
        packageEntity.setProduct(productEntity);

        return em.merge(packageEntity);
    }
}

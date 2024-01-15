package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.ProductEntity;
@Stateless
@NoArgsConstructor
public class PackageBean extends AbstractBean<PackageEntity> {
    public PackageBean(Class<PackageEntity> entityClass) {
        super(entityClass);
    }

    @Override
    public PackageEntity update(PackageEntity entity) {
        return null;
    }

    @EJB
    private ProductBean productBean;

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

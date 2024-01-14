package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.ProductEntity;

public class PackageBean extends AbstractBean<PackageEntity> {
    public PackageBean(Class<PackageEntity> entityClass) {
        super(entityClass);
    }

    @EJB
    private ProductBean productBean;

    private void createPackage(PackageEntity packageEntity, Long productId){
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
package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.ProductEntity;
@Stateless
@NoArgsConstructor
public class ProductBean extends AbstractBean<ProductEntity>{
    public ProductBean(Class<ProductEntity> entityClass) {
        super(entityClass);
    }

    @Override
    public ProductEntity update(ProductEntity entity) {
        return null;
    }
}

package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.ProductEntity;
@Stateless
public class ProductBean extends AbstractBean<ProductEntity>{
    public ProductBean() {
        super(ProductEntity.class);
    }

    @Override
    public ProductEntity update(ProductEntity entity) {
        return null;
    }
}

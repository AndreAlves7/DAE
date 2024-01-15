package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Path;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.PackageBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.ProductEntity;

@Path("products")
public class ProductService extends AbstractService<ProductEntity,ProductDTO> {

    @EJB
    protected ProductBean productBean;

    @Override
    protected AbstractBean<ProductEntity> getBean() {
        return productBean;
    }

    @Override
    protected ProductEntity convertToEntity(ProductDTO productDTO) {
        return null;
    }

    @Override
    protected ProductDTO convertToDto(ProductEntity productEntity) {
        return null;
    }

    @Override
    protected void copyDtoToEntity(ProductDTO productDTO, ProductEntity productEntity) {

    }
}

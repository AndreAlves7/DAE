package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.PackageBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.ProductEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
@PermitAll
public class ProductService extends AbstractService<ProductEntity,ProductDTO> {

    @EJB
    protected ProductBean productBean;
    @EJB
    protected PackageBean packageBean;

    @Override
    protected AbstractBean<ProductEntity> getBean() {
        return productBean;
    }

    @Override
    protected ProductEntity convertToEntity(ProductDTO productDTO) {
        return new ProductEntity(productDTO.getCode(),productDTO.getName(),productDTO.getDescription(),productDTO.getPhotoBase64());
    }

    @Override
    protected ProductDTO convertToDto(ProductEntity productEntity) {

        return new ProductDTO(productEntity.getId(),productEntity.getName(), productEntity.getDescription(),productEntity.getCode(), productEntity.getPhotoBase64(), null) ;
    }

    @Override
    public Response find(Long id) {
        return super.find(id);
    }

    @Override
    public Response findAll() {
        return super.findAll();
    }

    @Override
    @RolesAllowed({"Manufacturer", "Operator"})
    protected void copyDtoToEntity(ProductDTO productDTO, ProductEntity productEntity) {
        productEntity.setCode(productDTO.getCode());
        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setPhotoBase64(productDTO.getPhotoBase64());
    }

    @PATCH
    @RolesAllowed({"Manufacturer", "Operator"})
    public Response associatePackage(ProductDTO productDTO){
        Long productId = productDTO.getId();
        Long packageId = productDTO.getPackageId();

        try {
            productBean.associateProductToPackage(productId, packageId);
            return Response.status(Response.Status.OK).build();

        }catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.PackageBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageType;

import java.util.ArrayList;
import java.util.List;

@Path("packages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PackageService extends AbstractService<PackageEntity, PackageDTO>{

    public static final String PACKAGE_ASSOCIATION_SUCCESSFUL = "Package association successful";
    @EJB
    protected PackageBean packageBean;

    @Override
    protected AbstractBean<PackageEntity> getBean() {
        return packageBean;
    }

    @Override
    protected PackageEntity convertToEntity(PackageDTO packageDTO) {
        PackageMaterialType materialType = PackageMaterialType.valueOf(packageDTO.getMaterialType().toUpperCase());
        PackageType packageType = PackageType.valueOf(packageDTO.getPackageType().toUpperCase());

        return new PackageEntity(packageDTO.getCode(), materialType, packageType, null, null, null, null);
    }


    @Override
    protected PackageDTO convertToDto(PackageEntity packageEntity) {
        return new PackageDTO(packageEntity.getId(), null,packageEntity.getCode(), packageEntity.getPackageMaterial().getDescription(), packageEntity.getPackageType().getDescription());
    }

    @Override
    protected void copyDtoToEntity(PackageDTO packageDTO, PackageEntity packageEntity) {
        PackageMaterialType materialType = PackageMaterialType.valueOf(packageDTO.getMaterialType().toUpperCase());
        PackageType packageType = PackageType.valueOf(packageDTO.getPackageType().toUpperCase());


        packageEntity.setCode(packageDTO.getCode());
        packageEntity.setPackageMaterial(materialType);
        packageEntity.setPackageType(packageType);

    }

    @GET
    @Path("order/{id}")
    public List<PackageDTO> findAllByOrderId(@PathParam("id") Long orderId) {
        List<PackageDTO> dtos = new ArrayList<>();
        packageBean.findAllByOrderId(orderId).forEach( entity -> {
            PackageDTO sensorDTO = convertToDto(entity);
            dtos.add(sensorDTO);
        });
        return dtos;
    }

    @PATCH
    public Response associateOuterPackage(PackageDTO packageDTO) {
        Long innerPackageId = packageDTO.getId();
        Long outerPackageId = packageDTO.getOuterId();

        String result = packageBean.associateOuterPackage(innerPackageId, outerPackageId);

        // Based on the result string, determine the response
        if (result.equals(PACKAGE_ASSOCIATION_SUCCESSFUL)) {
            return Response.ok(result).build();
        } else {
            // Assuming all other cases are error cases
            return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        }
    }

}

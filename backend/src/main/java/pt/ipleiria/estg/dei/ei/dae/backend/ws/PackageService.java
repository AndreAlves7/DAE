package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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
        return new PackageDTO(packageEntity.getId(),packageEntity.getCode(), packageEntity.getPackageMaterial().getDescription(), packageEntity.getPackageType().getDescription());
    }

    @Override
    protected void copyDtoToEntity(PackageDTO packageDTO, PackageEntity packageEntity) {
        PackageMaterialType materialType = PackageMaterialType.valueOf(packageDTO.getMaterialType().toUpperCase());
        PackageType packageType = PackageType.valueOf(packageDTO.getPackageType().toUpperCase());


        packageEntity.setCode(packageDTO.getCode());
        packageEntity.setPackageMaterial(materialType);
        packageEntity.setPackageType(packageType);

    }

    @Path("order/{id}")
    @GET
    public List<PackageDTO> findAllByOrderId(@PathParam("id") Long orderId) {
        List<PackageDTO> dtos = new ArrayList<>();
        packageBean.findAllByOrderId(orderId).forEach( entity -> {
            PackageDTO sensorDTO = convertToDto(entity);
            dtos.add(sensorDTO);
        });
        return dtos;
    }

}

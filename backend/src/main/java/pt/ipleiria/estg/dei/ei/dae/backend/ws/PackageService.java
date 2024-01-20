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
import pt.ipleiria.estg.dei.ei.dae.backend.dto.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.ProductEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("packages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
@PermitAll
public class PackageService extends AbstractService<PackageEntity, PackageDTO>{

    public static final String PACKAGE_ASSOCIATION_SUCCESSFUL = "Package association successful";
    @EJB
    protected PackageBean packageBean;

    @EJB
    protected ProductBean productBean;

    @EJB
    protected PackageSensorBean packageSensorBean;

    @Override
    protected AbstractBean<PackageEntity> getBean() {
        return packageBean;
    }

    @Override
    protected PackageEntity convertToEntity(PackageDTO packageDTO) {
        PackageMaterialType materialType = PackageMaterialType.valueOf(packageDTO.getMaterialType().toUpperCase());
        PackageType packageType = PackageType.valueOf(packageDTO.getPackageType().toUpperCase());

        ProductEntity product = null;
        if (packageDTO.getProduct().getId() != null) {
          product = productBean.find(packageDTO.getProduct().getId());
        }
        return new PackageEntity(packageDTO.getCode(), materialType, packageType, product, null, null, null);
    }


    @Override
    protected PackageDTO convertToDto(PackageEntity packageEntity) {
        ProductEntity product = packageEntity.getProduct();
        ProductDTO productDTO = null;
        if(product != null){
            productDTO = new ProductDTO(product.getId(),product.getName(), product.getDescription(),product.getCode(), product.getPhotoBase64(), null) ;
        }

        List<PackageSensorEntity> sensors = packageEntity.getPackageSensors();
        //get all sensors from package
        List<SensorDTO> sensorDTOS = new ArrayList<>();
        if(sensors != null){
             sensorDTOS = sensors.stream()
                    .map(sensor -> new SensorDTO(sensor.getSensorEntity().getId(), sensor.getSensorEntity().getName()))
                    .collect(Collectors.toList());
        }

        return new PackageDTO(packageEntity.getId(), null,packageEntity.getCode(), packageEntity.getPackageMaterial().getDescription(), packageEntity.getPackageType().getDescription(), productDTO, sensorDTOS);
    }

    @Override
    protected void copyDtoToEntity(PackageDTO packageDTO, PackageEntity packageEntity) {
        PackageMaterialType materialType = PackageMaterialType.valueOf(packageDTO.getMaterialType().toUpperCase());
        PackageType packageType = PackageType.valueOf(packageDTO.getPackageType().toUpperCase());


        packageEntity.setCode(packageDTO.getCode());
        packageEntity.setPackageMaterial(materialType);
        packageEntity.setPackageType(packageType);

        if(packageDTO.getProduct().getId() != null) {
            ProductEntity product = productBean.find(packageDTO.getProduct().getId());
            packageEntity.setProduct(product);
        }

    }

    @Override
    public Response findAll() {
        return super.findAll();
    }

    @Override
    public Response find(Long id) {
        return super.find(id);
    }

//    @Override
//    @RolesAllowed({"Manufacturer", "Operator"})
//    public Response create(PackageDTO packageDTO) {
//        return super.create(packageDTO);
//    }
//
//    @Override
//    @RolesAllowed({"Manufacturer", "Operator"})
//    public Response delete(Long id) {
//        return super.delete(id);
//    }

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

    @GET
    @Path("product/{id}")
    public Response findPackagesByProducts(@PathParam("id") Long productId){

        try {
            List<PackageEntity> productPackages = packageBean.findAllByProductId(productId);

            List<PackageDTO> dtos = productPackages.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            return Response.status(Response.Status.OK).entity(dtos).build();

        } catch (PersistenceException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
//    @RolesAllowed({"Manufacturer", "Operator"})
    public Response update(Long id, PackageDTO packageDTO) {
        //add sensors to package
        List<Long> sensorIds = packageDTO.getSensors().stream()
                .map(SensorDTO::getId)
                .collect(Collectors.toList());

        packageSensorBean.setSensorsToPackage(packageDTO.getId(), sensorIds);

        return super.update(id, packageDTO);
    }
}

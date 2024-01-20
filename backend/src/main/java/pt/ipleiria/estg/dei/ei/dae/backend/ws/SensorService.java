package pt.ipleiria.estg.dei.ei.dae.backend.ws;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.PackageSensorBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.PackageSensorReadingsBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;

import javax.management.modelmbean.XMLParseException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Path("sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
@PermitAll
public class SensorService extends AbstractService<SensorEntity, SensorDTO>{

   @EJB
   protected SensorBean sensorBean;

   @EJB
   protected PackageSensorBean packageSensorBean;

   @EJB
   protected PackageSensorReadingsBean readingsBean;

    @Override
    protected AbstractBean<SensorEntity> getBean() {
        return sensorBean;
    }

    @Override
    protected SensorEntity convertToEntity(SensorDTO sensorDTO) {

        return new SensorEntity(sensorDTO.getName(), null);
    }

    @Override
    protected SensorDTO convertToDto(SensorEntity sensorEntity) {

        return new SensorDTO(sensorEntity.getId(),sensorEntity.getName()) ;
    }

    @Override
    protected void copyDtoToEntity(SensorDTO sensorDTO, SensorEntity sensorEntity) {
        sensorEntity.setName(sensorDTO.getName());
    }

    @Override
    public Response findAll() {
        return super.findAll();
    }

    @Override
    public Response find(Long id) {
        return super.find(id);
    }

    @Override
    @RolesAllowed({"Manufacturer", "Operator"})
    public Response create(SensorDTO sensorDTO) {
        return super.create(sensorDTO);
    }

    @Override
    @RolesAllowed({"Manufacturer", "Operator"})
    public Response update(Long id, SensorDTO sensorDTO) {
        return super.update(id, sensorDTO);
    }

    @Override
    @RolesAllowed({"Manufacturer", "Operator"})
    public Response delete(Long id) {
        return super.delete(id);
    }

    @GET
    @Path("packages/{id}")
    public List<SensorDTO> findAllByPackageId(@PathParam("id") Long packageId) {
        List<SensorDTO> dtos = new ArrayList<>();
         sensorBean.findAllByPackageId(packageId).forEach( entity -> {
             SensorDTO sensorDTO = convertToDto(entity);
             dtos.add(sensorDTO);
         });
        return dtos;
    }

    @POST
    @Path("readings")
    @Consumes(MediaType.APPLICATION_XML)
    @RolesAllowed({"Manufacturer", "Operator"})
    public Response importSensorReadings(InputStream xmlInputStream) {
        List<SensorDTO> sensorDTOS = new ArrayList<>();
        try {
            packageSensorBean.importSensorReadings(xmlInputStream).forEach(entity -> {
                sensorDTOS.add(convertToDto(entity));
            });
            return Response.ok(sensorDTOS).build();
        } catch (XMLParseException e) {
            // Log the exception details here
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid XML format: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            // Handle other exceptions
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error processing request: " + e.getMessage())
                    .build();
        }
    }

}

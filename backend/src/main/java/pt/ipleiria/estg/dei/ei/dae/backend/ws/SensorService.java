package pt.ipleiria.estg.dei.ei.dae.backend.ws;
import jakarta.ejb.EJB;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.PackageSensorBean;
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
public class SensorService extends AbstractService<SensorEntity, SensorDTO>{

   @EJB
   protected SensorBean sensorBean;

   @EJB
   protected PackageSensorBean packageSensorBean;

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

    @Path("packages/{id}")
    @GET
    public List<SensorDTO> findAllByPackageId(@PathParam("id") Long packageId) {
        List<SensorDTO> dtos = new ArrayList<>();
         sensorBean.findAllByPackageId(packageId).forEach( entity -> {
             SensorDTO sensorDTO = convertToDto(entity);
             dtos.add(sensorDTO);
         });
        return dtos;
    }

    @Path("readings")
    @POST
    @Consumes(MediaType.APPLICATION_XML)
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

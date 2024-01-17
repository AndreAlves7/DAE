package pt.ipleiria.estg.dei.ei.dae.backend.ws;
import jakarta.ejb.EJB;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;

import java.util.ArrayList;
import java.util.List;

@Path("sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorService extends AbstractService<SensorEntity, SensorDTO>{

   @EJB
   protected SensorBean sensorBean;

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

        return new SensorDTO(sensorEntity.getName()) ;
    }

    @Override
    protected void copyDtoToEntity(SensorDTO sensorDTO, SensorEntity sensorEntity) {

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

}

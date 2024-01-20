package pt.ipleiria.estg.dei.ei.dae.backend.ws;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;

import java.util.ArrayList;
import java.util.List;

@Path("sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
@RolesAllowed({"Manufacturer", "Operator"})
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

        return new SensorDTO(sensorEntity.getId(),sensorEntity.getName()) ;
    }

    @Override
    protected void copyDtoToEntity(SensorDTO sensorDTO, SensorEntity sensorEntity) {
        sensorDTO.setName(sensorEntity.getName());
    }

//    @Override
//    public Response findAll() {
//        return super.findAll();
//    }

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

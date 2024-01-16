package pt.ipleiria.estg.dei.ei.dae.backend.ws;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;

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
        return null;
    }

    @Override
    protected SensorDTO convertToDto(SensorEntity sensorEntity) {
        return null;
    }

    @Override
    protected void copyDtoToEntity(SensorDTO sensorDTO, SensorEntity sensorEntity) {

    }
}

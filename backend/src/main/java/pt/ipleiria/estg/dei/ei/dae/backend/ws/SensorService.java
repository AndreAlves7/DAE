package pt.ipleiria.estg.dei.ei.dae.backend.ws;
import jakarta.ws.rs.*;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.SensorDTO;

import java.util.List;


@Path("sensor")
public class SensorService extends AbstractService<SensorDTO>{
    
    @Override
    protected SensorDTO createEntity(SensorDTO entity) {
        return null;
    }

    @Override
    protected SensorDTO findEntity(Long id) {
        return null;
    }

    @Override
    protected SensorDTO updateEntity(SensorDTO entity) {
        return null;
    }

    @Override
    protected void deleteEntity(Long id) {

    }

    @Override
    protected List findAllEntities() {
        return null;
    }
}

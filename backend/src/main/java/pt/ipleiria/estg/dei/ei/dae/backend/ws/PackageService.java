package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.PackageBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;

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
        return null;
    }

    @Override
    protected PackageDTO convertToDto(PackageEntity packageEntity) {
        return null;
    }

    @Override
    protected void copyDtoToEntity(PackageDTO packageDTO, PackageEntity packageEntity) {

    }
}

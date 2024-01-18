package pt.ipleiria.estg.dei.ei.dae.backend.ws;
import java.util.Date;

import jakarta.ejb.EJB;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.PackageSensorReadingsDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.PackageSensorReadingsBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorReadingsEntity;

@Path("readings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PackageSensorReadingsService extends AbstractService<PackageSensorReadingsEntity, PackageSensorReadingsDTO>{

    @EJB
    protected PackageSensorReadingsBean packageSensorReadingsBean;

    @Override
    protected AbstractBean<PackageSensorReadingsEntity> getBean() {
        return packageSensorReadingsBean;
    }

    @Override
    protected PackageSensorReadingsEntity convertToEntity(PackageSensorReadingsDTO packageSensorReadingsDTO) {
        return new PackageSensorReadingsEntity(null, packageSensorReadingsDTO.getValue(), packageSensorReadingsDTO.getRecordingTimeStamp());
    }

    @Override
    protected PackageSensorReadingsDTO convertToDto(PackageSensorReadingsEntity entity) {
        return new PackageSensorReadingsDTO(entity.getId(), entity.getValue(), entity.getRecordingTimeStamp()
                , entity.getPackageSensorEntity().getSensorEntity().getId(), entity.getPackageSensorEntity().getPackageEntity().getId());
    }

    @Override
    protected void copyDtoToEntity(PackageSensorReadingsDTO packageSensorReadingsDTO, PackageSensorReadingsEntity entity) {
        entity.setValue(packageSensorReadingsDTO.getValue());
        entity.setRecordingTimeStamp(packageSensorReadingsDTO.getRecordingTimeStamp());
    }

    @Override
    public Response create(PackageSensorReadingsDTO packageSensorReadingsDTO) {
        try {
            packageSensorReadingsBean.createReading(convertToEntity(packageSensorReadingsDTO)
                    , packageSensorReadingsDTO.getSensorId(), packageSensorReadingsDTO.getPackageId());
        }catch (PersistenceException e ){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Override
    public Response update(Long id, PackageSensorReadingsDTO packageSensorReadingsDTO) {
        try {
            PackageSensorReadingsEntity entityToUpdate = getBean().find(id);
            if (entityToUpdate == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            copyDtoToEntity(packageSensorReadingsDTO, entityToUpdate);

            packageSensorReadingsBean.updateReading(entityToUpdate
                    , packageSensorReadingsDTO.getSensorId(), packageSensorReadingsDTO.getPackageId());
        }catch (PersistenceException e ){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }
}

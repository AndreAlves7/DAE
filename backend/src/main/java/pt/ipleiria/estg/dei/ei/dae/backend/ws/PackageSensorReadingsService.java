package pt.ipleiria.estg.dei.ei.dae.backend.ws;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.PackageSensorReadingsDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.PackageSensorBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.PackageSensorReadingsBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorReadingsEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;

@Path("readings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
@PermitAll
public class PackageSensorReadingsService extends AbstractService<PackageSensorReadingsEntity, PackageSensorReadingsDTO>{

    @EJB
    protected PackageSensorReadingsBean packageSensorReadingsBean;

    @Override
    protected AbstractBean<PackageSensorReadingsEntity> getBean() {
        return packageSensorReadingsBean;
    }

    @Override
    protected PackageSensorReadingsEntity convertToEntity(PackageSensorReadingsDTO packageSensorReadingsDTO) {
        return new PackageSensorReadingsEntity(null, packageSensorReadingsDTO.getValue(), packageSensorReadingsDTO.getRecordingTimeStamp(), null);
    }

    @Override
    protected PackageSensorReadingsDTO convertToDto(PackageSensorReadingsEntity entity) {
        return new PackageSensorReadingsDTO(entity.getId(), entity.getValue(), entity.getRecordingTimeStamp()
                , entity.getPackageSensorEntity().getSensorEntity().getName(), entity.getPackageSensorEntity().getPackageEntity().getCode(), entity.getOrderEntity().getCode()
                , entity.getPackageSensorEntity().getSensorEntity().getId(), entity.getPackageSensorEntity().getPackageEntity().getId()
        , entity.getOrderEntity().getId());
    }

    @Override
    protected void copyDtoToEntity(PackageSensorReadingsDTO packageSensorReadingsDTO, PackageSensorReadingsEntity entity) {
        entity.setValue(packageSensorReadingsDTO.getValue());
        entity.setRecordingTimeStamp(packageSensorReadingsDTO.getRecordingTimeStamp());
    }

    @Override
    public Response find(Long id) {
        return super.find(id);
    }

    @Override
    public Response findAll() {
        return super.findAll();
    }

    @GET
    @Path("sensor/{id}")
    public Response findReadingsBySensorId(@PathParam("id") Long sensorId) {
        try {
            List<PackageSensorReadingsDTO> dtos = new ArrayList<>();
            List<PackageSensorReadingsEntity> readings = packageSensorReadingsBean.findReadingsBySensorId(sensorId);

            if (readings.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No readings found for sensor ID " + sensorId)
                        .build();
            }

            readings.forEach(entity -> {
                PackageSensorReadingsDTO readingDTO = convertToDto(entity);
                dtos.add(readingDTO);
            });

            return Response.ok(dtos).build();
        } catch (Exception e) {
            // Log the exception details here
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error processing request: " + e.getMessage())
                    .build();
        }
    }

    @Override
    @RolesAllowed({"Manufacturer", "Operator"})
    public Response create(PackageSensorReadingsDTO packageSensorReadingsDTO) {
        try {
            packageSensorReadingsBean.createReading(convertToEntity(packageSensorReadingsDTO)
                    , packageSensorReadingsDTO.getSensorId(), packageSensorReadingsDTO.getPackageId());
        } catch (PersistenceException e ) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @Override
    @RolesAllowed({"Manufacturer", "Operator"})
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

    @Override
    @RolesAllowed({"Manufacturer", "Operator"})
    public Response delete(Long id) {
        return super.delete(id);
    }


    @GET
    @Path("/order/{id}")
    public Response getAverageReadingBySensorForOrder(@PathParam("id") Long orderId) {
        try {
            Map<String, Double> averageReadings = packageSensorReadingsBean.getAverageReadingBySensorForOrder(orderId);

            if (averageReadings.isEmpty()) {
                // No data found for the given order ID
                return Response.status(Response.Status.NOT_FOUND).entity("No data found for Order ID: " + orderId).build();
            }

            // Successful response with data
            return Response.ok(averageReadings).build();
        } catch (Exception e) {
            // Handle any exceptions, possibly log them, and return an appropriate error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving data: " + e.getMessage()).build();
        }
    }
}

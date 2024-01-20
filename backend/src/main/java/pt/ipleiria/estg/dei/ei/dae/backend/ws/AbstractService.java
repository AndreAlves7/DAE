package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@DenyAll
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class AbstractService<Entity,DTO> {

    protected abstract AbstractBean<Entity> getBean();

    protected abstract Entity convertToEntity(DTO dto);
    protected abstract DTO convertToDto(Entity entity);
    protected abstract void copyDtoToEntity(DTO dto, Entity entity);

    @POST
    public Response create(DTO dto) {
        try {
            Entity entity = convertToEntity(dto);
            getBean().create(entity);
            DTO responseDto = convertToDto(entity);
            return Response.status(Response.Status.CREATED).entity(responseDto).build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Persistence error: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response find(@PathParam("id") Long id) {
        Entity entity = getBean().find(id);
        if (entity != null) {
            DTO dto = convertToDto(entity);
            return Response.ok(dto).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, DTO dto) {
        try {
            Entity entityToUpdate = getBean().find(id);
            if (entityToUpdate == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            copyDtoToEntity(dto, entityToUpdate);
            Entity updatedEntity = getBean().update(entityToUpdate);
            if(updatedEntity == null){
                throw new PersistenceException();
            }

            DTO updatedDto = convertToDto(updatedEntity);
            return Response.ok(updatedDto).build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity("Persistence error: " + e.getMessage())
                    .build();
        }
    }


    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            Entity entityToDelete = getBean().
                    find(id);
            if (entityToDelete != null) {
                getBean().delete(id);
                return Response.status(Response.Status.FOUND).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (PersistenceException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error during delete operation: " + e.getMessage())
                    .build();
        }
    }

    @GET
    public Response findAll() {
        List<Entity> entities = getBean().findAll();
        List<DTO> dtos = entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }
}
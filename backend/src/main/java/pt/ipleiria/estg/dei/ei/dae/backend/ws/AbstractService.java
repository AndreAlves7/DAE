package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class AbstractService<T> {

    @EJB
    private AbstractBean<T> bean;

    @POST
    public Response create(T entity) {
        try{
            bean.create(entity);
            return Response.status(Response.Status.CREATED).build();
        }catch (PersistenceException e){
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity("Persistence error: " + e.getMessage())
                    .build();
        }

    }

    @GET
    @Path("/{id}")
    public Response find(@PathParam("id") Long id) {
        T entity = bean.find(id);
        if (entity != null) {
            return Response.ok(entity).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, T entity) {
        try{
            bean.update(entity);
        return Response.status(Response.Status.ACCEPTED).build();
        }catch (PersistenceException e){
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity("Persistence error: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        bean.delete(id);
        return Response.noContent().build();
    }

    @GET
    public Response findAll() {
        List<T> entities = bean.findAll();
        return Response.ok(entities).build();
    }
}
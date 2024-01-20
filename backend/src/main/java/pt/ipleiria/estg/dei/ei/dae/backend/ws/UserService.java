package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UserEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;

import java.security.Principal;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
@PermitAll
public class UserService extends AbstractService<UserEntity, UserDTO>{

    @EJB
    protected UserBean userBean;

    @Context
    private SecurityContext securityContext;

    @Override
    protected AbstractBean<UserEntity> getBean() {
        return userBean;
    }

    @Override
    protected UserEntity convertToEntity(UserDTO userDTO) {
        return null;
    }

    @Override
    protected UserDTO convertToDto(UserEntity userEntity) {
        return UserDTO.from(userEntity);
    }

    @Override
    protected void copyDtoToEntity(UserDTO userDTO, UserEntity userEntity) {

    }

    @GET
    @Path("/{username}")
    public Response find(@PathParam("username") String username) {
        Principal requestUser = securityContext.getUserPrincipal();

        if(!requestUser.getName().equals(username)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        UserEntity user = userBean.findByUsername(username);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(convertToDto(user)).build();
    }

    @PUT
    @Path("/{username}")
    public Response update(@PathParam("username") String username, UserDTO userDTO) {
        try {
            UserEntity user = userBean.findByUsername(username);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            user = userBean.update(user);
            return Response.ok(convertToDto(user)).build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity("Persistence error: " + e.getMessage())
                    .build();
        }
    }
}

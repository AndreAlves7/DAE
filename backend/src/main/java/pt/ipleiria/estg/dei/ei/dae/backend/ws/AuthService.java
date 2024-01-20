package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.AuthDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UserEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;
import pt.ipleiria.estg.dei.ei.dae.backend.security.TokenIssuer;

@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @Inject
    private TokenIssuer issuer;

    @Context
    private SecurityContext securityContext;

    @EJB
    private UserBean userBean;

    @POST
    @Path("login")
    public Response authenticate(@Valid AuthDTO auth) {
        if(userBean.canLogin(auth.getUsername(), auth.getPassword())){
            String token = issuer.issue(auth.getUsername());
            return Response.ok(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("me")
    @Authenticated
    public Response getAuthenticatedUser() {
        if (securityContext != null && securityContext.getUserPrincipal() != null) {
            String username = securityContext.getUserPrincipal().getName();
            UserEntity user = userBean.findByUsername(username);
            if(user != null) {
                UserDTO userDTO = new UserDTO(user.getUsername(), user.getName(),
                        user.getEmail(), user.getUserType().getCode());
                return Response.ok(userDTO).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_USER").build();
    }
}

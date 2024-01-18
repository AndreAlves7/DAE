package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.backend.dto.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.AbstractBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UserEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class UserService extends AbstractService<UserEntity, UserDTO>{

    @EJB
    protected UserBean userBean;

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
        return null;
    }

    @Override
    protected void copyDtoToEntity(UserDTO userDTO, UserEntity userEntity) {

    }
}

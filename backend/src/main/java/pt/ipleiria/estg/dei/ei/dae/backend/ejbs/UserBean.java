package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UserEntity;

@Stateless
public class UserBean extends AbstractBean<UserEntity> {
    public UserBean(Class<UserEntity> entityClass) {
        super(entityClass);
    }

    //findOrFail(getUsername(token))
    public UserEntity findOrFail(String username){


        return new UserEntity();
    }
}

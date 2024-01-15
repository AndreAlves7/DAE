package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UserEntity;

@Stateless
@NoArgsConstructor
public class UserBean extends AbstractBean<UserEntity> {
    public UserBean(Class<UserEntity> entityClass) {
        super(entityClass);
    }

    @Override
    public UserEntity update(UserEntity entity) {
        return null;
    }
}

package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UserEntity;

@Stateless
public class UserBean extends AbstractBean<UserEntity> {
    public UserBean() {
        super(UserEntity.class);
    }

    @Override
    public UserEntity update(UserEntity entity) {
        return null;
    }
}

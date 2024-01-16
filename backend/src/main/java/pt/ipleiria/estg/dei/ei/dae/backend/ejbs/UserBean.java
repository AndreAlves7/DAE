package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import org.hibernate.Hibernate;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UserEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.UserType;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Hasher;

import java.security.InvalidParameterException;

@Stateless
public class UserBean extends AbstractBean<UserEntity> {
    public UserBean() {
        super(UserEntity.class);
    }

    @Inject
    private Hasher hasher;

    public UserEntity find(String username){
        return em.find(UserEntity.class, username);
    }

    public UserEntity findOrFail(String username){
        UserEntity user = em.getReference(UserEntity.class, username);
        Hibernate.initialize(user);
        return user;
    }

    public boolean canLogin(String username, String password) {
        UserEntity user = find(username);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }

    public UserEntity create(String username, String name, String password, String email){
        if(username.isEmpty() || name.isEmpty() || password.isEmpty() || email.isEmpty()){
            throw new InvalidParameterException();
        }

        UserEntity user = null;
        try {
            user = new UserEntity(username, name, hasher.hash(password), email);
            em.persist(user);
        } catch (ConstraintViolationException ex) {
            throw new ConstraintViolationException(ex.getConstraintViolations());
        }
        return user;
    }

    @Override
    public UserEntity update(UserEntity entity) {
        return null;
    }
}

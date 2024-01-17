package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UserEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Hasher;

import java.security.InvalidParameterException;
import java.util.Locale;

@Stateless
public class UserBean extends AbstractBean<UserEntity> {
    public UserBean() {
        super(UserEntity.class);
    }

    @Inject
    private Hasher hasher;

    public UserEntity findByUsername(String username){
        return em.find(UserEntity.class, username);
    }

    public UserEntity findOrFail(String username){
        UserEntity user = em.getReference(UserEntity.class, username);
        Hibernate.initialize(user);
        return user;
    }

    public boolean canLogin(String username, String password) {
        System.out.println(username);
        UserEntity user = findByUsername(username);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }

    @Override
    public void create(UserEntity user) {
        validateUser(user);
        try {
            user.setUsername(user.getUsername().toLowerCase(Locale.ROOT));
            user.setPassword(hasher.hash(user.getPassword()));
            super.create(user);
        } catch (ConstraintViolationException ex) {
            throw new ConstraintViolationException(ex.getConstraintViolations());
        }
    }

    @Override
    public UserEntity update(UserEntity user) {
        validateUser(user);
        try {
            user.setName(user.getName().trim());
            user.setEmail(user.getName().trim());
            user.setUserType(user.getUserType());
            em.merge(user);
        } catch (ConstraintViolationException ex) {
            throw new ConstraintViolationException(ex.getConstraintViolations());
        }
        return user;
    }

    private void validateUser(UserEntity user) {
        if(user == null || user.getUsername() == null || user.getName() == null
                || user.getPassword() == null || user.getEmail() == null){
            throw new InvalidParameterException();
        }
    }
}

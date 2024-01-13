package pt.ipleiria.estg.dei.ei.dae.backend;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UserEntity;

import java.util.List;

@Stateless
public class UserBean extends AbstractBean {
    public UserBean(UserBean entityClass) {
        super(entityClass.getClass());
    }
}

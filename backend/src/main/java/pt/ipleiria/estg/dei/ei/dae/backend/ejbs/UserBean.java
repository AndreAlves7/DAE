package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;

@Stateless
public class UserBean extends AbstractBean<UserBean> {
    public UserBean(Class<UserBean> entityClass) {
        super(entityClass);
    }
}

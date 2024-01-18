package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderPackageEntity;

@Stateless
public class OrderPackageBean extends AbstractBean<OrderPackageEntity>{
    public OrderPackageBean() {
        super(OrderPackageEntity.class);
    }
}

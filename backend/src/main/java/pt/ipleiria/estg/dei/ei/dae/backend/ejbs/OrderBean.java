package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderEntity;
import java.util.List;

@Stateless
public class OrderBean extends AbstractBean<OrderEntity>{
    public OrderBean() {
        super(OrderEntity.class);
    }
    @EJB
    private PackageBean packageBean;

    @Override
    public OrderEntity update(OrderEntity entity) {
        return null;
    }

    public void createOrder(OrderEntity order, List<Long> packageIds) {
        List<PackageEntity> packages = packageBean.findAllById(packageIds);
        order.setPackageEntities(packages);

        em.persist(order);
    }

    public void updateOrder(OrderEntity order, List<Long> packageIds) {
        List<PackageEntity> packages = packageBean.findAllById(packageIds);
        order.setPackageEntities(packages);

        em.merge(order);
    }

}

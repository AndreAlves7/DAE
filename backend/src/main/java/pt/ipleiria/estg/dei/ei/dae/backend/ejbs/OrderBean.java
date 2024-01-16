package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderPackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderEntity;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class OrderBean extends AbstractBean<OrderEntity>{
    public OrderBean() {
        super(OrderEntity.class);
    }
    @EJB
    private PackageBean packageBean;

    public void createOrder(OrderEntity order, List<Long> packageIds, int quantity) {
        List<OrderPackageEntity> orderPackages = new ArrayList<>();

        List<PackageEntity> packageEntities = packageBean.findAllById(packageIds);
        packageEntities.forEach( packageEntity -> {
            OrderPackageEntity orderPackage = new OrderPackageEntity(packageEntity,order, quantity);
            orderPackages.add(orderPackage);
            // You might need to set the relationship on both sides if you're not using cascading
            packageEntity.getOrderPackages().add(orderPackage);
        });

        order.setOrderPackages(orderPackages);
        em.persist(order);
        orderPackages.forEach(em::persist);
    }


    public void updateOrder(OrderEntity order, List<Long> packageIds) {

        // Find current OrderPackages and remove them
        List<OrderPackageEntity> currentOrderPackages = order.getOrderPackages();
        if (currentOrderPackages != null) {
            for (OrderPackageEntity currentOrderPackage : currentOrderPackages) {
                em.remove(currentOrderPackage);
            }
        }

        // Now create new OrderPackageEntity instances for the new packageIds
        List<OrderPackageEntity> newOrderPackages = new ArrayList<>();
        for (Long packageId : packageIds) {
            PackageEntity packageEntity = packageBean.find(packageId);
            OrderPackageEntity orderPackage = new OrderPackageEntity();
            orderPackage.setOrderEntity(order);
            orderPackage.setPackageEntity(packageEntity);
            // set other properties on orderPackage, like count, if necessary
            newOrderPackages.add(orderPackage);
        }

        order.setOrderPackages(newOrderPackages); // Assuming this setter is properly implemented
        em.merge(order);
        // Persist the new OrderPackageEntity entities if not cascade-persisted from OrderEntity
        for (OrderPackageEntity op : newOrderPackages) {
            em.persist(op);
        }
    }

}

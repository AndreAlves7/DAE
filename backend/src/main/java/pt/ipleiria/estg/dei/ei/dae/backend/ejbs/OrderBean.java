package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderPackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class OrderBean extends AbstractBean<OrderEntity>{
    public OrderBean() {
        super(OrderEntity.class);
    }
    @EJB
    private PackageBean packageBean;
    @EJB
    private OrderPackageBean orderPackageLinkBean;

    public void associatePackagesToOrder(Long orderId,  Map<Long, Integer> quantityByPackageID) {
        em.flush();
        OrderEntity order = find(orderId);

        if (order == null) {
            System.out.println("No order found for given ID");
            return;
        }

        List<Long> packageIds = new ArrayList<>(quantityByPackageID.keySet());
        List<PackageEntity> packageEntities = packageBean.findAllById(packageIds);

        // Instead of replacing the list, modify the existing one
        List<OrderPackageEntity> existingOrderPackages = order.getOrderPackages();
        if (existingOrderPackages == null) {
            existingOrderPackages = new ArrayList<>();
            order.setOrderPackages(existingOrderPackages);
        }

        for (PackageEntity packageEntity : packageEntities) {
            // Create and add new OrderPackageEntity instances to the existing list
            OrderPackageEntity orderPackage = new OrderPackageEntity();
            orderPackage.setOrderEntity(order);
            orderPackage.setPackageEntity(packageEntity);
            orderPackage.setQuantity(quantityByPackageID.get(packageEntity.getId()));

            existingOrderPackages.add(orderPackage);
            orderPackageLinkBean.create(orderPackage);
        }
    }

    public List<PackageEntity> findNotAssociatedPackages(Long orderId){
        String jpql = "SELECT p FROM PackageEntity p WHERE p.id NOT IN " +
                "(SELECT po.packageEntity.id FROM OrderPackageEntity po WHERE po.orderEntity.id = :orderId)";

        // Creating a typed query
        TypedQuery<PackageEntity> query = em.createQuery(jpql, PackageEntity.class);
        query.setParameter("orderId", orderId);

        // Executing the query and returning the result list
        return query.getResultList();
    }

}

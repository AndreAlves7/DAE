package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderPackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.ProductEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Stateless
public class PackageBean extends AbstractBean<PackageEntity> {
    public PackageBean() {
        super(PackageEntity.class);
    }
    public static final String PACKAGE_ASSOCIATION_SUCCESSFUL = "Package association successful";
    @EJB
    private ProductBean productBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private OrderBean orderBean;

    @EJB
    private PackageSensorBean packageSensorBean;

    @EJB
    private OrderPackageBean orderPackageBean;


    public void associateSensorsToPackages(long packageId, List<Long> sensorIds) {
        PackageEntity packageEntity = find(packageId);

        if (packageEntity == null) {
            System.out.println("No packageEntity found for given ID");
            return;
        }

        List<SensorEntity> sensorEntities = sensorBean.findAllById(sensorIds);

        // Instead of replacing the list, modify the existing one
        List<PackageSensorEntity> existingPackageSensors = packageEntity.getPackageSensors();
        if (existingPackageSensors == null) {
            existingPackageSensors = new ArrayList<>();
            packageEntity.setPackageSensors(existingPackageSensors);
        }

        for (SensorEntity sensorEntity : sensorEntities) {
            // Create and add new PackageSensorEntity instances to the existing list
            PackageSensorEntity packageSensor = new PackageSensorEntity();
            packageSensor.setSensorEntity(sensorEntity);
            packageSensor.setPackageEntity(packageEntity);

            existingPackageSensors.add(packageSensor);
            packageSensorBean.create(packageSensor);
        }
    }


    public void associateOrdersToPackage(Long packageId, Map<Long, Integer> quantityByOrderID) {
        PackageEntity packageEntity = find(packageId);

        if (packageEntity == null) {
            System.out.println("No packageEntity found for given ID");
            return;
        }

        List<Long> orderIds = new ArrayList<>(quantityByOrderID.keySet());

        List<OrderEntity> orderEntities = orderBean.findAllById(orderIds);

        // Instead of replacing the list, modify the existing one
        List<OrderPackageEntity> existingOrderPackages = packageEntity.getOrderPackages();
        if (existingOrderPackages == null) {
            existingOrderPackages = new ArrayList<>();
//            packageEntity.setOrderPackages(existingOrderPackages);
        }

        for (OrderEntity orderEntity : orderEntities) {
            // Create and add new OrderPackageEntity instances to the existing list
            OrderPackageEntity orderPackage = new OrderPackageEntity();
            orderPackage.setOrderEntity(orderEntity);
            orderPackage.setPackageEntity(packageEntity);
            orderPackage.setQuantity(quantityByOrderID.get(orderEntity.getId()));

            existingOrderPackages.add(orderPackage);
            orderPackageBean.create(orderPackage);
        }
    }

    public List<PackageEntity> findAllByOrderId(Long orderId) {
        TypedQuery<PackageEntity> query = em.createQuery(
                "SELECT p FROM PackageEntity p " +
                        "JOIN OrderPackageEntity op ON op.packageEntity.id = p.id " +
                        "JOIN OrderEntity order_entity ON op.packageEntity.id = order_entity.id " +
                        "WHERE op.orderEntity.id = :orderId", PackageEntity.class);
        query.setParameter("orderId", orderId);

        return new ArrayList<>(query.getResultList());
    }

    public String associateOuterPackage(Long innerPackageId, Long outerPackageId) {
        PackageEntity innerPackage = find(innerPackageId);
        PackageEntity outerPackage = find(outerPackageId);

        if (innerPackage == null || outerPackage == null) {
            return "Packages are null";
        }

        if (innerPackage.getPackageType() == PackageType.TRANSPORT) {
            return "Inner package can not be a Transport package";
        }

        if (outerPackage.getPackageType().ordinal() - innerPackage.getPackageType().ordinal() != 1) {
            return "Outer package must be Of an higher type than inner package";
        }

        innerPackage.setOuterPackage(outerPackage);

        update(innerPackage);

        return PACKAGE_ASSOCIATION_SUCCESSFUL;
    }

    public List<PackageEntity> findAllByProductId(Long productId) {
        TypedQuery<PackageEntity> query = em.createQuery(
                "SELECT pack FROM PackageEntity pack " +
                        "WHERE pack.product.id = :productId", PackageEntity.class);
        query.setParameter("productId", productId);

        return new ArrayList<>(query.getResultList());
    }
}

package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderPackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.ProductEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
public class PackageBean extends AbstractBean<PackageEntity> {
    public PackageBean() {
        super(PackageEntity.class);
    }

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

    @Override
    public PackageEntity update(PackageEntity entity) {
        return null;
    }

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




    public void create(PackageEntity packageEntity, Long productId){
        ProductEntity productEntity = productBean.find(productId);
        packageEntity.setProduct(productEntity);

        em.persist(packageEntity);
    }

    public PackageEntity updatePackage(PackageEntity packageEntity, Long productId) {
        ProductEntity productEntity = productBean.find(productId);
        packageEntity.setProduct(productEntity);

        return em.merge(packageEntity);
    }
}

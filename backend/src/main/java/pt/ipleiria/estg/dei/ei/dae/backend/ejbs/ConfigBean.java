package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.UserType;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Singleton
@Startup
public class ConfigBean {

    @EJB
    private OrderBean orderBean;

    @EJB
    private PackageBean packageBean;

    @EJB
    private ProductBean productBean;

    @EJB
    private SensorBean sensorBean;

    @EJB
    private UserBean userBean;


    @PostConstruct
    public void populateDB(){
        //ORDER IS IMPORTANT

        //FIRST STEP ===========
        populateSensors();
        populateProducts();
        populateUsers();

        //SECOND STEP ===========
        populatePackages();

        //TODO populateReadings();

        //THIRD STEP ===========
        populateOrders();
    }

    private void populateUsers(){
        for (int i = 1; i <= 10; i++) {
            UserEntity user = new UserEntity();
            user.setUserName("User" + i);
            user.setPassword("Password" + i);
            user.setUserType(UserType.MANUFACTURER);

            userBean.create(user);
        }
    }

    private void populateSensors(){
        for (int i = 1; i <= 10; i++) {
            SensorEntity sensor = new SensorEntity();
            sensor.setName("Sensor" + i);

//            sensor.setPackageSensors(new ArrayList<PackageSensorEntity>());

            sensorBean.create(sensor);
        }
    }

    private void populateProducts(){
        for (int i = 1; i <= 10; i++) {
            ProductEntity product = new ProductEntity();
            product.setCode("Product" + i);
            product.setPhotoBase64("Product Photo" + i);

            product.setPackageEntity(Collections.singletonList(packageBean.find((long ) i)));

            productBean.create(product);
        }
    }


    private void populatePackages(){
        for (int i = 1; i <= 10; i++) {
            PackageEntity packageEntity = new PackageEntity();
            packageEntity.setCode("Package" + i);
            packageEntity.setPackageMaterial(PackageMaterialType.CARDBOARD);
            packageEntity.setPackageType(PackageType.PRIMARY);
            packageEntity.setTransportPackage(false);

            packageEntity.setProduct(productBean.find((long ) i));
            packageEntity.setPackagesForTransportEntity(null);

//            packageEntity.setOrderPackages(new ArrayList<OrderPackageEntity>());
//            packageEntity.setPackageSensors(new ArrayList<PackageSensorEntity>());
            packageBean.create(packageEntity);
        }
    }

    private void populateOrders(){
        for (int i = 1; i <= 10; i++) {
            OrderEntity order = new OrderEntity();
            order.setCode("Order" + i);

            orderBean.create(order);
        }
        //Associate Orders
        for (int i = 1; i <= 10; i++) {
            orderBean.associateOrder((long)i, Collections.singletonList((long) i),99);
        }

    }
}
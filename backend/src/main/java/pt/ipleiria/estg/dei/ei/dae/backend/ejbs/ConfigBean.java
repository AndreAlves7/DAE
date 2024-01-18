package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.UserType;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.SensorEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.PackageType;

import java.util.*;
import java.util.stream.Collectors;


import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UserEntity;

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

        UserEntity user1 = new UserEntity("test1", "Test User 1", "123", "test1@mail.pt");
        userBean.create(user1);

        UserEntity user2 = new UserEntity("test2", "Test User 2", "123", "test2@mail.pt");
        userBean.create(user2);

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

            packageEntity.setProduct(productBean.find((long ) i));
            packageEntity.setOuterPackageId(null);

            packageBean.create(packageEntity);
        }
        //Associate Sensors
        for (int i = 1; i <= 10; i++) {
            sensorBean.associatePackagesToSensor(i,packageBean.findAll().stream().map(PackageEntity::getId).collect(Collectors.toList()));
//            packageBean.associateSensorsToPackages(i,packageBean.findAll().stream().map(PackageEntity::getId).collect(Collectors.toList()));
        }
    }

    private void populateOrders(){
        for (int i = 1; i <= 10; i++) {
            OrderEntity order = new OrderEntity();
            order.setCode("Order" + i);

            orderBean.create(order);
        }
        Map<Long, Integer> packageToRandomCount = new HashMap<>();
        Random random = new Random();

        for (int i = 1; i <= 10; i++) {
            PackageEntity packageEntity = packageBean.find((long)i);
            int randomNumber = random.nextInt(100) + 1;
            packageToRandomCount.put(packageEntity.getId(), randomNumber);
        }

        for (int i = 1; i <= 10; i++) {
//            orderBean.associatePackagesToOrder((long)i,packageToRandomCount);
            packageBean.associateOrdersToPackage((long)i,packageToRandomCount);
        }
    }
}
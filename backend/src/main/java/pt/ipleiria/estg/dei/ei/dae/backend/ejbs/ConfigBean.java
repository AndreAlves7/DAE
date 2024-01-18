package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorReadingsEntity;
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
    @EJB
    private PackageSensorReadingsBean readingsBean;


    @PostConstruct
    public void populateDB() {
        //ORDER IS IMPORTANT

        //FIRST STEP ===========
        populateSensors();
        populateProducts();
        populateUsers();

        //SECOND STEP ===========
        populatePackages();

        populateReadings();

        //THIRD STEP ===========
        populateOrders();
    }

    private void populateUsers(){
        for (int i = 1; i <= 10; i++) {
            String username = "User" + i;
            String name = "User" + i;
            String password = "Password" + i;
            String email = "User" + i + "@mail.pt";
            UserEntity user = new UserEntity(username, name, password, email);

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

    private void populateProducts() {
        for (int i = 1; i <= 10; i++) {
            ProductEntity product = new ProductEntity();
            product.setCode("Product" + i);
            product.setName("ProductName" + i);
            product.setDescription("ProductDescription" + i);

            product.setPhotoBase64("https://i5.walmartimages.com/seo/Great-Value-Whole-Vitamin-D-Milk-Gallon-128-fl-oz_6a7b09b4-f51d-4bea-a01c-85767f1b481a.86876244397d83ce6cdedb030abe6e4a.jpeg?odnHeight=640&odnWidth=640&odnBg=FFFFFF");

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
            packageEntity.setOuterPackage(null);

            packageBean.create(packageEntity);
        }
        //Associate Sensors
        for (int i = 1; i <= 10; i++) {
            sensorBean.associatePackagesToSensor(i,packageBean.findAll().stream().map(PackageEntity::getId).collect(Collectors.toList()));
//            packageBean.associateSensorsToPackages(i,packageBean.findAll().stream().map(PackageEntity::getId).collect(Collectors.toList()));
        }
    }

    private void populateReadings(){
        for (int i = 1; i <= 10; i++) {
            PackageSensorReadingsEntity readingsEntity = new PackageSensorReadingsEntity();
            readingsEntity.setValue("");
            readingsEntity.setRecordingTimeStamp(new Date());

            Random random = new Random();
            int randomNumber = random.nextInt(10) + 1;
            int randomNumber2 = random.nextInt(10) + 1;

            readingsBean.createReading(readingsEntity, (long)randomNumber , (long)randomNumber2);
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
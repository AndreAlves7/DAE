package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;
import java.util.Date;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageEntity;
import java.util.ArrayList;


import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderEntity;

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
        populateOrders();
    }

//    private void populatePackages(){
//        PackageEntity package1 = new PackageEntity();
//        package1.setCode();
//        package1.setPackageMaterial();
//        package1.setPackageType();
//        package1.setTransportPackage();
//        package1.setOrder();
//        package1.setProduct();
////        package1.setPackagesForTransportEntity();
////        package1.setPackageSensors();
//
//
//    }
    private void populateOrders(){
        for (int i = 1; i <= 10; i++) {
            OrderEntity order = new OrderEntity();
            order.setCode("Order" + i);
            orderBean.create(order);
        }
    }
}
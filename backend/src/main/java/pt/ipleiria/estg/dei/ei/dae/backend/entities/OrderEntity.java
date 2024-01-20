package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.PackageSensorReadingsEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.OrderStatus;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.UserType;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity extends AbstractEntity {

    @Column
    private String code;

    @Column(name = "is_returned", nullable = false)
    private boolean isReturned = false;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @OneToMany(mappedBy = "orderEntity" , cascade = CascadeType.REMOVE)
    private List<OrderPackageEntity> orderPackages;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    private List<PackageSensorReadingsEntity> sensorReadings;

    @Override
    protected void onCreate() {

    }
}

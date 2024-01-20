package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.sql.ordering.antlr.OrderByAliasResolver;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.AbstractEntity;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderEntity;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "package_sensor_readings")
@AttributeOverride(name = "id", column = @Column(name = "reading_id"))
@AllArgsConstructor
@NoArgsConstructor
public class PackageSensorReadingsEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "package_id", referencedColumnName = "package_id"),
            @JoinColumn(name = "sensor_id", referencedColumnName = "sensor_id")
    })
    private PackageSensorEntity packageSensorEntity;

    @Column
    private String value;

    @Column(name = "recording_time_stamp")
    private Date recordingTimeStamp;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @Override
    protected void onCreate() {

    }
}

package pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.AbstractEntity;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SensorDataEntity extends AbstractEntity {
    private int weight;
    private int initialQuantity;
    private int currentQuantity;

}

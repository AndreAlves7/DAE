package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.UserType;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Manufacturer extends UserEntity {

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public Manufacturer(String username, String password, String name, String email){
        super(username, name, password, email);
        this.userType = UserType.MANUFACTURER;
    }
}
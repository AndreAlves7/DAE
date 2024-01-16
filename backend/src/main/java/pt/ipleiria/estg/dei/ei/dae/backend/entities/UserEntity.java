package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.UserType;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity extends AbstractEntity{

    @Column(name = "user_name" ,nullable = false, unique = true)
    private String userName;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "user_type" , nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Override
    protected void onCreate() {

    }
}

package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.UserType;

@Entity
@Getter
@Setter
@Table(name="users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserEntity extends AbstractEntity {

    @Column(name = "user_name" ,nullable = false, unique = true)
    private String username;

    @NotNull
    @Column(name = "password" , nullable = false)
    private String password;

    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public UserEntity(){}

    public UserEntity(String username, String name, String password, String email) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.userType = UserType.CONSUMER;
    }
}

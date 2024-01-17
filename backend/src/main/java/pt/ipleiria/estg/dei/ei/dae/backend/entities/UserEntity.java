package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pt.ipleiria.estg.dei.ei.dae.backend.enums.UserType;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserEntity extends Versionable {
    @Id
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    public UserEntity(){}

    public UserEntity(String username, String name, String password, String email) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.userType = UserType.CONSUMER;
        this.createdDate = new Date();
    }
}

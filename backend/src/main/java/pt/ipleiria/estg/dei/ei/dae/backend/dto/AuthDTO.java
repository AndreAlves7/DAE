package pt.ipleiria.estg.dei.ei.dae.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthDTO implements Serializable {
    private String username;
    private String password;

    public AuthDTO(){}

    public AuthDTO(String username, String password){
        this.username = username;
        this.password = password;
    }
}

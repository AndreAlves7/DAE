package pt.ipleiria.estg.dei.ei.dae.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthDTO implements Serializable {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public AuthDTO(){}

    public AuthDTO(String username, String password){
        this.username = username;
        this.password = password;
    }
}

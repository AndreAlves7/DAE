package pt.ipleiria.estg.dei.ei.dae.backend.dto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotNull
    private String username;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private int userType;

    public static UserDTO from(UserEntity user) {
        return new UserDTO(user.getUsername(), user.getName(), user.getEmail(), user.getUserType().getCode());
    }

    public static List<UserDTO> from(List<UserEntity> users){
        return users.stream().map(UserDTO::from).collect(Collectors.toList());
    }
}

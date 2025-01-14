package pt.ipleiria.estg.dei.ei.dae.backend.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReadingsDTO {
    private Long id;
    private String value;
}

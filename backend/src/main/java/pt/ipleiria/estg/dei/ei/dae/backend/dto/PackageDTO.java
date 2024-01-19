package pt.ipleiria.estg.dei.ei.dae.backend.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PackageDTO {
    private Long id;
    private Long outerId;
    private String code;
    private String materialType;
    private String packageType;
    private ProductDTO product;
    private List<SensorDTO> sensors;
}

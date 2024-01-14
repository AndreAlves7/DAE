package pt.ipleiria.estg.dei.ei.dae.backend.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PackageDTO {

    private String code;
    private String materialType;
    private String packageType;
    private boolean isTransportPackage;
}

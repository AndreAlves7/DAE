package pt.ipleiria.estg.dei.ei.dae.backend.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PackageSensorReadingsDTO {
    private Long id;
    private String value;
    private Date recordingTimeStamp;
    private String sensorName;
    private String packageCode;

    private Long sensorId;
    private Long packageId;

}

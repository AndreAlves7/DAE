package pt.ipleiria.estg.dei.ei.dae.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private String code;
    private boolean isReturned;
    private Map<Long, Integer> quantityByPackageID;
}

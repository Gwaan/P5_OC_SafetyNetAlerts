package com.safetynet.alerts.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class ChildAlertDTO {

    List<PersonsCoveredByStationDTO> adults;

    List<PersonsCoveredByStationDTO> children;

}

package com.safetynet.alerts.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class CountAndPersonsCoveredDTO {

    int countOfAdults;
    int countOfChildren;

    List<PersonsCoveredByStationDTO> personsCovered;

}

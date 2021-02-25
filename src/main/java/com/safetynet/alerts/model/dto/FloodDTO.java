package com.safetynet.alerts.model.dto;

import lombok.Data;
import java.util.List;


@Data
public class FloodDTO {

    private Integer station;

    private List<AddressDTO> houseHoldCovered;

}

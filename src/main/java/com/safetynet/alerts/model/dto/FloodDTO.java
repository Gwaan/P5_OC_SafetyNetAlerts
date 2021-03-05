package com.safetynet.alerts.model.dto;

import lombok.Data;
import java.util.List;


@Data
public class FloodDTO {

    public FloodDTO() {
    }

    public FloodDTO(Integer station, List<AddressDTO> houseHoldCovered) {
        this.station = station;
        this.houseHoldCovered = houseHoldCovered;
    }

    private Integer station;

    private List<AddressDTO> houseHoldCovered;

}

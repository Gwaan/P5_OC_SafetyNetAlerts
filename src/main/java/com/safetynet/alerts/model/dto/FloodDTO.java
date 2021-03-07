package com.safetynet.alerts.model.dto;

import lombok.Data;

import java.util.List;


/**
 * Flood dto.
 *
 * @author Gwen
 * @version 1.0
 */
@Data
public class FloodDTO {

    /**
     * Instantiates a new Flood dto.
     */
    public FloodDTO() {
    }

    /**
     * Instantiates a new Flood dto.
     *
     * @param station          the station
     * @param houseHoldCovered the house hold covered
     */
    public FloodDTO(Integer station, List<AddressDTO> houseHoldCovered) {
        this.station = station;
        this.houseHoldCovered = houseHoldCovered;
    }

    /**
     * The Station.
     */
    private Integer station;

    /**
     * The House hold covered.
     */
    private List<AddressDTO> houseHoldCovered;

}

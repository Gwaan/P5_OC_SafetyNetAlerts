package com.safetynet.alerts.model.dto;

import lombok.Data;

import java.util.List;

/**
 * Address dto.
 *
 * @author Gwen
 * @version 1.0
 */
@Data
public class AddressDTO {

    /**
     * Instantiates a new Address dto.
     */
    public AddressDTO() {
    }

    /**
     * Instantiates a new Address dto.
     *
     * @param houseHold      the house hold
     * @param personInfoList the person info list
     */
    public AddressDTO(String houseHold, List<PersonInfoDTO> personInfoList) {
        this.houseHold = houseHold;
        this.personInfoList = personInfoList;
    }

    /**
     * The House hold.
     */
    private String houseHold;

    /**
     * The Person info list.
     */
    private List<PersonInfoDTO> personInfoList;

}

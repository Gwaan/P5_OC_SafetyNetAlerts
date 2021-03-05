package com.safetynet.alerts.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class AddressDTO {

    public AddressDTO() {
    }

    public AddressDTO(String houseHold, List<PersonInfoDTO> personInfoList) {
        this.houseHold = houseHold;
        this.personInfoList = personInfoList;
    }

    private String houseHold;

    private List<PersonInfoDTO> personInfoList;

}

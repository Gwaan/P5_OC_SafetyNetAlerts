package com.safetynet.alerts.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class AddressDTO {

    private String houseHold;

    private List<PersonInfoDTO> personInfoList;

}

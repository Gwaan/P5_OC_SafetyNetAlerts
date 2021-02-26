package com.safetynet.alerts.model.dto;

import lombok.Data;

@Data
public class PersonFireDTO {

    private Integer stationNumber;
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private String[] allergies;
    private String[] medications;


}

package com.safetynet.alerts.model.dto;

import lombok.Data;

@Data
public class PersonFireDTO {

    public PersonFireDTO() {
    }

    public PersonFireDTO(Integer stationNumber, String firstName,
            String lastName, String phone, int age, String[] allergies,
            String[] medications) {
        this.stationNumber = stationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.allergies = allergies;
        this.medications = medications;
    }

    private Integer stationNumber;
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private String[] allergies;
    private String[] medications;


}

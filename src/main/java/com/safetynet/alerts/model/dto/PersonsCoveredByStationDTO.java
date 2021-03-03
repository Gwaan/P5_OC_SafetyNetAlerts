package com.safetynet.alerts.model.dto;

import lombok.Data;


@Data
public class PersonsCoveredByStationDTO {

    public PersonsCoveredByStationDTO() {
    }

    public PersonsCoveredByStationDTO(String firstName, String lastName,
            String address, String city, Integer zip, String phone, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.age = age;
    }

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private Integer zip;

    private String phone;

    private int age;

}

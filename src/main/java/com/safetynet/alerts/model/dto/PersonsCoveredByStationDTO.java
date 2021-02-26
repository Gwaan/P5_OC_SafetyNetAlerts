package com.safetynet.alerts.model.dto;

import lombok.Data;


@Data
public class PersonsCoveredByStationDTO {

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private Integer zip;

    private String phone;

    private int age;

}

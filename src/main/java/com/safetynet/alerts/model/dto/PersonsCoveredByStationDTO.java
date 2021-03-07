package com.safetynet.alerts.model.dto;

import lombok.Data;


/**
 * Persons covered by station dto.
 *
 * @author Gwen
 * @version 1.0
 */
@Data
public class PersonsCoveredByStationDTO {

    /**
     * Instantiates a new Persons covered by station dto.
     */
    public PersonsCoveredByStationDTO() {
    }

    /**
     * Instantiates a new Persons covered by station dto.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param address   the address
     * @param city      the city
     * @param zip       the zip
     * @param phone     the phone
     * @param age       the age
     */
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

    /**
     * The First name.
     */
    private String firstName;

    /**
     * The Last name.
     */
    private String lastName;

    /**
     * The Address.
     */
    private String address;

    /**
     * The City.
     */
    private String city;

    /**
     * The Zip.
     */
    private Integer zip;

    /**
     * The Phone.
     */
    private String phone;

    /**
     * The Age.
     */
    private int age;

}

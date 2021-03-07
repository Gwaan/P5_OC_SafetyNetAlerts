package com.safetynet.alerts.model.dto;

import lombok.Data;

/**
 * Person fire dto.
 *
 * @author Gwen
 * @version 1.0
 */
@Data
public class PersonFireDTO {

    /**
     * Instantiates a new Person fire dto.
     */
    public PersonFireDTO() {
    }

    /**
     * Instantiates a new Person fire dto.
     *
     * @param stationNumber the station number
     * @param firstName     the first name
     * @param lastName      the last name
     * @param phone         the phone
     * @param age           the age
     * @param allergies     the allergies
     * @param medications   the medications
     */
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

    /**
     * The Station number.
     */
    private Integer stationNumber;
    /**
     * The First name.
     */
    private String firstName;
    /**
     * The Last name.
     */
    private String lastName;
    /**
     * The Phone.
     */
    private String phone;
    /**
     * The Age.
     */
    private int age;
    /**
     * The Allergies.
     */
    private String[] allergies;
    /**
     * The Medications.
     */
    private String[] medications;


}

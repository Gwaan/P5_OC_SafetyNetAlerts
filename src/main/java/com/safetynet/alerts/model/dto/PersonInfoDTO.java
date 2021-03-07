package com.safetynet.alerts.model.dto;

import lombok.Data;

/**
 * Person info dto.
 *
 * @author Gwen
 * @version 1.0
 */
@Data
public class PersonInfoDTO {

    /**
     * Instantiates a new Person info dto.
     */
    public PersonInfoDTO() {
    }

    /**
     * Instantiates a new Person info dto.
     *
     * @param firstName   the first name
     * @param lastName    the last name
     * @param phone       the phone
     * @param age         the age
     * @param allergies   the allergies
     * @param medications the medications
     */
    public PersonInfoDTO(String firstName, String lastName, String phone,
                         int age, String[] allergies, String[] medications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.allergies = allergies;
        this.medications = medications;
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

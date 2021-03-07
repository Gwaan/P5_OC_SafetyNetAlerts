package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;


/**
 * Medical record entity.
 *
 * @author Gwen
 * @version 1.0
 */
@Data
@Entity
@Table(name = "medicalrecords")
public class MedicalRecord {

    /**
     * Instantiates a new Medical record.
     */
    public MedicalRecord() {
    }

    /**
     * Instantiates a new Medical record.
     *
     * @param id          the id
     * @param firstName   the first name
     * @param lastName    the last name
     * @param birthDate   the birth date
     * @param medications the medications
     * @param allergies   the allergies
     */
    public MedicalRecord(Long id, String firstName, String lastName,
                         Date birthDate, String[] medications, String[] allergies) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.medications = medications;
        this.allergies = allergies;
    }

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    /**
     * The First name.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * The Last name.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * The Birth date.
     */
    @Column(name = "birth_date")
    @JsonProperty("birthdate")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    /**
     * The Medications.
     */
    @Column(name = "medications")
    private String[] medications;

    /**
     * The Allergies.
     */
    @Column(name = "allergies")
    private String[] allergies;


}

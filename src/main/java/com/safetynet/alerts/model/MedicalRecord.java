package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;


@Data
@Entity
@Table(name = "medicalrecords")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    @JsonProperty("birthdate")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @Column(name = "medications")
    private String[] medications;

    @Column(name = "allergies")
    private String[] allergies;



}

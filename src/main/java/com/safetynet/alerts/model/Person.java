package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "persons")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {

    public Person() {

    }

    public Person(
            @NotBlank(message = "First name field cannot be empty") String firstName,
            @NotBlank(message = "Last name field cannot be empty") String lastName,
            @NotBlank(message = "Address field cannot be empty") String address,
            @NotBlank(message = "City field cannot be empty") String city,
            @NotNull(message = "Zip field cannot be empty") Integer zip,
            @NotBlank(message = "Phone field name cannot be empty") String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "First name field cannot be empty")
    @Column(name = "first_name")
    @JsonProperty("")
    private String firstName;

    @NotBlank(message = "Last name field cannot be empty")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Address field cannot be empty")
    @Column(name = "address")
    private String address;

    @NotBlank(message = "City field cannot be empty")
    @Column(name = "city")
    private String city;

    @NotNull(message = "Zip field cannot be empty")
    @Column(name = "zip")
    private Integer zip;

    @NotBlank(message = "Phone field name cannot be empty")
    @Column(name = "phone")
    private String phone;

    @NotBlank(message = "Email field cannot be empty")
    @Column(name = "email")
    private String email;


}

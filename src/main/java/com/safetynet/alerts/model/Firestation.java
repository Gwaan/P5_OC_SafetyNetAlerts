package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Firestation entity.
 *
 * @author Gwen
 * @version 1.0
 */
@Data
@Entity
@Table(name = "firestations")
public class Firestation {

    /**
     * Instantiates a new Firestation.
     */
    public Firestation() {
    }

    /**
     * Instantiates a new Firestation.
     *
     * @param id      the id
     * @param station the station
     * @param address the address
     */
    public Firestation(Long id, @NotNull(
            message = "Station number cannot be empty") Integer station,
                       @NotBlank(
                               message = "Addresse field cannot be empty") String address) {
        this.id = id;
        this.station = station;
        this.address = address;
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
     * The Station.
     */
    @NotNull(message = "Station number cannot be empty")
    @Column(name = "station")
    private Integer station;

    /**
     * The Address.
     */
    @NotBlank(message = "Address field cannot be empty")
    @Column(name = "address")
    private String address;


}

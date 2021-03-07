package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * POJO wrapper.
 *
 * @author Gwen
 * @version 1.0
 */
@Data
public class ImportData {

    /**
     * The Persons.
     */
    List<Person> persons;

    /**
     * The Fire stations.
     */
    @JsonProperty("firestations")
    private List<Firestation> fireStations;

    /**
     * The Medical records.
     */
    @JsonProperty("medicalrecords")
    private List<MedicalRecord> medicalRecords;


}

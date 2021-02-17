package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.*;

@Data
public class ImportData {

    List<Person> persons;

    @JsonProperty("firestations")
    List<Firestation> fireStations;

    @JsonProperty("medicalrecords")
    List<MedicalRecord> medicalRecords;


}

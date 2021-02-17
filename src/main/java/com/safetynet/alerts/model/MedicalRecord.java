package com.safetynet.alerts.model;

import lombok.Data;
import java.util.*;


@Data
public class MedicalRecord {

    String firstName;
    String lastName;
    Calendar birthDate;
    String medications;
    String allergies;


}

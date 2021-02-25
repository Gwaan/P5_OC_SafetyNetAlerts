package com.safetynet.alerts.model.dto;

import com.safetynet.alerts.model.Person;
import lombok.Data;
import java.util.List;

@Data
public class ChildAlertDTO {

    List<Person> adults;

    List<Person> children;

}

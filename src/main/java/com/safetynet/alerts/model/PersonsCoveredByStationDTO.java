package com.safetynet.alerts.model;

import lombok.Setter;
import java.util.List;

@Setter
public class PersonsCoveredByStationDTO {

    int countOfAdults;
    int countOfChildren;

    List<Person> personsCovered;

}

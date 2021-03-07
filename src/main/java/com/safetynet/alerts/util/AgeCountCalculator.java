package com.safetynet.alerts.util;

import com.safetynet.alerts.model.dto.PersonsCoveredByStationDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Age count calculator.
 *
 * @author Gwen
 * @version 1.0
 */
@Component
public class AgeCountCalculator {

    /**
     * Calculate age from a Date object.
     *
     * @param birthDate the birth date
     * @return the age
     */
    public int calculateAge(Date birthDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDateConverted = null;
        if (birthDate != null) {
            birthDateConverted = birthDate
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDateConverted, currentDate).getYears();
        } else {
            return 0;
        }
    }

    /**
     * Count number of children from a list.
     *
     * @param persons the persons
     * @return the count of children
     */
    public int countNumberOfChildren(List<PersonsCoveredByStationDTO> persons) {
        int countOfChildren = 0;
        for (PersonsCoveredByStationDTO person : persons) {
            if (person.getAge() <= 18)
                countOfChildren++;
        }
        return countOfChildren;
    }


}

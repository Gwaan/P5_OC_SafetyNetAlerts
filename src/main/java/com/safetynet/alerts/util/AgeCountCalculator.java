package com.safetynet.alerts.util;

import com.safetynet.alerts.model.dto.PersonsCoveredByStationDTO;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class AgeCountCalculator {

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

    public int countNumberOfChildren(List<PersonsCoveredByStationDTO> persons) {
        int countOfChildren = 0;
        for (PersonsCoveredByStationDTO person : persons) {
            if (person.getAge() <= 18)
                countOfChildren++;
        }
        return countOfChildren;
    }


}

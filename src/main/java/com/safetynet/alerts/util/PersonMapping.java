package com.safetynet.alerts.util;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.CountAndPersonsCoveredDTO;
import com.safetynet.alerts.model.dto.PersonsCoveredByStationDTO;
import com.safetynet.alerts.service.PersonsCoveredByStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonMapping {

    @Autowired
    PersonsCoveredByStationService personsCoveredByStationService;

    @Autowired
    AgeCountCalculator ageCountCalculator;

    public List<PersonsCoveredByStationDTO> convertToPersonsCoveredByStationDto(
            List<Person> persons) {
        List<PersonsCoveredByStationDTO> personsDTO = new ArrayList<>();

        for (Person person : persons) {
            PersonsCoveredByStationDTO persDto = new PersonsCoveredByStationDTO();
            persDto.setFirstName(person.getFirstName());
            persDto.setLastName(person.getLastName());
            persDto.setAddress(person.getAddress());
            persDto.setCity(person.getCity());
            persDto.setZip(person.getZip());
            persDto.setPhone(person.getPhone());
            persDto.setAge(ageCountCalculator.calculateAge(
                    ageCountCalculator.convertToLocalDateViaInstant(
                            personsCoveredByStationService.findDateByFirstNameAndLastName(
                                    person.getFirstName(),
                                    person.getLastName()))));
            personsDTO.add(persDto);
        }
        return personsDTO;
    }

    public CountAndPersonsCoveredDTO convertToCountAndPersonsCoveredDTO(
            List<Person> persons) {
        CountAndPersonsCoveredDTO countAndPersonsCoveredDTO = new CountAndPersonsCoveredDTO();

        countAndPersonsCoveredDTO.setPersonsCovered(
                convertToPersonsCoveredByStationDto(persons));

        countAndPersonsCoveredDTO.setCountOfChildren(
                ageCountCalculator.countNumberOfChildren(
                        countAndPersonsCoveredDTO.getPersonsCovered()));
        countAndPersonsCoveredDTO.setCountOfAdults(
                countAndPersonsCoveredDTO.getPersonsCovered().size()
                        - countAndPersonsCoveredDTO.getCountOfChildren());

        return countAndPersonsCoveredDTO;
    }
}

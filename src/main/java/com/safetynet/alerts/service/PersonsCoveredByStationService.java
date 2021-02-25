package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.NotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.CountAndPersonsCoveredDTO;
import com.safetynet.alerts.model.dto.PersonsCoveredByStationDTO;
import com.safetynet.alerts.repository.PersonsCoveredByStationRepository;
import com.safetynet.alerts.util.AgeCountCalculator;
import com.safetynet.alerts.util.PersonMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PersonsCoveredByStationService {

    private static final Logger LOGGER = LogManager.getLogger(
            PersonsCoveredByStationRepository.class);

    @Autowired
    PersonsCoveredByStationRepository personsCoveredbyStationRepository;

    @Autowired
    PersonMapping personMapping;

    public CountAndPersonsCoveredDTO findPersonsWithStationNumber(
            final int stationNumber) {
        List<Person> persons = (List<Person>) personsCoveredbyStationRepository.findPersonsWithStationNumber(
                stationNumber);

        if (persons.isEmpty()) {
            LOGGER.error("PersonsCoveredByStationRepository -> No address is "
                    + "covered by the station " + "n°: " + stationNumber);
            throw new NotFoundException(
                    "No address is covered by the station n°: "
                            + stationNumber);
        }
        LOGGER.info("PersonsCoveredByStationService  -> " + persons.size()
                + " persons found.");
        return personMapping.convertToCountAndPersonsCoveredDTO(persons);
    }

    public Date findDateByFirstNameAndLastName(String firstName,
            String lastName) {
        return personsCoveredbyStationRepository.findDateByFirstNameAndLastName(
                firstName, lastName);
    }


}

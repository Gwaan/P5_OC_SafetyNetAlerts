package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.NotFoundException;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FirestationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FirestationService {

    private static final Logger LOGGER = LogManager.getLogger(
            FirestationService.class);

    @Autowired
    private FirestationRepository fireStationRepository;


    public Iterable<Firestation> findAll() {
        return fireStationRepository.findAll();
    }

    public Firestation save(Firestation firestation) {
        return fireStationRepository.save(firestation);
    }

    public Iterable<Firestation> saveAll(Iterable<Firestation> firestations) {
        return fireStationRepository.saveAll(firestations);
    }

    public Iterable<Person> findPersonsWithStationNumber(
            final byte stationNumber) {
        List<Person> person = (List<Person>) fireStationRepository.findPersonsWithStationNumber(
                stationNumber);

        if (person.isEmpty()) {
            LOGGER.error(
                    "FireStationService -> No address is covered by the station n°: "
                            + stationNumber);
            throw new NotFoundException(
                    "No address is covered by the station n°: "
                            + stationNumber);
        }
        LOGGER.info(
                "FireStationService -> " + person.size() + " persons found.");
        return person;
    }

}

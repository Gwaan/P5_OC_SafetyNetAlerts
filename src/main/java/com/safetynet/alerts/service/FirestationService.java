package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.AlreadyExistingException;
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
        if (existsFirestationByAddressAndStation(firestation.getAddress(),
                firestation.getStation())) {
            LOGGER.error("FirestationService -> Fire station number: "
                    + firestation.getStation() + " at address: "
                    + firestation.getAddress() + " is already existing");
            throw new AlreadyExistingException(
                    "FirestationService -> Fire station number: "
                            + firestation.getStation() + " at address: "
                            + firestation.getAddress()
                            + " is already existing");
        }
        return fireStationRepository.save(firestation);
    }

    public Firestation saveUpdated(Firestation firestation) {
        return fireStationRepository.save(firestation);
    }

    public Iterable<Firestation> saveAll(Iterable<Firestation> firestations) {
        return fireStationRepository.saveAll(firestations);
    }

    public void deleteFirestation(Firestation firestation) {
        fireStationRepository.delete(firestation);
    }

    public Iterable<Person> findPersonsWithStationNumber(
            final int stationNumber) {
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

    public Firestation findFirestationByAddressAndStation(String address,
            int station) {
        LOGGER.debug(
                "FirestationService -> Searching for fire station n° " + station
                        + " at address: " + address + " ...");
        Firestation firestation = fireStationRepository.findByAddressAndStation(
                address, station);
        if (firestation == null) {
            LOGGER.error("FirestationService -> Fire station n° " + station
                    + " at address: " + address + " doesn't exist");
            throw new NotFoundException(
                    "FirestationService -> Fire station n° " + station
                            + " at address: " + address + " doesn't exist");
        }
        LOGGER.info("FirestationService -> Fire station n°  " + station
                + " at address: " + address + " was found");
        return firestation;
    }

    public Firestation updateFirestation(Firestation firestationBody,
            Firestation firestationToUpdate) {
        firestationToUpdate.setAddress(firestationBody.getAddress());
        firestationToUpdate.setStation(firestationBody.getStation());

        return firestationToUpdate;
    }

    public boolean existsFirestationByAddressAndStation(String address,
            int station) {
        return fireStationRepository.existsFirestationByAddressAndStation(
                address, station);
    }
}

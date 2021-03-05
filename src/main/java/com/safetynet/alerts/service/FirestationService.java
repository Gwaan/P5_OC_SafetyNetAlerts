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

    private FirestationRepository fireStationRepository;


    public FirestationService(FirestationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }

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

    public Iterable<String> findByStation(int station) {
        return fireStationRepository.findAddressesByStation(station);
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

    public List<Integer> findStationByAddress(String address) {
        List<Integer> stationIds = (List<Integer>) fireStationRepository.findStationByAddress(
                address);
        LOGGER.debug(
                "FirestationService -> Searching for fire station at address"
                        + address + "...");
        if (stationIds.isEmpty()) {
            LOGGER.error("No station is existing at address: " + address);
            throw new NotFoundException(
                    "No station is existing at address: " + address);

        }
        return stationIds;
    }
}

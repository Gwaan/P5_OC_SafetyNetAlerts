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

/**
 * Firestation service.
 *
 * @author Gwen
 * @version 1.0
 */
@Service
public class FirestationService {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger(
            FirestationService.class);

    /**
     * @see FirestationRepository
     */
    private FirestationRepository fireStationRepository;


    /**
     * Instantiates a new Firestation service.
     *
     * @param fireStationRepository the fire station repository
     */
    public FirestationService(FirestationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }

    /**
     * Find all fire stations.
     *
     * @return all fire stations
     */
    public Iterable<Firestation> findAll() {
        return fireStationRepository.findAll();
    }

    /**
     * Save firestation.
     *
     * @param firestation the firestation
     * @return the firestation saved
     */
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

    /**
     * Save updated firestation.
     *
     * @param firestation the firestation
     * @return the firestation saved
     */
    public Firestation saveUpdated(Firestation firestation) {
        return fireStationRepository.save(firestation);
    }

    /**
     * Save all fire stations.
     *
     * @param firestations the firestations
     * @return list of fire stations saved
     */
    public Iterable<Firestation> saveAll(Iterable<Firestation> firestations) {
        return fireStationRepository.saveAll(firestations);
    }

    /**
     * Delete firestation.
     *
     * @param firestation the firestation
     */
    public void deleteFirestation(Firestation firestation) {
        fireStationRepository.delete(firestation);
    }


    /**
     * Find firestation by address and station.
     *
     * @param address the address
     * @param station the station
     * @return the firestation
     * @throws NotFoundException if no fire station was found
     */
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

    /**
     * Find by station iterable.
     *
     * @param station the station
     * @return list of fire station by station number
     */
    public Iterable<String> findByStation(int station) {
        return fireStationRepository.findAddressesByStation(station);
    }

    /**
     * Update firestation.
     *
     * @param firestationBody     the firestation body
     * @param firestationToUpdate the firestation to update
     * @return the firestation updated
     */
    public Firestation updateFirestation(Firestation firestationBody,
                                         Firestation firestationToUpdate) {
        firestationToUpdate.setAddress(firestationBody.getAddress());
        firestationToUpdate.setStation(firestationBody.getStation());

        return firestationToUpdate;
    }

    /**
     * Exists firestation by address and station boolean.
     *
     * @param address the address
     * @param station the station
     * @return either true if fire station is existing or false if it's not
     */
    public boolean existsFirestationByAddressAndStation(String address,
                                                        int station) {
        return fireStationRepository.existsFirestationByAddressAndStation(
                address, station);
    }

    /**
     * Find station by address list.
     *
     * @param address the address
     * @return list of fire station covered by address
     * @throws NotFoundException if no fire station was found
     */
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

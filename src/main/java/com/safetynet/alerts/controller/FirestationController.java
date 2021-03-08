package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.dto.CountAndPersonsCoveredDTO;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Firestation controller.
 */
@RestController
public class FirestationController {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger(
            FirestationController.class);

    /**
     * @see FirestationService
     */
    FirestationService firestationService;

    /**
     * @see PersonService
     */
    PersonService personService;

    /**
     * Instantiates a new Firestation controller.
     *
     * @param firestationService the firestation service
     * @param personService      the person service
     */
    public FirestationController(FirestationService firestationService,
            PersonService personService) {
        this.firestationService = firestationService;
        this.personService = personService;
    }

    /**
     * Gets persons covered by station id.
     *
     * @param stationNumber the station number
     * @return the persons covered by station id
     */
    @GetMapping("/firestation")
    public CountAndPersonsCoveredDTO getPersonsCoveredByStationId(
            @RequestParam final int stationNumber) {
        LOGGER.info(
                "FireStationController (GET) -> Getting all persons covered "
                        + "by station number: " + stationNumber);
        return personService.findPersonsWithStationNumber(stationNumber);

    }

    /**
     * Add firestation.
     *
     * @param firestation the firestation
     * @return the response entity
     */
    @PostMapping("/firestation")
    public ResponseEntity<Void> addFirestation(
            @Valid @RequestBody final Firestation firestation) {
        Firestation firestationToSave = firestationService.save(firestation);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(firestationToSave.getId())
                .toUri();
        LOGGER.info("FireStationController (POST) -> Fire station "
                + "successfully added: " + firestationToSave.toString());
        return ResponseEntity.created(location).build();
    }

    /**
     * Update firestation.
     *
     * @param address     the address
     * @param station     the station
     * @param firestation the firestation
     * @return the response entity
     */
    @PutMapping("/firestation")
    public ResponseEntity<Firestation> updateFirestation(
            @RequestParam final String address, @RequestParam final int station,
            @Valid @RequestBody final Firestation firestation) {

        Firestation firestationToUpdate = firestationService.findFirestationByAddressAndStation(
                address, station);
        Firestation firestationUpdated = firestationService.updateFirestation(
                firestation, firestationToUpdate);
        Firestation firestationSaved = firestationService.saveUpdated(
                firestationUpdated);

        LOGGER.info("FirestationController (PUT) -> Successfully updated fire "
                + "station: " + firestationUpdated.toString());
        return ResponseEntity.ok(firestationSaved);

    }

    /**
     * Delete fire station.
     *
     * @param address the address
     * @param station the station
     * @return the response entity
     */
    @DeleteMapping("/firestation")
    public ResponseEntity<Void> deleteFireStation(
            @RequestParam(value = "address") final String address,
            @RequestParam(value = "station") final int station) {
        Firestation firestationToDelete = firestationService.findFirestationByAddressAndStation(
                address, station);
        firestationService.deleteFirestation(firestationToDelete);
        LOGGER.info("FirestationController (DEL) -> Successfully deleted fire "
                + "station: " + firestationToDelete.toString());
        return ResponseEntity.ok().build();

    }
}

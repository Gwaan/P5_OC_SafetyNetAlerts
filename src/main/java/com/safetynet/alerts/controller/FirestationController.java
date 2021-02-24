package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.FirestationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class FirestationController {

    private static final Logger LOGGER = LogManager.getLogger(
            FirestationController.class);

    @Autowired
    FirestationService firestationService;


    @GetMapping("/firestation")
    public Iterable<Person> getPersonsCoveredByStationId(
            @RequestParam(value = "stationNumber") final int stationNumber) {
        LOGGER.info(
                "FireStationController -> Getting all persons covered by station number: "
                        + stationNumber);
        return firestationService.findPersonsWithStationNumber(stationNumber);
    }

    @PostMapping("/firestation")
    public ResponseEntity<Void> addFirestation(
            @Valid
            @RequestBody final Firestation firestation) {
        Firestation firestationToSave = firestationService.save(firestation);

        if (firestationToSave == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(firestationToSave.getId())
                .toUri();
        LOGGER.info(
                "FireStationController -> Fire station successfully added: " + firestationToSave
                        .toString());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/firestation")
    public ResponseEntity<Firestation> updateFirestation(
            @RequestParam(value = "address") final String address,
            @RequestParam(value = "station") final int station,
            @Valid
            @RequestBody final Firestation firestation) {

        Firestation firestationToUpdate = firestationService.findFirestationByAddressAndStation(address, station);
        Firestation firestationUpdated = firestationService.updateFirestation(firestation, firestationToUpdate);
        Firestation firestationSaved = firestationService.save(firestationUpdated);

        LOGGER.info("FirestationController -> Successfully updated person: "
                + firestationUpdated.toString());
        return ResponseEntity.ok(firestationUpdated);

    }

    @DeleteMapping("/firestation")
    public ResponseEntity<Void> deletePerson(
            @RequestParam(value = "address") final String address,
            @RequestParam(value = "station") final int station) {
        Firestation firestationToDelete = firestationService.findFirestationByAddressAndStation(address, station);
        firestationService.deleteFirestation(firestationToDelete);
        LOGGER.info("FirestationController -> Successfully deleted fire station: "
                + firestationToDelete.toString());
        return ResponseEntity.ok().build();

    }
}

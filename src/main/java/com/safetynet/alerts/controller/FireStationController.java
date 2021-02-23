package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.FirestationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

@RestController
public class FireStationController {

    private static final Logger LOGGER = LogManager.getLogger(
            FireStationController.class);

    @Autowired
    FirestationService firestationService;


    @GetMapping("/firestation")
    public Iterable<Person> getPersonsCoveredByStationId(
            @RequestParam(value = "stationNumber") final byte stationNumber) {
        LOGGER.info(
                "FireStationController -> Getting all persons covered by station number: "
                        + stationNumber);
        return firestationService.findPersonsWithStationNumber(stationNumber);
    }
}

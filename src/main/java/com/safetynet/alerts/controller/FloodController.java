package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.dto.FloodDTO;
import com.safetynet.alerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Flood controller.
 *
 * @author Gwen
 * @version 1.0
 */
@RestController
public class FloodController {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger(
            FloodController.class);

    /**
     * @see PersonService
     */
    PersonService personService;

    /**
     * Instantiates a new Flood controller.
     *
     * @param personService the person service
     */
    public FloodController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Get flood by stations.
     *
     * @param stations the stations
     * @return the list
     */
    @GetMapping("/flood/stations")
    public List<FloodDTO> floodController(
            @RequestParam final List<Integer> stations) {
        LOGGER.info("FloodController (GET) -> Getting all persons covered by "
                + "station(s) n° " + stations);
        return personService.getFloodDtoListByStation(stations);
    }

}

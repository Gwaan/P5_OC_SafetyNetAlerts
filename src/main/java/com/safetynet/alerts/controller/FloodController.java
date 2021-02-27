package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.dto.FloodDTO;
import com.safetynet.alerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class FloodController {

    private static final Logger LOGGER = LogManager.getLogger(
            FloodController.class);

    @Autowired
    PersonService personService;

    @GetMapping("/flood/stations")
    public List<FloodDTO> floodController(
            @RequestParam final List<Integer> stations) {
        LOGGER.info("FloodController (GET) -> Getting all persons covered by "
                + "station(s) n° " + stations);
        return personService.getFloodDtoListByStation(stations);
    }

}

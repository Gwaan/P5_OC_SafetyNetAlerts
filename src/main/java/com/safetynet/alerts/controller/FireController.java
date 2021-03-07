package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.dto.PersonFireDTO;
import com.safetynet.alerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Fire controller.
 *
 * @author Gwen
 * @version 1.0
 */
@RestController
public class FireController {

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
     * Instantiates a new Fire controller.
     *
     * @param personService the person service
     */
    public FireController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Fire controller list.
     *
     * @param address the address
     * @return the list
     */
    @GetMapping("/fire")
    public List<PersonFireDTO> fireController(@RequestParam String address) {
        LOGGER.info("FireController (GET) -> Getting persons covered by "
                + "fire station at address:  " + address);
        return personService.getFireDtoListByStation(address);
    }


}

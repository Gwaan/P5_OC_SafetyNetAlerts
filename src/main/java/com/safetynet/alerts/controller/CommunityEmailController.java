package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Community email controller.
 *
 * @author Gwen
 * @version 1.0
 */
@RestController
public class CommunityEmailController {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger(
            CommunityEmailController.class);

    /**
     * @see PersonService
     */
    PersonService personService;

    /**
     * Instantiates a new Community email controller.
     *
     * @param personService the person service
     */
    public CommunityEmailController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Gets all mail addresses from city.
     *
     * @param city the city
     * @return the all mail addresses from city
     */
    @GetMapping("/communityEmail")
    public List<Person> getAllMailAddressesFromCity(
            @RequestParam
                    String city) {
        LOGGER.info("CommunityEmailController (GET) -> Retrieving all email "
                + "address from city: " + city);

        return personService.getMailAddressesFromCity(city);
    }
}

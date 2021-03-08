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
 * Phone alert controller.
 */
@RestController
public class PhoneAlertController {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger(
            PhoneAlertController.class);

    /**
     * @see PersonService
     */
    PersonService personService;

    /**
     * Instantiates a new Phone alert controller.
     *
     * @param personService the person service
     */
    public PhoneAlertController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Get phone number by station.
     *
     * @param station the station
     * @return the list
     */
    @GetMapping("/phoneAlert")
    public List<Person> phoneAlertController(
            @RequestParam(value = "firestation") final int station) {
        LOGGER.info("PhoneAlertController (GET) -> Getting all phone numbers "
                + "for station n° " + station + " ...");
        return personService.getPhoneNumberByStation(station);
    }

}

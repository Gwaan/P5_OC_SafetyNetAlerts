package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class PhoneAlertController {

    private static final Logger LOGGER = LogManager.getLogger(
            PhoneAlertController.class);

    @Autowired
    PersonService personService;

    @GetMapping("/phoneAlert")
    public List<Person> phoneAlertController(
            @RequestParam(value = "firestation")
            final int station) {
        LOGGER.info("PhoneAlertController (GET) -> Getting all phone numbers "
                + "for station n° " + station + " ...");
        return personService.getPhoneNumberByStation(station);
    }

}

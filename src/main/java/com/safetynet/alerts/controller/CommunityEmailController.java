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
public class CommunityEmailController {

    private static final Logger LOGGER = LogManager.getLogger(
            CommunityEmailController.class);

    @Autowired
    PersonService personService;

    @GetMapping("/communityEmail")
    public List<Person> getAllMailAddressesFromCity(
            @RequestParam
                    String city) {
        LOGGER.info("CommunityEmailController (GET) -> Retrieving all email "
                + "address from city: " + city);

        return personService.findMailAddressesFromCity(city);
    }
}

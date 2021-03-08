package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.dto.PersonInfoDTO;
import com.safetynet.alerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Person info controller.
 */
@RestController
public class PersonInfoController {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger(
            PersonInfoController.class);

    /**
     * @see PersonService
     */
    PersonService personService;

    /**
     * Instantiates a new Person info controller.
     *
     * @param personService the person service
     */
    public PersonInfoController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Gets person infos.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the person infos
     */
    @GetMapping("/personInfo")
    public List<PersonInfoDTO> getPersonInfos(@RequestParam String firstName,
            @RequestParam String lastName) {

        LOGGER.info("PersonInfoController (GET) -> Getting person infos for"
                + firstName + " " + lastName);
        return personService.getPersonInfoList(firstName, lastName);
    }
}

package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.PersonInfoDTO;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.Mapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class PersonInfoController {

    private static final Logger LOGGER = LogManager.getLogger(
            PersonInfoController.class);

    PersonService personService;

    public PersonInfoController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/personInfo")
    public List<PersonInfoDTO> getPersonInfos(@RequestParam String firstName,
            @RequestParam String lastName) {

        LOGGER.info("PersonInfoController (GET) -> Getting person infos for"
                + firstName + " " + lastName);
        return personService.getPersonInfoList(firstName, lastName);
    }
}

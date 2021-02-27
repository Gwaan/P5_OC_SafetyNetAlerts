package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.dto.ChildAlertDTO;
import com.safetynet.alerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChildAlertController {

    private static final Logger LOGGER = LogManager.getLogger(
            ChildAlertController.class);

    @Autowired
    PersonService personService;

    @GetMapping("/childAlert")
    public ChildAlertDTO getChildAtAddress(
            @RequestParam(value = "address") final String address) {
        LOGGER.info("ChildAlertController (GET) -> Getting all persons "
                + "covered by address: " + address);
        return personService.getListOfChildrenByAddress(address);
    }

}

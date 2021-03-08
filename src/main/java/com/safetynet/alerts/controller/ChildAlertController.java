package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.dto.ChildAlertDTO;
import com.safetynet.alerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Child alert controller.
 *
 * @author Gwen
 * @version 1.0
 */
@RestController
public class ChildAlertController {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger(
            ChildAlertController.class);

    /**
     * @see PersonService
     */
    PersonService personService;

    /**
     * Instantiates a new Child alert controller.
     *
     * @param personService the person service
     */
    public ChildAlertController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Gets child at address.
     *
     * @param address the address
     * @return the child at address
     */
    @GetMapping("/childAlert")
    public ChildAlertDTO getChildAtAddress(
            @RequestParam(value = "address") final String address) {
        LOGGER.info("ChildAlertController (GET) -> Getting all persons "
                + "covered by address: " + address);
        return personService.getListOfChildrenByAddress(address);
    }

}

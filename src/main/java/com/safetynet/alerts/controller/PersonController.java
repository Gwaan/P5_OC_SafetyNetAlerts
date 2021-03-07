package com.safetynet.alerts.controller;

import com.safetynet.alerts.exceptions.NotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Person controller.
 *
 * @author Gwen
 * @version 1.0
 */
@RestController
public class PersonController {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger(
            PersonController.class);


    /**
     * @see PersonService
     */
    private PersonService personService;

    /**
     * Instantiates a new Person controller.
     *
     * @param personService the person service
     */
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Get all persons.
     *
     * @return the persons
     */
    @GetMapping("/person")
    public Iterable<Person> getPersons() {
        LOGGER.info("PersonController (GET) -> Get all persons ");
        return personService.findAll();
    }

    /**
     * Add person.
     *
     * @param person the person
     * @return the response entity
     */
    @PostMapping("/person")
    public ResponseEntity<Void> addPerson(
            @Valid @RequestBody final Person person) {
        Person personToSave = personService.save(person);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(personToSave.getId())
                .toUri();
        LOGGER.info("PersonController (POST) -> Person successfully added: "
                + personToSave.toString());
        return ResponseEntity.created(location).build();
    }

    /**
     * Update person.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param person    the person
     * @return the response entity
     */
    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(
            @RequestParam(value = "firstName") final String firstName,
            @RequestParam(value = "lastName") final String lastName,
            @Valid @RequestBody final Person person) {

        Person personToUpdate = personService.findByFirstNameAndLastName(
                firstName, lastName);
        Person personUpdated = personService.updatePerson(person,
                personToUpdate);
        final Person personSaved = personService.saveUpdated(personToUpdate);
        LOGGER.info("PersonController (PUT) -> Successfully updated person: "
                + personUpdated.toString());
        return ResponseEntity.ok(personSaved);

    }

    /**
     * Delete person.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the response entity
     */
    @DeleteMapping("/person")
    public ResponseEntity<Void> deletePerson(
            @RequestParam(value = "firstName") final String firstName,
            @RequestParam(value = "lastName") final String lastName) {
        Person personToDelete = personService.findByFirstNameAndLastName(
                firstName, lastName);
        personService.deletePerson(personToDelete);
        LOGGER.info("PersonController (DEL) -> Successfully deleted person: "
                + personToDelete.toString());
        return ResponseEntity.ok().build();

    }
}




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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class PersonController {

    private static final Logger LOGGER = LogManager.getLogger(
            PersonController.class);

    @Autowired
    private PersonService personService;

    @GetMapping("/person")
    public Iterable<Person> getPersons() {
        LOGGER.info("PersonController -> Get all persons ");
        return personService.findAll();
    }

    @GetMapping("/person/{firstName}_{lastName}")
    public Person getPerson(@PathVariable final String firstName,
            @PathVariable final String lastName) {
        return personService.findByFirstNameAndLastName(firstName, lastName);
    }

    @PostMapping("/person")
    public ResponseEntity<Void> addPerson(
            @Valid @RequestBody final Person person) {
        Person personToSave = personService.save(person);

        if (personToSave == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(personToSave.getId())
                .toUri();
        LOGGER.info("PersonController -> Person successfully added: "
                + personToSave.toString());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/person/{firstName}_{lastName}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable final String firstName,
            @PathVariable final String lastName,
            @Valid @RequestBody final Person person) {

        Person personToUpdate = personService.findByFirstNameAndLastName(
                firstName, lastName);

        personToUpdate.setAddress(person.getAddress());
        personToUpdate.setCity(person.getCity());
        personToUpdate.setEmail(person.getEmail());
        personToUpdate.setPhone(person.getPhone());
        personToUpdate.setZip(person.getZip());

        final Person personUpdated = personService.save(personToUpdate);
        LOGGER.info("PersonController -> Successfully updated person: "
                + personUpdated.toString());
        return ResponseEntity.ok(personUpdated);

    }

    @DeleteMapping("/person/{firstName}_{lastName}")
    public ResponseEntity<Void> deletePerson(@PathVariable String firstName,
            @PathVariable String lastName) {
        Person personToDelete = personService.findByFirstNameAndLastName(
                firstName, lastName);
        personService.deletePerson(personToDelete);
        LOGGER.info("PersonController -> Successfully deleted person: "
                + personToDelete.toString());
        return ResponseEntity.ok().build();

    }
}




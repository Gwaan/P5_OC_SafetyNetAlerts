package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public Person getPerson(@PathVariable String firstName,
            @PathVariable String lastName) {
        return personService.findByFirstNameAndLastName(firstName, lastName);
    }

    @PostMapping("/person")
    public ResponseEntity<Void> addPerson(@Valid @RequestBody Person person) {
        Person personToSave = personService.save(person);

        if (personToSave == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(personToSave.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}

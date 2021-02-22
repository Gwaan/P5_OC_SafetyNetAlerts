package com.safetynet.alerts.service;

import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.exceptions.NotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    private static final Logger LOGGER = LogManager.getLogger(
            PersonService.class);


    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Iterable<Person> saveAll(Iterable<Person> persons) {
        return personRepository.saveAll(persons);
    }

    public Person findByFirstNameAndLastName(String firstName,
            String lastName) {
        LOGGER.info(
                "PersonService -> Searching for person " + firstName + " " + lastName
                        + " ...");
        Person person = personRepository.findByFirstNameAndLastName(firstName,
                lastName);

        if (person == null) {
            LOGGER.error(
                    "Person " + firstName + " " + lastName + " doesn't exist");
            throw new NotFoundException(
                    "Person " + firstName + " " + lastName + " doesn't exist");
        }
        LOGGER.info("PersonService -> Person " + firstName + " " + lastName
                + " was found");
        return person;
    }

}

package com.safetynet.alerts.service;

import com.safetynet.alerts.controller.ChildAlertController;
import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.exceptions.AlreadyExistingException;
import com.safetynet.alerts.exceptions.NotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.ChildAlertDTO;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.repository.PersonsCoveredByStationRepository;
import com.safetynet.alerts.util.AgeCountCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AgeCountCalculator ageCountCalculator;

    @Autowired
    private PersonsCoveredByStationService personsCoveredByStationService;

    private static final Logger LOGGER = LogManager.getLogger(
            PersonService.class);


    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    public Person save(Person person) {
        if (existsPersonByFirstNameAndLastName(person.getFirstName(),
                person.getLastName())) {
            LOGGER.error(
                    "PersonService -> Person " + person.getFirstName() + " "
                            + person.getLastName() + " is already existing");
            throw new AlreadyExistingException(
                    "PersonService -> Person: " + person.getFirstName() + " "
                            + person.getLastName() + " is already existing.");
        }

        return personRepository.save(person);
    }

    public Person saveUpdated(Person person) {
        return personRepository.save(person);
    }

    public Iterable<Person> saveAll(Iterable<Person> persons) {
        return personRepository.saveAll(persons);
    }

    public Person findByFirstNameAndLastName(String firstName,
            String lastName) {
        LOGGER.debug("PersonService -> Searching for person " + firstName + " "
                + lastName + " ...");
        Person person = personRepository.findByFirstNameAndLastName(firstName,
                lastName);

        if (person == null) {
            LOGGER.error("PersonService -> " + firstName + " " + lastName
                    + " doesn't exist");
            throw new NotFoundException(
                    "Person " + firstName + " " + lastName + " doesn't exist");
        }
        LOGGER.info("PersonService -> Person " + firstName + " " + lastName
                + " was found");
        return person;
    }

    public void deletePerson(Person person) {
        personRepository.delete(person);
    }

    public Person updatePerson(Person personBody, Person personToUpdate) {
        personToUpdate.setAddress(personBody.getAddress());
        personToUpdate.setCity(personBody.getCity());
        personToUpdate.setEmail(personBody.getEmail());
        personToUpdate.setPhone(personBody.getPhone());
        personToUpdate.setZip(personBody.getZip());

        return personToUpdate;
    }

    public boolean existsPersonByFirstNameAndLastName(String firstName,
            String lastName) {
        return personRepository.existsPersonByFirstNameAndLastName(firstName,
                lastName);
    }

    public ChildAlertDTO findPersonByAddress(String address) {
        List<Person> personsByAddress = (List<Person>) personRepository.findPersonByAddress(
                address);
        LOGGER.debug(
                "PersonService -> Searching for person at address: " + address);
        if (personsByAddress.isEmpty()) {
            LOGGER.error(
                    "PersonService -> No person found at address: " + address);
            throw new NotFoundException(
                    "PersonService -> No person found at address: " + address);
        }
        return sortChildrenAndAdults(personsByAddress);
    }

    public ChildAlertDTO sortChildrenAndAdults(List<Person> personsByAddress) {
        ChildAlertDTO childAlertDTO = new ChildAlertDTO();
        List<Person> adults = new ArrayList<>();
        List<Person> children = new ArrayList<>();
        for (Person person : personsByAddress) {

            int age = ageCountCalculator.calculateAge(
                    ageCountCalculator.convertToLocalDate(
                            personsCoveredByStationService.findDateByFirstNameAndLastName(
                                    person.getFirstName(),
                                    person.getLastName())));

            if (age <= 18) {
                children.add(person);
            } else {
                adults.add(person);
            }

        }
        childAlertDTO.setAdults(adults);
        childAlertDTO.setChildren(children);

        LOGGER.info("PersonService -> " + adults.size() + " adult(s) found / "
                + children.size() + " children found");

        return childAlertDTO;
    }


}

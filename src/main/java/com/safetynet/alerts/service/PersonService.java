package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.AlreadyExistingException;
import com.safetynet.alerts.exceptions.NotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.ChildAlertDTO;
import com.safetynet.alerts.model.dto.CountAndPersonsCoveredDTO;
import com.safetynet.alerts.model.dto.FloodDTO;
import com.safetynet.alerts.model.dto.PersonFireDTO;
import com.safetynet.alerts.model.dto.PersonsCoveredByStationDTO;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.util.AgeCountCalculator;
import com.safetynet.alerts.util.PersonMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AgeCountCalculator ageCountCalculator;

    @Autowired
    private PersonMapping personMapping;

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

    public List<Person> findPersonsByFirstNameAndLastName(String firstName,
            String lastName) {
        LOGGER.debug("PersonService -> Searching for person " + firstName + " "
                + lastName + " ...");
        List<Person> persons = (List<Person>) personRepository.findPersonByFirstNameAndLastName(
                firstName, lastName);

        if (persons.isEmpty()) {
            LOGGER.error("PersonService -> " + firstName + " " + lastName
                    + " doesn't exist");
            throw new NotFoundException(
                    "Person " + firstName + " " + lastName + " doesn't exist");
        }
        LOGGER.info("PersonService -> Person " + firstName + " " + lastName
                + " was found");
        return persons;
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
        return personRepository.existsPersonsByFirstNameAndLastName(firstName,
                lastName);
    }

    public List<Person> findPersonByAddress(String address) {
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
        return personsByAddress;
    }

    public List<Person> findPersonsByAddresses(List<String> addresses) {
        return (List<Person>) personRepository.findPersonByAddress(addresses);
    }

    //TODO: rename method

    //TODO: refactor code

    public List<Person> findPersonByStation(int station) {
        List<Person> personFound = (List<Person>) personRepository.findPersonByStation(
                station);

        if (personFound.isEmpty()) {
            LOGGER.error(
                    "PersonService -> No person covered by station: " + station
                            + " found");
            throw new NotFoundException(
                    "PersonService -> No person covered by station: " + station
                            + " found");
        }
        return personFound;
    }

    public CountAndPersonsCoveredDTO findPersonsWithStationNumber(
            final int stationNumber) {
        List<Person> persons = (List<Person>) personRepository.findPersonsWithStationNumber(
                stationNumber);

        if (persons.isEmpty()) {
            LOGGER.error("PersonsService -> No address is "
                    + "covered by the station " + "n°: " + stationNumber);
            throw new NotFoundException(
                    "No address is covered by the station n°: "
                            + stationNumber);
        }
        LOGGER.info("PersonsCoveredByStationService  -> " + persons.size()
                + " persons found.");
        return personMapping.convertToCountAndPersonsCoveredDTO(persons);
    }

    public Date findDateByFirstNameAndLastName(String firstName,
            String lastName) {
        return personRepository.findDateByFirstNameAndLastName(firstName,
                lastName);
    }

    public int getAge(String firstName, String lastName) {
        Date dateOfBirth = findDateByFirstNameAndLastName(firstName, lastName);
        return ageCountCalculator.calculateAge(dateOfBirth);
    }

    public int countNumberOfChildren(List<PersonsCoveredByStationDTO> persons) {
        int countOfChildren = 0;
        for (PersonsCoveredByStationDTO person : persons) {
            if (person.getAge() <= 18)
                countOfChildren++;
        }
        return countOfChildren;
    }

    public List<PersonFireDTO> getPersonCoveredByStation(String address) {
        return personMapping.convertToFireDTOList(address);
    }

    public List<FloodDTO> getFloodDtoByStation(List<Integer> stations) {
        return personMapping.convertToFloodDtoList(stations);
    }

    public ChildAlertDTO getChildAlertDtoSorted(String address) {
        List<Person> personsByAddress = findPersonByAddress(address);
        List<PersonsCoveredByStationDTO> personsCoveredByStationDTOS = personMapping
                .convertToPersonsCoveredByStationDto(personsByAddress);
        ChildAlertDTO childAlertDTO = new ChildAlertDTO();
        List<PersonsCoveredByStationDTO> adults = new ArrayList<>();
        List<PersonsCoveredByStationDTO> children = new ArrayList<>();
        for (PersonsCoveredByStationDTO person : personsCoveredByStationDTOS) {

            int age = getAge(person.getFirstName(), person.getLastName());

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

        if (childAlertDTO.getChildren().isEmpty())
            return null;
        else {
            return childAlertDTO;
        }
    }

    public List<Person> getMailAddressesFromCity(String city) {
        List<Person> personsMailAddresses = (List<Person>) personRepository.findMailAddressesFromCity(
                city);
        LOGGER.debug("Searching for mail addresses from persons who lives in: "
                + city);

        if (personsMailAddresses.isEmpty()) {
            LOGGER.error("No persons covered in city: " + city);
            throw new NotFoundException("No persons covered in city: " + city);
        }
        LOGGER.info(personsMailAddresses.size() + " mail address(es) found");
        return personsMailAddresses;
    }

    public List<Person> getPhoneNumberByStation(int station) {
        List<Person> personsPhoneNumber = (List<Person>) personRepository.findPhoneNumberByStation(
                station);
        LOGGER.debug(
                "Searching for persons covered by station number: " + station);

        if (personsPhoneNumber.isEmpty()) {
            LOGGER.error(
                    "PersonService -> No person covered by station: " + station
                            + " found");
            throw new NotFoundException(
                    "PersonService -> No person covered by station: " + station
                            + " found");
        }
        LOGGER.info(personsPhoneNumber.size() + " number(s) found");
        return personsPhoneNumber;
    }
}

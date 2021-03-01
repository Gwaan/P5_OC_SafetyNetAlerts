package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.AlreadyExistingException;
import com.safetynet.alerts.exceptions.NotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.AddressDTO;
import com.safetynet.alerts.model.dto.ChildAlertDTO;
import com.safetynet.alerts.model.dto.CountAndPersonsCoveredDTO;
import com.safetynet.alerts.model.dto.FloodDTO;
import com.safetynet.alerts.model.dto.PersonFireDTO;
import com.safetynet.alerts.model.dto.PersonInfoDTO;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.util.AgeCountCalculator;
import com.safetynet.alerts.util.Mapping;
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
    private Mapping mapping;

    @Autowired
    private FirestationService firestationService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    private static final Logger LOGGER = LogManager.getLogger(
            PersonService.class);

    //TODO: fix bean cycle with constructor injection
    /*public PersonService(PersonRepository personRepository,
            AgeCalculator ageCountCalculator, Mapping mapping,
            FirestationService firestationService) {
        this.personRepository = personRepository;
        this.ageCountCalculator = ageCountCalculator;
        this.mapping = mapping;
        this.firestationService = firestationService;
    }*/

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
        List<Person> persons = (List<Person>) personRepository.findPersonsByFirstNameAndLastName(
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
        List<MedicalRecord> medicalRecords = new ArrayList<>();

        for (Person person : persons) {
            MedicalRecord medicalRecord = medicalRecordService.findByFirstNameAndLastName(
                    person.getFirstName(), person.getLastName());
            medicalRecords.add(medicalRecord);
        }
        CountAndPersonsCoveredDTO countAndPersonsCoveredDTO = mapping.convertPersonListToCountAndPersonsCoveredDTO(
                persons, medicalRecords);
        return countAndPersonsCoveredDTO;
    }


    public List<PersonFireDTO> getFireDtoListByStation(String address) {
        List<PersonFireDTO> personFireDTOList = null;
        List<Integer> listOfStations = firestationService.findStationByAddress(
                address);
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        for (Integer integer : listOfStations) {
            List<Person> personsCovered = findPersonByStation(integer);
            for (Person person : personsCovered) {
                MedicalRecord medicalRecord = medicalRecordService.findByFirstNameAndLastName(
                        person.getFirstName(), person.getLastName());
                medicalRecords.add(medicalRecord);
            }
            personFireDTOList = mapping.convertPersonListToPersonFireList(
                    personsCovered, integer, medicalRecords);
        }

        return personFireDTOList;
    }

    public List<FloodDTO> getFloodDtoListByStation(List<Integer> station) {
        List<FloodDTO> floodDTOList = new ArrayList<>();
        List<AddressDTO> addressDtoList = new ArrayList<>();
        for (Integer integer : station) {
            List<String> firestationAddresses = (List<String>) firestationService
                    .findByStation(integer);

            for (String firestationAddress : firestationAddresses) {
                List<MedicalRecord> medicalRecords = new ArrayList<>();
                List<Person> personsCovered = findPersonByAddress(
                        firestationAddress);
                for (Person person : personsCovered) {
                    MedicalRecord medicalRecord = medicalRecordService.findByFirstNameAndLastName(
                            person.getFirstName(), person.getLastName());
                    medicalRecords.add(medicalRecord);
                }
                AddressDTO addressDTO = mapping.createAddressDto(
                        firestationAddress, personsCovered, medicalRecords);
                addressDtoList.add(addressDTO);
            }

            FloodDTO floodDTO = mapping.createFloodDTO(integer, addressDtoList);
            floodDTOList.add(floodDTO);
        }

        return floodDTOList;
    }

    public ChildAlertDTO getListOfChildrenByAddress(final String address) {
        List<Person> personByAddressList = findPersonByAddress(address);
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        for (Person person : personByAddressList) {
            MedicalRecord medicalRecord = medicalRecordService.findByFirstNameAndLastName(
                    person.getFirstName(), person.getLastName());
            medicalRecords.add(medicalRecord);
        }
        ChildAlertDTO childAlertDTO = mapping.createChildAlertDto(
                personByAddressList, medicalRecords);
        LOGGER.info(
                "PersonService -> " + childAlertDTO.getChildren().size() + " "
                        + "children found / " + childAlertDTO.getAdults().size()
                        + " adults " + "founds");
        return childAlertDTO;
    }

    public List<PersonInfoDTO> getPersonInfoList(String firstName,
            String lastName) {
        List<Person> personList = findPersonsByFirstNameAndLastName(firstName,
                lastName);
        List<MedicalRecord> medicalRecordList = medicalRecordService.findMedicalRecordsByFirstNameAndLastName(
                firstName, lastName);
        List<PersonInfoDTO> personInfoDTOList = mapping.convertPersonListToPersonInfoDtoList(
                personList, medicalRecordList);
        return personInfoDTOList;
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

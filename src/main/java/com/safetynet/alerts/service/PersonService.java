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

/**
 * Person service.
 *
 * @author Gwen
 * @version 1.0
 */
@Service
public class PersonService {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger(
            PersonService.class);

    /**
     * @see PersonRepository
     */
    private PersonRepository personRepository;

    /**
     * @see Mapping
     */
    private Mapping mapping;

    /**
     * @see FirestationService
     */
    private FirestationService firestationService;

    /**
     * @see MedicalRecordService
     */
    private MedicalRecordService medicalRecordService;

    /**
     * Instantiates a new Person service.
     *
     * @param personRepository     the person repository
     * @param mapping              the mapping
     * @param firestationService   the firestation service
     * @param medicalRecordService the medical record service
     */
    public PersonService(PersonRepository personRepository, Mapping mapping,
                         FirestationService firestationService,
                         MedicalRecordService medicalRecordService) {
        this.personRepository = personRepository;
        this.mapping = mapping;
        this.firestationService = firestationService;
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Find all persons.
     *
     * @return list of persons
     */
    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    /**
     * Save person.
     *
     * @param person the person
     * @return the person
     * @throws AlreadyExistingException if person is already existing
     */
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

    /**
     * Save updated person.
     *
     * @param person the person
     * @return the person saved
     */
    public Person saveUpdated(Person person) {
        return personRepository.save(person);
    }

    /**
     * Save all persons.
     *
     * @param persons the persons
     * @return list of persons saved
     */
    public Iterable<Person> saveAll(Iterable<Person> persons) {
        return personRepository.saveAll(persons);
    }

    /**
     * Find person by first name and last name.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the person
     * @throws NotFoundException if no person was found
     */
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

    /**
     * Find persons by first name and last name list.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return list of persons found
     * @throws NotFoundException if no person was found
     */
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


    /**
     * Delete person.
     *
     * @param person the person
     */
    public void deletePerson(Person person) {
        personRepository.delete(person);
    }

    /**
     * Update person.
     *
     * @param personBody     the person body
     * @param personToUpdate the person to update
     * @return the person updated
     */
    public Person updatePerson(Person personBody, Person personToUpdate) {
        personToUpdate.setAddress(personBody.getAddress());
        personToUpdate.setCity(personBody.getCity());
        personToUpdate.setEmail(personBody.getEmail());
        personToUpdate.setPhone(personBody.getPhone());
        personToUpdate.setZip(personBody.getZip());

        return personToUpdate;
    }

    /**
     * Exists person by first name and last name.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return either true if person is existing or false if it's not
     */
    public boolean existsPersonByFirstNameAndLastName(String firstName,
                                                      String lastName) {
        return personRepository.existsPersonsByFirstNameAndLastName(firstName,
                lastName);
    }

    /**
     * Find person by address.
     *
     * @param address the address
     * @return list of persons covered by address
     */
    public List<Person> findPersonByAddress(String address) {

        List<Person> personsByAddress = (List<Person>) personRepository.findPersonByAddress(
                address);
        LOGGER.debug("PersonService -> Searching for person at address: "
                + address);

        return personsByAddress;
    }

    /**
     * Find person by station.
     *
     * @param station the station
     * @return list of persons covered by station number
     * @throws NotFoundException if no person was found
     */
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

    /**
     * Find persons covered by station number and convert them into a CountAndPersonsCoveredDTO object.
     *
     * @param stationNumber the station number
     * @return the count and persons covered dto object
     * @throws NotFoundException if no person was found
     */
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


    /**
     * Find persons covered by address and convert theme into a PersonFireDTO list.
     *
     * @param address the address
     * @return PersonFireDTO list
     */
    public List<PersonFireDTO> getFireDtoListByStation(String address) {
        List<PersonFireDTO> personFireDTOList = new ArrayList<>();
        List<Integer> listOfStations = firestationService.findStationByAddress(
                address);
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        for (Integer integer : listOfStations) {
            List<Person> personsCovered = findPersonByStation(integer);
            for (Person person : personsCovered) {
                MedicalRecord medicalRecord = medicalRecordService.findByFirstNameAndLastName(
                        person.getFirstName(), person.getLastName());
                if (medicalRecord != null)
                    medicalRecords.add(medicalRecord);
            }
            List<PersonFireDTO> personFireDTOListToAdd = mapping.convertPersonListToPersonFireList(
                    personsCovered, integer, medicalRecords);
            personFireDTOList.addAll(personFireDTOListToAdd);

        }

        return personFireDTOList;
    }

    /**
     * Find person covered by station numbers and convert them into a FloodDTO list.
     *
     * @param station the station
     * @return flood dto list
     */
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

    /**
     * Find persons covered by address and convert them into a ChildAlertDTO object.
     *
     * @param address the address
     * @return ChildAlertDTO object
     */
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

    /**
     * Find persons by first name and last name and convert theme into a PersonInfoDTO list.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return person info list
     */
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

    /**
     * Get mail addresses from city.
     *
     * @param city the city
     * @return the mail addresses from city
     */
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

    /**
     * Get phone number by station.
     *
     * @param station the station
     * @return the phone number by station
     */
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

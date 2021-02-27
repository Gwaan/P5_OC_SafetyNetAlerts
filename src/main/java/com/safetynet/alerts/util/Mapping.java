package com.safetynet.alerts.util;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.AddressDTO;
import com.safetynet.alerts.model.dto.ChildAlertDTO;
import com.safetynet.alerts.model.dto.CountAndPersonsCoveredDTO;
import com.safetynet.alerts.model.dto.FloodDTO;
import com.safetynet.alerts.model.dto.PersonFireDTO;
import com.safetynet.alerts.model.dto.PersonInfoDTO;
import com.safetynet.alerts.model.dto.PersonsCoveredByStationDTO;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class Mapping {


    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PersonService personService;

    @Autowired
    private FirestationService firestationService;

    public Mapping(MedicalRecordService medicalRecordService,
            PersonService personService,
            FirestationService firestationService) {
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
        this.personService = personService;
        this.firestationService = firestationService;
    }

    public List<PersonsCoveredByStationDTO> convertPersonListToPersonsCoveredByStationDtoList(
            List<Person> persons) {
        List<PersonsCoveredByStationDTO> personsDTO = new ArrayList<>();

        for (Person person : persons) {
            PersonsCoveredByStationDTO personsCoveredByStationDTO = convertPersonToPersonsCoveredByStationsDto(
                    person);
            personsDTO.add(personsCoveredByStationDTO);
        }
        return personsDTO;
    }

    public PersonsCoveredByStationDTO convertPersonToPersonsCoveredByStationsDto(
            Person person) {
        PersonsCoveredByStationDTO persDto = new PersonsCoveredByStationDTO();
        persDto.setFirstName(person.getFirstName());
        persDto.setLastName(person.getLastName());
        persDto.setAddress(person.getAddress());
        persDto.setCity(person.getCity());
        persDto.setZip(person.getZip());
        persDto.setPhone(person.getPhone());
        persDto.setAge(personService.getAge(person.getFirstName(),
                person.getLastName()));
        return persDto;
    }

    public List<PersonInfoDTO> convertPersonListToPersonInfoDtoList(
            List<Person> persons) {
        List<PersonInfoDTO> personsInfoDTO = new ArrayList<>();

        for (Person person : persons) {
            PersonInfoDTO personInfoDTO = convertPersonToPersonInfoDto(person);
            personsInfoDTO.add(personInfoDTO);
        }

        return personsInfoDTO;
    }

    public PersonInfoDTO convertPersonToPersonInfoDto(Person person) {
        PersonInfoDTO personInfoDTO = new PersonInfoDTO();
        personInfoDTO.setFirstName(person.getFirstName());
        personInfoDTO.setLastName(person.getLastName());
        personInfoDTO.setPhone(person.getPhone());
        personInfoDTO.setAge(personService.getAge(person.getFirstName(),
                person.getLastName()));
        personInfoDTO.setAllergies(
                medicalRecordService.getAllergies(person.getFirstName(),
                        person.getLastName()));
        personInfoDTO.setMedications(
                medicalRecordService.getMedications(person.getFirstName(),
                        person.getLastName()));
        return personInfoDTO;
    }

    public List<PersonFireDTO> convertPersonListToPersonFireList(
            List<Person> persons, Integer station) {
        List<PersonFireDTO> personFireDTOList = new ArrayList<>();

        for (Person person : persons) {
            PersonFireDTO personFireDTO = convertPersonToPersonFireDto(person);
            personFireDTO.setStationNumber(station);
            personFireDTOList.add(personFireDTO);
        }

        return personFireDTOList;
    }

    public PersonFireDTO convertPersonToPersonFireDto(Person person) {
        PersonFireDTO personFireDTO = new PersonFireDTO();
        personFireDTO.setFirstName(person.getFirstName());
        personFireDTO.setLastName(person.getLastName());
        personFireDTO.setPhone(person.getPhone());
        personFireDTO.setAge(personService.getAge(person.getFirstName(),
                person.getLastName()));
        personFireDTO.setAllergies(
                medicalRecordService.getAllergies(person.getFirstName(),
                        person.getLastName()));
        personFireDTO.setMedications(
                medicalRecordService.getMedications(person.getFirstName(),
                        person.getLastName()));
        return personFireDTO;
    }


    public CountAndPersonsCoveredDTO convertPersonListToCountAndPersonsCoveredDTO(
            List<Person> persons) {
        CountAndPersonsCoveredDTO countAndPersonsCoveredDTO = new CountAndPersonsCoveredDTO();

        countAndPersonsCoveredDTO.setPersonsCovered(
                convertPersonListToPersonsCoveredByStationDtoList(persons));

        countAndPersonsCoveredDTO.setCountOfChildren(
                personService.countNumberOfChildren(
                        countAndPersonsCoveredDTO.getPersonsCovered()));
        countAndPersonsCoveredDTO.setCountOfAdults(
                countAndPersonsCoveredDTO.getPersonsCovered().size()
                        - countAndPersonsCoveredDTO.getCountOfChildren());

        return countAndPersonsCoveredDTO;
    }

    public ChildAlertDTO createChildAlertDto(final List<Person> personList) {
        ChildAlertDTO childAlertDTO = new ChildAlertDTO();
        List<PersonsCoveredByStationDTO> personsCoveredByStationDTOList = convertPersonListToPersonsCoveredByStationDtoList(
                personList);
        List<PersonsCoveredByStationDTO> adults = new ArrayList<>();
        List<PersonsCoveredByStationDTO> children = new ArrayList<>();

        for (PersonsCoveredByStationDTO person : personsCoveredByStationDTOList) {
            int age = personService.getAge(person.getFirstName(),
                    person.getLastName());
            if (age <= 18) {
                children.add(person);
            } else {
                adults.add(person);
            }
        }
        childAlertDTO.setAdults(adults);
        childAlertDTO.setChildren(children);
        return childAlertDTO;
    }


    public AddressDTO createAddressDto(final String address,
            final List<Person> personInfoDTOList) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setHouseHold(address);
        addressDTO.setPersonInfoList(
                convertPersonListToPersonInfoDtoList(personInfoDTOList));

        return addressDTO;

    }

    public FloodDTO createFloodDTO(final Integer station,
            final List<AddressDTO> addressDTOList) {
        FloodDTO floodDTO = new FloodDTO();
        floodDTO.setStation(station);
        floodDTO.setHouseHoldCovered(addressDTOList);

        return floodDTO;
    }
}





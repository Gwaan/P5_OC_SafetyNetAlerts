package com.safetynet.alerts.util;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.AddressDTO;
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
public class PersonMapping {


    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PersonService personService;

    @Autowired
    FirestationService firestationService;

    public PersonMapping(

            MedicalRecordService medicalRecordService,
            PersonService personService,
            FirestationService firestationService) {
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
        this.personService = personService;
        this.firestationService = firestationService;
    }

    public List<PersonsCoveredByStationDTO> convertToPersonsCoveredByStationDto(
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

    public CountAndPersonsCoveredDTO convertToCountAndPersonsCoveredDTO(
            List<Person> persons) {
        CountAndPersonsCoveredDTO countAndPersonsCoveredDTO = new CountAndPersonsCoveredDTO();

        countAndPersonsCoveredDTO.setPersonsCovered(
                convertToPersonsCoveredByStationDto(persons));

        countAndPersonsCoveredDTO.setCountOfChildren(
                personService.countNumberOfChildren(
                        countAndPersonsCoveredDTO.getPersonsCovered()));
        countAndPersonsCoveredDTO.setCountOfAdults(
                countAndPersonsCoveredDTO.getPersonsCovered().size()
                        - countAndPersonsCoveredDTO.getCountOfChildren());

        return countAndPersonsCoveredDTO;
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

    public List<PersonInfoDTO> convertToPersonInfoDtoList(
            List<Person> persons) {
        List<PersonInfoDTO> personsInfoDTO = new ArrayList<>();

        for (Person person : persons) {
            PersonInfoDTO personInfoDTO = convertPersonToPersonInfoDto(person);
            personsInfoDTO.add(personInfoDTO);
        }

        return personsInfoDTO;
    }


    public List<FloodDTO> convertToFloodDtoList(List<Integer> station) {
        List<FloodDTO> floodDTOList = new ArrayList<>();
        List<AddressDTO> addressDtoList = new ArrayList<>();
        for (Integer integer : station) {
            FloodDTO floodDTO = new FloodDTO();
            List<String> firestationAddresses = (List<String>) firestationService
                    .findByStation(integer);

            for (String firestationAddress : firestationAddresses) {
                AddressDTO addressDTO = new AddressDTO();
                List<Person> personsCovered = personService.findPersonByAddress(
                        firestationAddress);
                List<PersonInfoDTO> personInfoDTOS = convertToPersonInfoDtoList(
                        personsCovered);
                addressDTO.setHouseHold(firestationAddress);
                addressDTO.setPersonInfoList(personInfoDTOS);
                addressDtoList.add(addressDTO);
            }

            floodDTO.setStation(integer);
            floodDTO.setHouseHoldCovered(addressDtoList);
            floodDTOList.add(floodDTO);
        }

        return floodDTOList;
    }

    public List<PersonFireDTO> convertToFireDTOList(String address) {
        List<PersonFireDTO> personFireDTOList = new ArrayList<>();
        List<Integer> listOfStations = firestationService.findStationByAddress(
                address);
        for (Integer integer : listOfStations) {
            List<Person> personsCovered = personService.findPersonByStation(
                    integer);
            for (Person person : personsCovered) {
                PersonFireDTO personFireDTO = convertPersonToFireDto(person);
                personFireDTO.setStationNumber(integer);
                personFireDTOList.add(personFireDTO);

            }
        }
        return personFireDTOList;
    }

    public PersonFireDTO convertPersonToFireDto(Person person) {
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


}

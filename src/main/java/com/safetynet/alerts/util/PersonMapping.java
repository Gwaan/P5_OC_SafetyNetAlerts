package com.safetynet.alerts.util;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.AddressDTO;
import com.safetynet.alerts.model.dto.CountAndPersonsCoveredDTO;
import com.safetynet.alerts.model.dto.FloodDTO;
import com.safetynet.alerts.model.dto.PersonInfoDTO;
import com.safetynet.alerts.model.dto.PersonsCoveredByStationDTO;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.service.PersonsCoveredByStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonMapping {

    @Autowired
    PersonsCoveredByStationService personsCoveredByStationService;

    @Autowired
    AgeCountCalculator ageCountCalculator;

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    PersonService personService;

    @Autowired
    FirestationService firestationService;

    public List<PersonsCoveredByStationDTO> convertToPersonsCoveredByStationDto(
            List<Person> persons) {
        List<PersonsCoveredByStationDTO> personsDTO = new ArrayList<>();

        for (Person person : persons) {
            PersonsCoveredByStationDTO persDto = new PersonsCoveredByStationDTO();
            persDto.setFirstName(person.getFirstName());
            persDto.setLastName(person.getLastName());
            persDto.setAddress(person.getAddress());
            persDto.setCity(person.getCity());
            persDto.setZip(person.getZip());
            persDto.setPhone(person.getPhone());
            persDto.setAge(ageCountCalculator.calculateAge(
                    ageCountCalculator.convertToLocalDate(
                            personsCoveredByStationService.findDateByFirstNameAndLastName(
                                    person.getFirstName(),
                                    person.getLastName()))));
            personsDTO.add(persDto);
        }
        return personsDTO;
    }

    public CountAndPersonsCoveredDTO convertToCountAndPersonsCoveredDTO(
            List<Person> persons) {
        CountAndPersonsCoveredDTO countAndPersonsCoveredDTO = new CountAndPersonsCoveredDTO();

        countAndPersonsCoveredDTO.setPersonsCovered(
                convertToPersonsCoveredByStationDto(persons));

        countAndPersonsCoveredDTO.setCountOfChildren(
                ageCountCalculator.countNumberOfChildren(
                        countAndPersonsCoveredDTO.getPersonsCovered()));
        countAndPersonsCoveredDTO.setCountOfAdults(
                countAndPersonsCoveredDTO.getPersonsCovered().size()
                        - countAndPersonsCoveredDTO.getCountOfChildren());

        return countAndPersonsCoveredDTO;
    }

    public List<PersonInfoDTO> convertToPersonInfoDto(List<Person> persons) {
        List<PersonInfoDTO> personsInfoDTO = new ArrayList<>();

        for (Person person : persons) {
            PersonInfoDTO personInfoDTO = new PersonInfoDTO();
            personInfoDTO.setFirstName(person.getFirstName());
            personInfoDTO.setLastName(person.getLastName());
            personInfoDTO.setPhone(person.getPhone());
            personInfoDTO.setAge(ageCountCalculator.calculateAge(
                    ageCountCalculator.convertToLocalDate(
                            personsCoveredByStationService.findDateByFirstNameAndLastName(
                                    person.getFirstName(),
                                    person.getLastName()))));
            personInfoDTO.setAllergies(medicalRecordService
                    .findByFirstNameAndLastName(person.getFirstName(),
                            person.getLastName())
                    .getAllergies());
            personInfoDTO.setMedications(medicalRecordService
                    .findByFirstNameAndLastName(person.getFirstName(),
                            person.getLastName())
                    .getMedications());
            personsInfoDTO.add(personInfoDTO);
        }

        return personsInfoDTO;
    }

    //TODO: floodDTOList must be returned in personService

    public List<FloodDTO> convertToFloodDto(List<Integer> station) {
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
                List<PersonInfoDTO> personInfoDTOS = convertToPersonInfoDto(
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
}

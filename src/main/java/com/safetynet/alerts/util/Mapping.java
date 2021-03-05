package com.safetynet.alerts.util;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.AddressDTO;
import com.safetynet.alerts.model.dto.ChildAlertDTO;
import com.safetynet.alerts.model.dto.CountAndPersonsCoveredDTO;
import com.safetynet.alerts.model.dto.FloodDTO;
import com.safetynet.alerts.model.dto.PersonFireDTO;
import com.safetynet.alerts.model.dto.PersonInfoDTO;
import com.safetynet.alerts.model.dto.PersonsCoveredByStationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Mapping {


    private AgeCountCalculator ageCountCalculator;

    public Mapping(AgeCountCalculator ageCountCalculator) {
        this.ageCountCalculator = ageCountCalculator;
    }

    public List<PersonsCoveredByStationDTO> convertPersonListToPersonsCoveredByStationDtoList(
            final List<Person> persons,
            final List<MedicalRecord> medicalRecords) {
        List<PersonsCoveredByStationDTO> personsDTO = new ArrayList<>();

        for (Person person : persons) {

            MedicalRecord medicalRecord = mapMedicalRecordsWithPerson(person,
                    medicalRecords);
            PersonsCoveredByStationDTO personsCoveredByStationDTO = convertPersonToPersonsCoveredByStationsDto(
                    person, medicalRecord);
            personsDTO.add(personsCoveredByStationDTO);

        }
        return personsDTO;
    }

    public PersonsCoveredByStationDTO convertPersonToPersonsCoveredByStationsDto(
            final Person person, final MedicalRecord medicalRecord) {
        PersonsCoveredByStationDTO persDto = new PersonsCoveredByStationDTO();
        persDto.setFirstName(person.getFirstName());
        persDto.setLastName(person.getLastName());
        persDto.setAddress(person.getAddress());
        persDto.setCity(person.getCity());
        persDto.setZip(person.getZip());
        persDto.setPhone(person.getPhone());
        persDto.setAge(medicalRecord != null ? ageCountCalculator.calculateAge(
                medicalRecord.getBirthDate()) : 404);
        return persDto;
    }

    public List<PersonInfoDTO> convertPersonListToPersonInfoDtoList(
            final List<Person> persons,
            final List<MedicalRecord> medicalRecords) {
        List<PersonInfoDTO> personsInfoDTO = new ArrayList<>();

        for (Person person : persons) {
            MedicalRecord medicalRecord = mapMedicalRecordsWithPerson(person,
                    medicalRecords);
            PersonInfoDTO personInfoDTO = convertPersonToPersonInfoDto(person,
                    medicalRecord);
            personsInfoDTO.add(personInfoDTO);

        }

        return personsInfoDTO;
    }

    public PersonInfoDTO convertPersonToPersonInfoDto(final Person person,
            final MedicalRecord medicalRecord) {
        PersonInfoDTO personInfoDTO = new PersonInfoDTO();
        personInfoDTO.setFirstName(person.getFirstName());
        personInfoDTO.setLastName(person.getLastName());
        personInfoDTO.setPhone(person.getPhone());
        personInfoDTO.setAge(
                medicalRecord != null ? ageCountCalculator.calculateAge(
                        medicalRecord.getBirthDate()) : 404);
        personInfoDTO.setAllergies(
                medicalRecord != null ? medicalRecord.getAllergies()
                        : new String[]{"No medical record found"});
        personInfoDTO.setMedications(
                medicalRecord != null ? medicalRecord.getMedications()
                        : new String[]{"No medical record found"});
        return personInfoDTO;
    }

    public List<PersonFireDTO> convertPersonListToPersonFireList(
            final List<Person> persons, final Integer station,
            final List<MedicalRecord> medicalRecords) {
        List<PersonFireDTO> personFireDTOList = new ArrayList<>();

        for (Person person : persons) {
            MedicalRecord medicalRecord = mapMedicalRecordsWithPerson(person,
                    medicalRecords);
            PersonFireDTO personFireDTO = convertPersonToPersonFireDto(person,
                    medicalRecord);
            personFireDTO.setStationNumber(station);
            personFireDTOList.add(personFireDTO);

        }

        return personFireDTOList;
    }

    public PersonFireDTO convertPersonToPersonFireDto(final Person person,
            final MedicalRecord medicalRecord) {
        PersonFireDTO personFireDTO = new PersonFireDTO();
        personFireDTO.setFirstName(person.getFirstName());
        personFireDTO.setLastName(person.getLastName());
        personFireDTO.setPhone(person.getPhone());
        personFireDTO.setAge(
                medicalRecord != null ? ageCountCalculator.calculateAge(
                        medicalRecord.getBirthDate()) : 404);
        personFireDTO.setAllergies(
                medicalRecord != null ? medicalRecord.getAllergies()
                        : new String[]{"No medical record found"});
        personFireDTO.setMedications(
                medicalRecord != null ? medicalRecord.getMedications()
                        : new String[]{"No medical record found"});
        return personFireDTO;
    }


    public CountAndPersonsCoveredDTO convertPersonListToCountAndPersonsCoveredDTO(
            final List<Person> persons,
            final List<MedicalRecord> medicalRecords) {
        CountAndPersonsCoveredDTO countAndPersonsCoveredDTO = new CountAndPersonsCoveredDTO();

        countAndPersonsCoveredDTO.setPersonsCovered(
                convertPersonListToPersonsCoveredByStationDtoList(persons,
                        medicalRecords));

        countAndPersonsCoveredDTO.setCountOfChildren(
                ageCountCalculator.countNumberOfChildren(
                        countAndPersonsCoveredDTO.getPersonsCovered()));
        countAndPersonsCoveredDTO.setCountOfAdults(
                countAndPersonsCoveredDTO.getPersonsCovered().size()
                        - countAndPersonsCoveredDTO.getCountOfChildren());

        return countAndPersonsCoveredDTO;
    }

    public ChildAlertDTO createChildAlertDto(final List<Person> personList,
            final List<MedicalRecord> medicalRecords) {
        ChildAlertDTO childAlertDTO = new ChildAlertDTO();
        List<PersonsCoveredByStationDTO> personsCoveredByStationDTOList = convertPersonListToPersonsCoveredByStationDtoList(
                personList, medicalRecords);
        List<PersonsCoveredByStationDTO> adults = new ArrayList<>();
        List<PersonsCoveredByStationDTO> children = new ArrayList<>();

        for (PersonsCoveredByStationDTO person : personsCoveredByStationDTOList) {
            MedicalRecord medicalRecord = mapMedicalRecordsWithPersonCoveredByStationDTO(
                    person, medicalRecords);
            int age = medicalRecord != null ? ageCountCalculator.calculateAge(
                    medicalRecord.getBirthDate()) : 404;
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
            final List<Person> personInfoDTOList,
            final List<MedicalRecord> medicalRecords) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setHouseHold(address);
        addressDTO.setPersonInfoList(
                convertPersonListToPersonInfoDtoList(personInfoDTOList,
                        medicalRecords));

        return addressDTO;

    }

    public FloodDTO createFloodDTO(final Integer station,
            final List<AddressDTO> addressDTOList) {
        FloodDTO floodDTO = new FloodDTO();
        floodDTO.setStation(station);
        floodDTO.setHouseHoldCovered(addressDTOList);

        return floodDTO;
    }

    public MedicalRecord mapMedicalRecordsWithPersonCoveredByStationDTO(
            PersonsCoveredByStationDTO person,
            List<MedicalRecord> medicalRecords) {
        MedicalRecord medicalRecordToReturn = null;
        for (MedicalRecord medicalRecord : medicalRecords) {

            if (person
                    .getFirstName()
                    .equalsIgnoreCase(medicalRecord.getFirstName()) && person
                    .getLastName()
                    .equalsIgnoreCase(medicalRecord.getLastName())) {
                medicalRecordToReturn = medicalRecord;
            }
        }
        return medicalRecordToReturn;
    }

    public MedicalRecord mapMedicalRecordsWithPerson(Person person,
            List<MedicalRecord> medicalRecords) {
        MedicalRecord medicalRecordToReturn = null;
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (person
                    .getFirstName()
                    .equalsIgnoreCase(medicalRecord.getFirstName()) && person
                    .getLastName()
                    .equalsIgnoreCase(medicalRecord.getLastName())) {
                medicalRecordToReturn = medicalRecord;
            }
        }
        return medicalRecordToReturn;
    }

    public MedicalRecord mapMedicalRecordsWithPersonInfoDTO(
            PersonInfoDTO person, List<MedicalRecord> medicalRecords) {
        MedicalRecord medicalRecordToReturn = null;
        for (MedicalRecord medicalRecord : medicalRecords) {

            if (person
                    .getFirstName()
                    .equalsIgnoreCase(medicalRecord.getFirstName()) && person
                    .getLastName()
                    .equalsIgnoreCase(medicalRecord.getLastName())) {
                medicalRecordToReturn = medicalRecord;
            }
        }
        return medicalRecordToReturn;
    }
}




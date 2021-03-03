package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.AlreadyExistingException;
import com.safetynet.alerts.exceptions.NotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.util.AgeCountCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MedicalRecordService {

    private static final Logger LOGGER = LogManager.getLogger(
            MedicalRecordService.class);

    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(
            MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public Iterable<MedicalRecord> list() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        if (existsMedicalRecordByFirstNameAndLastName(
                medicalRecord.getFirstName(), medicalRecord.getLastName())) {
            LOGGER.error("MedicalRecordService -> Medical record for: "
                    + medicalRecord.getFirstName() + " "
                    + medicalRecord.getLastName() + " is already existing");
            throw new AlreadyExistingException(
                    "MedicalRecordService -> Medical record for: "
                            + medicalRecord.getFirstName() + " " + medicalRecord
                            .getLastName() + " is already existing.");
        }
        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord saveUpdated(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public Iterable<MedicalRecord> saveAll(
            Iterable<MedicalRecord> medicalRecords) {
        return medicalRecordRepository.saveAll(medicalRecords);
    }

    public MedicalRecord findByFirstNameAndLastName(String firstName,
            String lastName) {
        try {
            LOGGER.debug(
                    "MedicalRecordService -> Searching for person " + firstName
                            + " " + lastName + " ...");
            MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(
                    firstName, lastName);
            if (medicalRecord == null) {
                LOGGER.error(
                        "MedicalRecordService -> " + firstName + " " + lastName
                                + " doesn't exist");
                throw new NotFoundException(
                        "Person " + firstName + " " + lastName
                                + " doesn't exist");
            }
            LOGGER.info(
                    "MedicalRecordService -> Medical record for " + firstName
                            + " " + lastName + " was found");
            return medicalRecord;
        } catch (NotFoundException e) {
            return new MedicalRecord(null, "", "", null, new String[]{""},
                    new String[]{""});
        }

    }

    public List<MedicalRecord> findMedicalRecordsByFirstNameAndLastName(
            String firstName, String lastName) {
        LOGGER.debug("MedicalRecordService -> Searching for person " + firstName
                + " " + lastName + " ...");
        List<MedicalRecord> medicalRecordList = (List<MedicalRecord>) medicalRecordRepository
                .findMedicalRecordsByFirstNameAndLastName(firstName, lastName);
        if (medicalRecordList.isEmpty()) {
            LOGGER.error("MedicalRecordService -> " + firstName + " " + lastName
                    + " doesn't exist");
            throw new NotFoundException(
                    "Person " + firstName + " " + lastName + " doesn't exist");
        }
        LOGGER.info(
                "MedicalRecordService -> Medical record for " + firstName + " "
                        + lastName + " was found");
        return medicalRecordList;
    }

    public void deleteMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.delete(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecordBody,
            MedicalRecord medicalRecordUpdated) {

        medicalRecordUpdated.setBirthDate(medicalRecordBody.getBirthDate());
        medicalRecordUpdated.setMedications(medicalRecordBody.getMedications());
        medicalRecordUpdated.setAllergies(medicalRecordBody.getAllergies());

        return medicalRecordUpdated;
    }

    public boolean existsMedicalRecordByFirstNameAndLastName(String firstName,
            String lastName) {
        return medicalRecordRepository.existsMedicalRecordByFirstNameAndLastName(
                firstName, lastName);
    }


}

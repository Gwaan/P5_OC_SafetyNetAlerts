package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.NotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService {

    private static final Logger LOGGER = LogManager.getLogger(
            MedicalRecordService.class);
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public Iterable<MedicalRecord> list() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public Iterable<MedicalRecord> saveAll(
            Iterable<MedicalRecord> medicalRecords) {
        return medicalRecordRepository.saveAll(medicalRecords);
    }

    public MedicalRecord findByFirstNameAndLastName(String firstName,
            String lastName) {
        LOGGER.debug("MedicalRecordService -> Searching for person " + firstName
                + " " + lastName + " ...");
        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(
                firstName, lastName);
        if (medicalRecord == null) {
            LOGGER.error("MedicalRecordService -> " + firstName + " " + lastName
                    + " doesn't exist");
            throw new NotFoundException(
                    "Person " + firstName + " " + lastName + " doesn't exist");
        }
        LOGGER.info(
                "MedicalRecordService -> Medical record for " + firstName + " "
                        + lastName + " was found");
        return medicalRecord;
    }

    public void deleteMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.delete(medicalRecord);
    }

}

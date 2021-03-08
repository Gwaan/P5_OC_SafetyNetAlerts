package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.AlreadyExistingException;
import com.safetynet.alerts.exceptions.NotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Medical record service.
 *
 * @author Gwen
 * @version 1.0
 */
@Service
public class MedicalRecordService {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger(
            MedicalRecordService.class);

    /**
     * @see MedicalRecordRepository
     */
    private MedicalRecordRepository medicalRecordRepository;

    /**
     * Instantiates a new Medical record service.
     *
     * @param medicalRecordRepository the medical record repository
     */
    public MedicalRecordService(
            MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    /**
     * Find all medical records.
     *
     * @return medical records
     */
    public Iterable<MedicalRecord> findAll() {
        return medicalRecordRepository.findAll();
    }

    /**
     * Save medical record.
     *
     * @param medicalRecord the medical record
     * @return the medical record saved
     * @throws AlreadyExistingException if medical record is already existing
     */
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

    /**
     * Save updated medical record.
     *
     * @param medicalRecord the medical record
     * @return the medical record saved
     */
    public MedicalRecord saveUpdated(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    /**
     * Save all medical records.
     *
     * @param medicalRecords the medical records
     * @return list of medical records saved
     */
    public Iterable<MedicalRecord> saveAll(
            Iterable<MedicalRecord> medicalRecords) {
        return medicalRecordRepository.saveAll(medicalRecords);
    }

    /**
     * Find  medical record by first name and last name.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the medical record
     * @throws NotFoundException if no medical record was found
     */
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

    /**
     * Find medical records by first name and last name.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return list of medical records
     * @throws NotFoundException if no medical record was found
     */
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

    /**
     * Delete medical record.
     *
     * @param medicalRecord the medical record
     */
    public void deleteMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.delete(medicalRecord);
    }

    /**
     * Update medical record.
     *
     * @param medicalRecordBody    the medical record body
     * @param medicalRecordUpdated the medical record updated
     * @return the medical record updated
     */
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecordBody,
                                             MedicalRecord medicalRecordUpdated) {

        medicalRecordUpdated.setBirthDate(medicalRecordBody.getBirthDate());
        medicalRecordUpdated.setMedications(medicalRecordBody.getMedications());
        medicalRecordUpdated.setAllergies(medicalRecordBody.getAllergies());

        return medicalRecordUpdated;
    }

    /**
     * Exists medical record by first name and last name boolean.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return either true if medical record is existing or false if it's not
     */
    public boolean existsMedicalRecordByFirstNameAndLastName(String firstName,
                                                             String lastName) {
        return medicalRecordRepository.existsMedicalRecordByFirstNameAndLastName(
                firstName, lastName);
    }


}

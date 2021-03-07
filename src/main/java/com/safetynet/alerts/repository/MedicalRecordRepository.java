package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Medical record repository.
 */
@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Long> {

    /**
     * Find by first name and last name medical record.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the medical record
     */
    MedicalRecord findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Find medical records by first name and last name iterable.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return list of medical records
     */
    Iterable<MedicalRecord> findMedicalRecordsByFirstNameAndLastName(
            String firstName, String lastName);

    /**
     * Exists medical record by first name and last name boolean.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return either true if medical record is existing or false if it's not
     */
    boolean existsMedicalRecordByFirstNameAndLastName(String firstName,
                                                      String lastName);

}

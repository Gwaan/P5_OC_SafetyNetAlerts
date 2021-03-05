package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Long> {

    MedicalRecord findByFirstNameAndLastName(String firstName, String lastName);

    Iterable<MedicalRecord> findMedicalRecordsByFirstNameAndLastName(
            String firstName, String lastName);

    boolean existsMedicalRecordByFirstNameAndLastName(String firstName,
            String lastName);

    @Query(value = "SELECT m.birthDate FROM MedicalRecord m WHERE m.firstName"
            + " = " + " :firstName AND m.lastName = :lastName")
    Date findDateByFirstNameAndLastName(String firstName, String lastName);


}

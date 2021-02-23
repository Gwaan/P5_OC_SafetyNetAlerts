package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class MedicalRecordController {

    private static final Logger LOGGER = LogManager.getLogger(
            MedicalRecordController.class);
    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/medicalRecord/list")
    public Iterable<MedicalRecord> list() {
        return medicalRecordService.list();
    }


    @PostMapping("/medicalRecord")
    public ResponseEntity<Void> addPerson(
            @Valid @RequestBody final MedicalRecord medicalRecord) {
        MedicalRecord medicalRecordToSave = medicalRecordService.save(
                medicalRecord);

        if (medicalRecordToSave == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(medicalRecordToSave.getId())
                .toUri();
        LOGGER.info(
                "MedicalRecordController -> Successfully add medical record: "
                        + medicalRecordToSave.toString());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/medicalRecord/{firstName}_{lastName}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(
            @PathVariable final String firstName,
            @PathVariable final String lastName,
            @Valid @RequestBody final MedicalRecord medicalRecord) {

        MedicalRecord medicalRecordToUpdate = medicalRecordService.findByFirstNameAndLastName(
                firstName, lastName);

        medicalRecordToUpdate.setBirthDate(medicalRecord.getBirthDate());
        medicalRecordToUpdate.setMedications(medicalRecord.getMedications());
        medicalRecordToUpdate.setAllergies(medicalRecord.getAllergies());

        final MedicalRecord medicalRecordUpdated = medicalRecordService.save(
                medicalRecordToUpdate);
        LOGGER.info(
                "MedicalRecordController -> Successfully updated medical record: "
                        + medicalRecordUpdated.toString());
        return ResponseEntity.ok(medicalRecordUpdated);

    }

    @DeleteMapping("/medicalRecord/{firstName}_{lastName}")
    public ResponseEntity<Void> deleteMedicalRecord(
            @PathVariable String firstName, @PathVariable String lastName) {
        MedicalRecord medicalRecordToDelete = medicalRecordService.findByFirstNameAndLastName(
                firstName, lastName);
        medicalRecordService.deleteMedicalRecord(medicalRecordToDelete);
        LOGGER.info(
                "MedicalRecordController -> Successfully deleted medical record: "
                        + medicalRecordToDelete.toString());
        return ResponseEntity.ok().build();

    }
}

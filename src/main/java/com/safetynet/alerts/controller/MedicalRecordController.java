package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class MedicalRecordController {

    private static final Logger LOGGER = LogManager.getLogger(
            MedicalRecordController.class);

    private MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/medicalRecord")
    public Iterable<MedicalRecord> list() {
        return medicalRecordService.findAll();
    }


    @PostMapping("/medicalRecord")
    public ResponseEntity<Void> addPerson(
            @Valid @RequestBody final MedicalRecord medicalRecord) {
        MedicalRecord medicalRecordToSave = medicalRecordService.save(
                medicalRecord);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(medicalRecordToSave.getId())
                .toUri();
        LOGGER.info("MedicalRecordController (POST) -> Medical record "
                + "successfully added: " + medicalRecordToSave.toString());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(
            @RequestParam(value = "firstName") final String firstName,
            @RequestParam(value = "lastName") final String lastName,
            @Valid @RequestBody final MedicalRecord medicalRecord) {

        MedicalRecord medicalRecordToUpdate = medicalRecordService.findByFirstNameAndLastName(
                firstName, lastName);
        MedicalRecord medicalRecordUpdated = medicalRecordService.updateMedicalRecord(
                medicalRecord, medicalRecordToUpdate);
        MedicalRecord medicalRecordSaved = medicalRecordService.saveUpdated(
                medicalRecordUpdated);

        LOGGER.info("MedicalRecordController (PUT) -> Medical record "
                + "successfully " + "updated: "
                + medicalRecordUpdated.toString());
        return ResponseEntity.ok(medicalRecordSaved);

    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<Void> deleteMedicalRecord(
            @RequestParam(value = "firstName") final String firstName,
            @RequestParam(value = "lastName") final String lastName) {
        MedicalRecord medicalRecordToDelete = medicalRecordService.findByFirstNameAndLastName(
                firstName, lastName);
        medicalRecordService.deleteMedicalRecord(medicalRecordToDelete);
        LOGGER.info(
                "MedicalRecordController (DEL) -> Med: " + medicalRecordToDelete
                        .toString());
        return ResponseEntity.ok().build();

    }
}

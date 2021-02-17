package com.safetynet.alerts.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.ImportData;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Component
public class JsonReader {

    @Autowired
    private PersonService personService;

    @Autowired
    private FirestationService firestationService;

    @Autowired
    private MedicalRecordService medicalRecordService;



    public void readJsonAndSaveToDb() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<ImportData> typeReference = new TypeReference<ImportData>() {
        };
        File f = new File("src/main/resources/data.json");
        InputStream is = null;
        try {
            is = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImportData persList = null;
        try {
            persList = mapper.readValue(is, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Person> persons = persList.getPersons();
        List<Firestation> firestations = persList.getFireStations();
        List<MedicalRecord> medicalRecords = persList.getMedicalRecords();
        personService.saveAll(persons);
        firestationService.saveAll(firestations);
        medicalRecordService.saveAll(medicalRecords);
        System.out.println("Users saved");

    }

}

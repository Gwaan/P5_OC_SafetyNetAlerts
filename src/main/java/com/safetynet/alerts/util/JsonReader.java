package com.safetynet.alerts.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.ImportData;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        ObjectMapper mapper = null;
        InputStream is = null;
        File f = null;
        try {
            mapper = new ObjectMapper();
            f = new File("src/main/resources/data.json");
            is = new FileInputStream(f);
            ImportData persList = mapper.readValue(is, ImportData.class);
            List<Person> persons = persList.getPersons();
            List<Firestation> fireStations = persList.getFireStations();
            List<MedicalRecord> medicalRecords = persList.getMedicalRecords();
            personService.saveAll(persons);
            firestationService.saveAll(fireStations);
            medicalRecordService.saveAll(medicalRecords);
            System.out.println("Users saved");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

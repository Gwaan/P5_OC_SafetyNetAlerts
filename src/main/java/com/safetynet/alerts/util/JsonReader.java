package com.safetynet.alerts.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.ImportData;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOGGER = LogManager.getLogger(JsonReader.class);

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
            ImportData lists = mapper.readValue(is, ImportData.class);
            List<Person> persons = lists.getPersons();
            List<Firestation> fireStations = lists.getFireStations();
            List<MedicalRecord> medicalRecords = lists.getMedicalRecords();
            personService.saveAll(persons);
            firestationService.saveAll(fireStations);
            medicalRecordService.saveAll(medicalRecords);
            LOGGER.info("data.json successfully read and saved in db");

        } catch (FileNotFoundException e) {
            LOGGER.error("File data.json not found");
        } catch (JsonParseException e) {
            LOGGER.error("Error while parsing json");
        } catch (JsonMappingException e) {
            LOGGER.error("Error while mapping json");
        } catch (IOException e) {
            LOGGER.error("I/O error");
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                LOGGER.error("Error while closing the InputStream");
            }
        }
    }

}

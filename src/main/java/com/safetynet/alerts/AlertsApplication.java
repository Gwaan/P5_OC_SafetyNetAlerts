package com.safetynet.alerts;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.JsonReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
public class AlertsApplication implements CommandLineRunner {

    @Autowired
    private JsonReader jsonReader;


    public static void main(String[] args) {
        SpringApplication.run(AlertsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        jsonReader.readJsonAndSaveToDb();
    }
}

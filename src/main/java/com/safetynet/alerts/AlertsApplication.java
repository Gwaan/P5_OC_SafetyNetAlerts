package com.safetynet.alerts;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.util.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


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




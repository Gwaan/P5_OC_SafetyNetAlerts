package com.safetynet.alerts;

import com.safetynet.alerts.util.JsonReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Alerts application.
 *
 * @author Gwen
 * @version 1.0
 */
@SpringBootApplication
public class AlertsApplication implements CommandLineRunner {

    /**
     * @see JsonReader
     */
    private JsonReader jsonReader;

    /**
     * Instantiates a new Alerts application.
     *
     * @param jsonReader the json reader
     */
    public AlertsApplication(JsonReader jsonReader) {
        this.jsonReader = jsonReader;
    }

    /**
     * Main.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AlertsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        jsonReader.readJsonAndSaveToDb();
    }
}




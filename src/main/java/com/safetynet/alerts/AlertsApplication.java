package com.safetynet.alerts;

import com.safetynet.alerts.util.JsonReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
public class AlertsApplication implements CommandLineRunner {

    private JsonReader jsonReader;

    public AlertsApplication(JsonReader jsonReader) {
        this.jsonReader = jsonReader;
    }

    public static void main(String[] args) {
        SpringApplication.run(AlertsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        jsonReader.readJsonAndSaveToDb();
    }
}




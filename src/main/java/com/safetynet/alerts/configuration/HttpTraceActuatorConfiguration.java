package com.safetynet.alerts.configuration;


import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for HttpTrace actuator.
 *
 * @author Gwen
 * @version 1.0
 */
@Configuration
public class HttpTraceActuatorConfiguration {

    /**
     * Http trace actuator.
     *
     * @return the http trace repository
     */
    @Bean
    protected HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

}

package com.safetynet.alerts.configuration;


import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpTraceActuatorConfiguration {

    @Bean
    protected HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

}

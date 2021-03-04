package com.safetynet.alerts.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ChildAlertControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_Return_Children_By_Address() throws Exception {
        mockMvc
                .perform(get("/childAlert?address=1509 Culver St"))
                .andExpect(status().isOk());
    }

}

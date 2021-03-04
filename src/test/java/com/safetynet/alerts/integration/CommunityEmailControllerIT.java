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
public class CommunityEmailControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_Return_All_Mail_Addresses_From_City() throws Exception {
        mockMvc
                .perform(get("/communityEmail?city=Culver"))
                .andExpect(status().isOk());
    }


}

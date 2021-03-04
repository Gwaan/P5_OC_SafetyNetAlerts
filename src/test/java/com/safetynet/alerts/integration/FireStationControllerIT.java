package com.safetynet.alerts.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Firestation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_Return_All_Persons_Covered_By_Station_Number() throws Exception {
        mockMvc
                .perform(get("/firestation?stationNumber=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_Add_Fire_Station() throws Exception {
        Firestation firestation = new Firestation(1L, 1, "Test address");

        mockMvc
                .perform(post("/firestation")
                        .content(new ObjectMapper().writeValueAsString(
                                firestation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_Update_Fire_Station() throws Exception {
        Firestation firestation = new Firestation(1L, 1, "Test address");

        mockMvc
                .perform(put("/firestation?address=112 Steppes Pl&station=3")
                        .content(new ObjectMapper().writeValueAsString(
                                firestation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_Delete_Fire_Station() throws Exception {
        mockMvc
                .perform(delete("/firestation?address=112 Steppes "
                        + "Pl&station=4"))
                .andExpect(status().isOk());
    }

}

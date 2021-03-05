package com.safetynet.alerts.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Date;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_Return_All_Medical_Records() throws Exception {
        mockMvc.perform(get("/medicalRecord")).andExpect(status().isOk());
    }

    @Test
    public void should_Add_Medical_Record() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord(1L, "Test", "Test",
                new Date(), new String[]{"Test", "Test"},
                new String[]{"Test", "Test"});

        mockMvc
                .perform(post("/medicalRecord")
                        .content(new ObjectMapper().writeValueAsString(
                                medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_Update_Medical_Record() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord(1L, "Test", "Test",
                new Date(), new String[]{"Test", "Test"},
                new String[]{"Test", "Test"});

        mockMvc
                .perform(put("/medicalRecord?firstName=Tony&lastName=Cooper")
                        .content(new ObjectMapper().writeValueAsString(
                                medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_Delete_Medical_Record() throws Exception {
        mockMvc
                .perform(delete("/medicalRecord?firstName=Test&lastName=Test"))
                .andExpect(status().isOk());
    }

}

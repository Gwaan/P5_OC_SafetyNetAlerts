package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.util.JsonReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Date;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @MockBean
    private JsonReader jsonReader;

    @Test
    public void should_Return_All_Medical_Records() throws Exception {
        mockMvc.perform(get("/medicalRecord")).andExpect(status().isOk());
    }

    @Test
    public void should_Add_New_Medical_Record() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord(1L, "Test", "Test",
                new Date(), new String[]{"Test", "Test"},
                new String[]{"Test", "Test"});
        when(medicalRecordService.save(any(MedicalRecord.class))).thenReturn(
                medicalRecord);
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
        when(medicalRecordService.findByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(medicalRecord);
        when(medicalRecordService.updateMedicalRecord(any(MedicalRecord.class),
                any(MedicalRecord.class))).thenReturn(medicalRecord);
        when(medicalRecordService.saveUpdated(
                any(MedicalRecord.class))).thenReturn(medicalRecord);
        mockMvc
                .perform(put("/medicalRecord?firstName=Test&lastName=Test")
                        .content(new ObjectMapper().writeValueAsString(
                                medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_Delete_Medical_Record() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord(1L, "Test", "Test",
                new Date(), new String[]{"Test", "Test"},
                new String[]{"Test", "Test"});
        when(medicalRecordService.findByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(medicalRecord);
        doNothing()
                .when(medicalRecordService)
                .deleteMedicalRecord(medicalRecord);
        mockMvc
                .perform(delete("/medicalRecord?firstName=Test&lastName=Test"))
                .andExpect(status().isOk());
    }

}

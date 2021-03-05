package com.safetynet.alerts.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_Return_All_Persons() throws Exception {
        mockMvc.perform(get("/person")).andExpect(status().isOk());
    }

    @Test
    public void should_Add_Person() throws Exception {
        Person person = new Person(1L, "Test", "Test", "test address",
                "Test city", 12345, "0123456789", "test@test.test");

        mockMvc
                .perform(post("/person")
                        .content(new ObjectMapper().writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_Update_Person() throws Exception {
        Person person = new Person(1L, "Test", "Test", "test address",
                "Test city", 12345, "0123456789", "test@test.test");

        mockMvc
                .perform(put("/person?firstName=Lily&lastName=Cooper")
                        .content(new ObjectMapper().writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("0123456789")))
                .andExpect(content().string(containsString("test@test.test")));

    }

    @Test
    public void should_Delete_Person() throws Exception {
        mockMvc
                .perform(delete("/person?firstName=John&lastName=Boyd"))
                .andExpect(status().isOk());
    }

}

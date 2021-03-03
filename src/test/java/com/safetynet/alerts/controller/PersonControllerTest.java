package com.safetynet.alerts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.JsonReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private JsonReader jsonReader;


    @Test
    public void should_Return_All_Persons() throws Exception {
        mockMvc.perform(get("/person")).andExpect(status().isOk());
    }

    @Test
    public void should_Return_A_Person_With_FirstName_And_LastName() throws Exception {
        mockMvc
                .perform(get("/person/firstName=Test_lastName=Test"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_Add_New_Person() throws Exception {
        Person person = new Person(1L, "Test", "Test", "test address",
                "Test city", 12345, "0123456789", "test@test.test");
        when(personService.save(any(Person.class))).thenReturn(person);
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
        when(personService.findByFirstNameAndLastName(anyString(), anyString()))
                .thenReturn(person);
        when(personService.updatePerson(any(Person.class),
                any(Person.class))).thenReturn(person);
        when(personService.saveUpdated(any(Person.class))).thenReturn(person);
        mockMvc
                .perform(put("/person?firstName=Test&lastName=Test")
                        .content(new ObjectMapper().writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_Delete_Person() throws Exception {
        Person person = new Person(1L, "Test", "Test", "test address",
                "Test city", 12345, "0123456789", "test@test.test");
        when(personService.findByFirstNameAndLastName(anyString(), anyString()))
                .thenReturn(person);
        doNothing().when(personService).deletePerson(any(Person.class));
        mockMvc
                .perform(delete("/person?firstName=Test&lastName=Test"))
                .andExpect(status().isOk());
    }


}

package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.JsonReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = PhoneAlertController.class)
public class PhoneAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private JsonReader jsonReader;

    @Test
    public void should_Return_All_Phone_Number_From_Station() throws Exception {
        List<Person> personList = new ArrayList<>();
        personList.add(
                new Person(1L, "John", "Doe", "Test address", "Test city",
                        12345, "0123", "test@test" + ".test"));
        personList.add(
                new Person(2L, "Jane", "Doe", "Test address", "Test " + "city",
                        12345, "4567", "test@test" + ".test"));
        when(personService.getPhoneNumberByStation(anyInt())).thenReturn(
                personList);

        mockMvc
                .perform(get("/phoneAlert?firestation=3"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("0123")))
                .andExpect(content().string(containsString("4567")));
    }
}

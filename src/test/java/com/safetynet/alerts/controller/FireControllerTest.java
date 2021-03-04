package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.dto.PersonFireDTO;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = FireController.class)
public class FireControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private JsonReader jsonReader;

    @Test
    public void should_Return_A_List_Of_Persons_Covered_By_Station_Address() throws Exception {
        List<PersonFireDTO> personFireDTOList = new ArrayList<>();
        personFireDTOList.add(
                new PersonFireDTO(1, "Test", "Test", "testphone", 25,
                        new String[]{"test", "test"},
                        new String[]{"test", "test"}));
        personFireDTOList.add(
                new PersonFireDTO(1, "Test", "Test", "testphone", 15,
                        new String[]{"test", "test"},
                        new String[]{"test", "test"}));
        when(personService.getFireDtoListByStation(anyString())).thenReturn(
                personFireDTOList);

        mockMvc
                .perform(get("/fire?address=test address"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("25")))
                .andExpect(content().string(containsString("15")));

    }
}

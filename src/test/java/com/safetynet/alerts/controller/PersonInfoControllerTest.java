package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.dto.PersonInfoDTO;
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


@WebMvcTest(controllers = PersonInfoController.class)
public class PersonInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private JsonReader jsonReader;

    @Test
    public void should_Return_A_PersonInfoDTO_List() throws Exception {
        List<PersonInfoDTO> personInfoDTOList = new ArrayList<>();
        personInfoDTOList.add(
                new PersonInfoDTO("Test", "Test", "0123456789", 25,
                        new String[]{"test", "test"},
                        new String[]{"test", "test"}));
        personInfoDTOList.add(
                new PersonInfoDTO("Test", "Test", "0123456789", 50,
                        new String[]{"test", "test"},
                        new String[]{"test", "test"}));
        when(personService.getPersonInfoList(anyString(),
                anyString())).thenReturn(personInfoDTOList);

        mockMvc
                .perform(get("/personInfo?firstName=Test&lastName=Test"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("25")))
                .andExpect(content().string(containsString("50")));
    }

}

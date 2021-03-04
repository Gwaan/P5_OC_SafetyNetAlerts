package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.dto.ChildAlertDTO;
import com.safetynet.alerts.model.dto.PersonsCoveredByStationDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ChildAlertController.class)
public class ChildAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private JsonReader jsonReader;

    @Test
    public void should_Return_A_ChildAlertDTO_Object() throws Exception {
        ChildAlertDTO childAlertDTO = new ChildAlertDTO();
        List<PersonsCoveredByStationDTO> adults = new ArrayList<>();
        adults.add(
                new PersonsCoveredByStationDTO("Test", "Test", "Test address",
                        "Test city", 12345, "0123456789", 50));
        List<PersonsCoveredByStationDTO> children = new ArrayList<>();
        children.add(
                new PersonsCoveredByStationDTO("Test", "Test", "Test address",
                        "Test city", 12345, "0123456789", 16));
        childAlertDTO.setAdults(adults);
        childAlertDTO.setChildren(children);
        when(personService.getListOfChildrenByAddress(anyString())).thenReturn(
                childAlertDTO);

        mockMvc
                .perform(get("/childAlert?address=test address"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("50")))
                .andExpect(content().string(containsString("16")));


    }


}

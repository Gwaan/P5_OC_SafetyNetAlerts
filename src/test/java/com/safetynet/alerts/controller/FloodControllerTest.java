package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.AddressDTO;
import com.safetynet.alerts.model.dto.FloodDTO;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = FloodController.class)
public class FloodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private JsonReader jsonReader;

    @Test
    public void should_Return_A_FloodDTO_List() throws Exception {
        List<FloodDTO> floodDTOList = new ArrayList<>();
        List<AddressDTO> addressDTOList = new ArrayList<>();
        List<PersonInfoDTO> personInfoDTOList = new ArrayList<>();
        personInfoDTOList.add(
                new PersonInfoDTO("Test", "Test", "0123456789", 50,
                        new String[]{"test", "test"},
                        new String[]{"test", "test"}));
        personInfoDTOList.add(
                new PersonInfoDTO("Test", "Test", "0123456789", 18,
                        new String[]{"test", "test"},
                        new String[]{"test", "test"}));
        addressDTOList.add(new AddressDTO("test household", personInfoDTOList));
        floodDTOList.add(new FloodDTO(1, addressDTOList));
        when(personService.getFloodDtoListByStation(anyList())).thenReturn(
                floodDTOList);

        mockMvc
                .perform(get("/flood/stations?stations=1,2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test household")))
                .andExpect(content().string(containsString("50")));
    }


}

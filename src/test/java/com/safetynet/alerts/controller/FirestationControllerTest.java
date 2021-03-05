package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.CountAndPersonsCoveredDTO;
import com.safetynet.alerts.model.dto.PersonsCoveredByStationDTO;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.JsonReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = FirestationController.class)
public class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationService firestationService;

    @MockBean
    private PersonService personService;

    @MockBean
    private JsonReader jsonReader;

    @Test
    public void should_Return_All_Persons_Covered_By_Station() throws Exception {
        CountAndPersonsCoveredDTO countAndPersonsCoveredDTO = new CountAndPersonsCoveredDTO();
        List<PersonsCoveredByStationDTO> personsCoveredByStationDTOList = new ArrayList<>();
        PersonsCoveredByStationDTO personsCoveredByStationDTO1 = new PersonsCoveredByStationDTO(
                "Test", "Test", "Test address", "Test city", 12345,
                "0123456789", 15);
        PersonsCoveredByStationDTO personsCoveredByStationDTO2 = new PersonsCoveredByStationDTO(
                "Test", "Test", "Test address", "Test city", 12345,
                "0123456789", 20);
        personsCoveredByStationDTOList.add(personsCoveredByStationDTO1);
        personsCoveredByStationDTOList.add(personsCoveredByStationDTO2);
        countAndPersonsCoveredDTO.setPersonsCovered(
                personsCoveredByStationDTOList);
        countAndPersonsCoveredDTO.setCountOfChildren(1);
        countAndPersonsCoveredDTO.setCountOfAdults(1);
        when(personService.findPersonsWithStationNumber(anyInt())).thenReturn(
                countAndPersonsCoveredDTO);
        mockMvc
                .perform(get("/firestation?stationNumber=3"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("15")))
                .andExpect(content().string(containsString("20")));
    }

    @Test
    public void should_Add_Fire_Station() throws Exception {
        Firestation firestation = new Firestation(1l, 1, "Test Address");
        when(firestationService.save(any(Firestation.class))).thenReturn(
                firestation);

        mockMvc
                .perform(post("/firestation")
                        .content(new ObjectMapper().writeValueAsString(
                                firestation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_Update_Fire_Station() throws Exception {
        Firestation firestation = new Firestation(1l, 1, "Test Address");
        when(firestationService.findFirestationByAddressAndStation(anyString(),
                anyInt())).thenReturn(firestation);
        when(firestationService.updateFirestation(any(Firestation.class),
                any(Firestation.class))).thenReturn(firestation);
        when(firestationService.saveUpdated(any(Firestation.class))).thenReturn(
                firestation);

        mockMvc
                .perform(put("/firestation?address=Test&station=1")
                        .content(new ObjectMapper().writeValueAsString(
                                firestation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_Delete_Fire_Station() throws Exception {
        Firestation firestation = new Firestation(1l, 1, "Test Address");
        when(firestationService.findFirestationByAddressAndStation(anyString(),
                anyInt())).thenReturn(firestation);
        doNothing()
                .when(firestationService)
                .deleteFirestation(any(Firestation.class));

        mockMvc
                .perform(delete("/firestation?address=Test&station=3"))
                .andExpect(status().isOk());
    }

}

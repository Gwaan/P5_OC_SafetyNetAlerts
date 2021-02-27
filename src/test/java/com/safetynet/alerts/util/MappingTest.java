package com.safetynet.alerts.util;


import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.AddressDTO;
import com.safetynet.alerts.model.dto.ChildAlertDTO;
import com.safetynet.alerts.model.dto.CountAndPersonsCoveredDTO;
import com.safetynet.alerts.model.dto.FloodDTO;
import com.safetynet.alerts.model.dto.PersonFireDTO;
import com.safetynet.alerts.model.dto.PersonInfoDTO;
import com.safetynet.alerts.model.dto.PersonsCoveredByStationDTO;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MappingTest {

    private static Mapping mapping;

    @Mock
    private static MedicalRecordService medicalRecordService;

    @Mock
    private static PersonService personService;

    @Mock
    private static FirestationService firestationService;

    private static List<Person> personList = new ArrayList<>();

    private static Person person;

    @BeforeEach
    void beforeEach() {
        mapping = new Mapping(medicalRecordService, personService,
                firestationService);
        person = new Person(1L, "Test", "Test", "123 Test", "Testcity", 12345,
                "0123456789", "test@test.fr");
        for (int i = 0; i < 3; i++) {
            personList.add(new Person(1L, "Test first name", "Test last name",
                    "Test address", "Test city", 12345, "Test phone",
                    "test@test" + ".test"));
        }
    }

    @Test
    public void should_Return_A_PersonCoveredByStationDTO_List() {
        // ARRANGE

        // ACT
        List<PersonsCoveredByStationDTO> personInfoDTOList = mapping.convertPersonListToPersonsCoveredByStationDtoList(
                personList);


        // ASSERT
        assertTrue(
                personInfoDTOList.get(0) instanceof PersonsCoveredByStationDTO);
        assertNotNull(personInfoDTOList);

    }

    @Test
    public void should_Return_A_PersonsCoveredByStationDTO_Object() {
        // ARRANGE
        when(personService.getAge(anyString(), anyString())).thenReturn(19);

        // ACT
        PersonsCoveredByStationDTO personsCoveredByStationDTO = mapping.convertPersonToPersonsCoveredByStationsDto(
                person);

        // ASSERT
        assertTrue(
                personsCoveredByStationDTO instanceof PersonsCoveredByStationDTO);
        assertEquals(19, personsCoveredByStationDTO.getAge());
    }

    @Test
    public void should_Return_A_PersonInfoDto_List() {
        // ARRANGE

        // ACT
        List<PersonInfoDTO> personInfoDTOList = mapping.convertPersonListToPersonInfoDtoList(
                personList);

        // ASSERT
        assertTrue(personInfoDTOList.get(0) instanceof PersonInfoDTO);
        assertNotNull(personInfoDTOList);

    }

    @Test
    public void should_Return_A_PersonInfoDto_Object() {
        // ARRANGE
        when(personService.getAge(anyString(), anyString())).thenReturn(19);

        // ACT
        PersonInfoDTO personInfoDTO = mapping.convertPersonToPersonInfoDto(
                person);

        // ASSERT
        assertTrue(personInfoDTO instanceof PersonInfoDTO);
        assertEquals(19, personInfoDTO.getAge());
    }

    @Test
    public void should_Return_A_PersonFireDTO_List() {
        // ARRANGE

        // ACT
        List<PersonFireDTO> personFireDTOList = mapping.convertPersonListToPersonFireList(
                personList, 1);

        // ASSERT
        assertTrue(personFireDTOList.get(0) instanceof PersonFireDTO);
        assertEquals(personFireDTOList.get(0).getStationNumber(), 1);
    }

    @Test
    public void should_Return_A_PersonFireDTO_Object() {
        // ARRANGE
        when(personService.getAge(anyString(), anyString())).thenReturn(20);
        when(medicalRecordService.getAllergies(anyString(),
                anyString())).thenReturn(new String[]{"test", "test"});
        ;
        when(medicalRecordService.getMedications(anyString(),
                anyString())).thenReturn(new String[]{"test", "test"});

        // ACT
        PersonFireDTO personFireDTO = mapping.convertPersonToPersonFireDto(
                person);

        // ASSERT
        assertTrue(personFireDTO instanceof PersonFireDTO);
        assertNotNull(personFireDTO);
        assertNotNull(personFireDTO.getAllergies());
        assertNotNull(personFireDTO.getMedications());
    }

    @Test
    public void should_Return_A_CountsAndPersonsCoveredDTO_Object() {
        // ARRANGE
        when(personService.countNumberOfChildren(anyList())).thenReturn(10);

        // ACT
        CountAndPersonsCoveredDTO countAndPersonsCoveredDTO = mapping.convertPersonListToCountAndPersonsCoveredDTO(
                personList);

        // ASSERT
        assertTrue(
                countAndPersonsCoveredDTO instanceof CountAndPersonsCoveredDTO);
    }

    @Test
    public void should_Return_A_ChildAlertDTO_Object() {
        // ARRANGE
        when(personService.getAge(anyString(), anyString())).thenReturn(19);

        // ACT
        ChildAlertDTO childAlertDTO = mapping.createChildAlertDto(personList);

        // ASSERT
        assertTrue(childAlertDTO instanceof ChildAlertDTO);
    }

    @Test
    public void should_Return_An_AddressDTO_Object() {
        // ARRANGE

        // ACT
        AddressDTO addressDTO = mapping.createAddressDto("test", personList);

        // ASSERT
        assertTrue(addressDTO instanceof AddressDTO);
    }

    @Test
    public void should_Return_A_FloodDTO_Object() {
        // ARRANGE
        List<AddressDTO> addressDTOList = new ArrayList<>();

        // ACT
        FloodDTO floodDTO = mapping.createFloodDTO(1, addressDTOList);

        // ASSERT
        assertTrue(floodDTO instanceof FloodDTO);
    }


}

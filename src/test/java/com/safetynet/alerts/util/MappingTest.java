package com.safetynet.alerts.util;


import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.AddressDTO;
import com.safetynet.alerts.model.dto.ChildAlertDTO;
import com.safetynet.alerts.model.dto.CountAndPersonsCoveredDTO;
import com.safetynet.alerts.model.dto.FloodDTO;
import com.safetynet.alerts.model.dto.PersonFireDTO;
import com.safetynet.alerts.model.dto.PersonInfoDTO;
import com.safetynet.alerts.model.dto.PersonsCoveredByStationDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MappingTest {

    private static Mapping mapping;

    @Mock
    private static AgeCountCalculator ageCountCalculator;

    private static List<Person> personList;

    private static List<MedicalRecord> medicalRecordList;

    private static Person person;

    private static MedicalRecord medicalRecord;

    @BeforeEach
    void beforeEach() {
        mapping = new Mapping(ageCountCalculator);
        person = new Person(1L, "Test", "Test", "123 Test", "Testcity", 12345,
                "0123456789", "test@test.fr");
        medicalRecord = new MedicalRecord(1L, "Tes", "Test",
                new Date(1991 + 1900, 8, 28), new String[]{"test", "test12"},
                new String[]{"test", "test123"});

        personList = new ArrayList<>();
        medicalRecordList = new ArrayList<>();
        personList.add(
                new Person(1L, "John", "Doe", "Test address", "Test city",
                        12345, "Test phone", "test@test" + ".test"));
        personList.add(
                new Person(1L, "Jane", "Doe", "Test address", "Test city",
                        12345, "Test phone", "test@test" + ".test"));
        personList.add(
                new Person(1L, "Test", "Test", "Test address", "Test city",
                        12345, "Test phone", "test@test" + ".test"));


        medicalRecordList.add(new MedicalRecord(1L, "John", "Doe",
                new Date(1991 + 1900, 8, 28), new String[]{"test", "test12"},
                new String[]{"test", "test123"}));
        medicalRecordList.add(new MedicalRecord(1L, "Jane", "Doe",
                new Date(1991 + 1900, 8, 28), new String[]{"test", "test12"},
                new String[]{"test", "test123"}));
        medicalRecordList.add(new MedicalRecord(1L, "Test", "Test",
                new Date(1991 + 1900, 8, 28), new String[]{"test", "test12"},
                new String[]{"test", "test123"}));

    }

    @AfterEach
    void afterEach() {
        personList = null;
        medicalRecordList = null;
        person = null;
        medicalRecord = null;
    }


    @Test
    public void should_Return_A_PersonCoveredByStationDTO_List() {

        List<PersonsCoveredByStationDTO> personInfoDTOList = mapping.convertPersonListToPersonsCoveredByStationDtoList(
                personList, medicalRecordList);

        assertTrue(
                personInfoDTOList.get(0) instanceof PersonsCoveredByStationDTO);
    }

    @Test
    public void should_Return_A_PersonsCoveredByStationDTO_Object() {
        when(ageCountCalculator.calculateAge(any(Date.class))).thenReturn(29);

        PersonsCoveredByStationDTO personsCoveredByStationDTO = mapping.convertPersonToPersonsCoveredByStationsDto(
                person, medicalRecord);

        assertTrue(
                personsCoveredByStationDTO instanceof PersonsCoveredByStationDTO);
        assertEquals(29, personsCoveredByStationDTO.getAge());
        verify(ageCountCalculator, times(1)).calculateAge(any());

    }

    @Test
    public void should_Return_A_PersonInfoDTO_List() {
        List<PersonInfoDTO> personInfoDTOList = mapping.convertPersonListToPersonInfoDtoList(
                personList, medicalRecordList);

        assertTrue(personInfoDTOList.get(0) instanceof PersonInfoDTO);
    }

    @Test
    public void should_Return_A_PersonInfoDTO_Object() {
        when(ageCountCalculator.calculateAge(any(Date.class))).thenReturn(29);

        PersonInfoDTO personInfoDTO = mapping.convertPersonToPersonInfoDto(
                person, medicalRecord);

        assertTrue(personInfoDTO instanceof PersonInfoDTO);
        assertEquals(29, personInfoDTO.getAge());
        assertEquals(medicalRecord.getAllergies(),
                personInfoDTO.getAllergies());
        assertEquals(medicalRecord.getMedications(),
                personInfoDTO.getMedications());
        verify(ageCountCalculator, times(1)).calculateAge(any());

    }

    @Test
    public void should_Not_Display_MedicalRecord_If_Its_Null_For_PersonInfoDTO() {
        PersonInfoDTO personInfoDTO = mapping.convertPersonToPersonInfoDto(
                person, null);

        assertEquals(-1, personInfoDTO.getAge());
    }

    @Test
    public void should_Return_A_PersonFireDTO_List() {
        List<PersonFireDTO> personFireDTOList = mapping.convertPersonListToPersonFireList(
                personList, 1, medicalRecordList);

        assertTrue(personFireDTOList.get(0) instanceof PersonFireDTO);
        assertEquals(1, personFireDTOList.get(0).getStationNumber());
    }

    @Test
    public void should_Return_A_PersonFireDTO_Object() {
        when(ageCountCalculator.calculateAge(any(Date.class))).thenReturn(29);

        PersonFireDTO personFireDTO = mapping.convertPersonToPersonFireDto(
                person, medicalRecord);

        assertTrue(personFireDTO instanceof PersonFireDTO);
        assertEquals(29, personFireDTO.getAge());
        assertEquals(medicalRecord.getMedications(),
                personFireDTO.getMedications());
        assertEquals(medicalRecord.getAllergies(),
                personFireDTO.getAllergies());
        verify(ageCountCalculator, times(1)).calculateAge(any());
    }

    @Test
    public void should_Not_Display_MedicalRecord_If_Its_Null_For_PersonFireDTO() {
        PersonFireDTO personFireDTO = mapping.convertPersonToPersonFireDto(
                person, null);

        assertEquals(-1, personFireDTO.getAge());
    }

    @Test
    public void should_Return_A_CountAndPersonsCoveredDTO_List() {
        when(ageCountCalculator.countNumberOfChildren(anyList())).thenReturn(2);

        CountAndPersonsCoveredDTO countAndPersonsCoveredDTO = mapping.convertPersonListToCountAndPersonsCoveredDTO(
                personList, medicalRecordList);

        assertTrue(
                countAndPersonsCoveredDTO instanceof CountAndPersonsCoveredDTO);
        assertEquals(2, countAndPersonsCoveredDTO.getCountOfChildren());
        assertEquals(1, countAndPersonsCoveredDTO.getCountOfAdults());

    }

    @Test
    public void should_Return_A_ChildAlertDTO_Object_Filled_With_Children() {
        when(ageCountCalculator.calculateAge(any(Date.class))).thenReturn(18);

        ChildAlertDTO childAlertDTO = mapping.createChildAlertDto(personList,
                medicalRecordList);

        assertTrue(childAlertDTO instanceof ChildAlertDTO);
        assertEquals(3, childAlertDTO.getChildren().size());
        assertEquals(0, childAlertDTO.getAdults().size());
        verify(ageCountCalculator, times(6)).calculateAge(any());
    }
    @Test
    public void should_Return_A_ChildAlertDTO_Object_Filled_With_Adults() {
        when(ageCountCalculator.calculateAge(any(Date.class))).thenReturn(20);

        ChildAlertDTO childAlertDTO = mapping.createChildAlertDto(personList,
                medicalRecordList);

        assertTrue(childAlertDTO instanceof ChildAlertDTO);
        assertEquals(0, childAlertDTO.getChildren().size());
        assertEquals(3, childAlertDTO.getAdults().size());
        verify(ageCountCalculator, times(6)).calculateAge(any());
    }

    @Test
    public void should_Return_An_AddressDTO_Object() {
        AddressDTO addressDTO = mapping.createAddressDto("TestAddress",
                personList, medicalRecordList);

        assertTrue(addressDTO instanceof AddressDTO);
        assertEquals("TestAddress", addressDTO.getHouseHold());
    }

    @Test
    public void should_Return_A_FloodDTO_Object() {
        List<AddressDTO> addressDTOList = new ArrayList<>();

        FloodDTO floodDTO = mapping.createFloodDTO(1, addressDTOList);

        assertTrue(floodDTO instanceof FloodDTO);
    }

    @Test
    public void should_Return_A_MedicalRecord_Mapped_With_A_PersonsCoveredByStationDTO_Object() {
        PersonsCoveredByStationDTO personsCoveredByStationDTO = new PersonsCoveredByStationDTO();
        personsCoveredByStationDTO.setFirstName("John");
        personsCoveredByStationDTO.setLastName("Doe");

        MedicalRecord medicalRecord = mapping.mapMedicalRecordsWithPersonCoveredByStationDTO(
                personsCoveredByStationDTO, medicalRecordList);

        assertEquals(personsCoveredByStationDTO.getFirstName(),
                medicalRecord.getFirstName());
        assertEquals(personsCoveredByStationDTO.getLastName(),
                medicalRecord.getLastName());
    }

    @Test
    public void should_Return_A_MedicalRecord_Mapped_With_A_Person_Object() {

        MedicalRecord medicalRecord = mapping.mapMedicalRecordsWithPerson(
                person, medicalRecordList);

        assertEquals(person.getFirstName(), medicalRecord.getFirstName());
        assertEquals(person.getLastName(), medicalRecord.getLastName());

    }

    @Test
    public void should_Return_A_MedicalRecord_Mapped_With_A_PersonInfoDTO_Object() {
        PersonInfoDTO personInfoDTO = new PersonInfoDTO();
        personInfoDTO.setFirstName("Jane");
        personInfoDTO.setLastName("Doe");

        MedicalRecord medicalRecord = mapping.mapMedicalRecordsWithPersonInfoDTO(
                personInfoDTO, medicalRecordList);

        assertEquals(personInfoDTO.getFirstName(),
                medicalRecord.getFirstName());
        assertEquals(personInfoDTO.getLastName(), medicalRecord.getLastName());

    }


}

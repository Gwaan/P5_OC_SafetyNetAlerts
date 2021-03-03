package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.AlreadyExistingException;
import com.safetynet.alerts.exceptions.NotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.AddressDTO;
import com.safetynet.alerts.model.dto.ChildAlertDTO;
import com.safetynet.alerts.model.dto.CountAndPersonsCoveredDTO;
import com.safetynet.alerts.model.dto.FloodDTO;
import com.safetynet.alerts.model.dto.PersonFireDTO;
import com.safetynet.alerts.model.dto.PersonInfoDTO;
import com.safetynet.alerts.model.dto.PersonsCoveredByStationDTO;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.util.Mapping;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private Mapping mapping;

    @Mock
    private FirestationService firestationService;

    @Mock
    private MedicalRecordService medicalRecordService;

    private static List<Person> personList;

    private static List<MedicalRecord> medicalRecordList;

    private static Person person;

    private static List<Integer> listOfIntegers;

    private MedicalRecord medicalRecord;

    private List<Person> emptyList;


    @BeforeEach
    void beforeEach() {
        personList = new ArrayList<>();
        medicalRecordList = new ArrayList<>();
        listOfIntegers = new ArrayList<>();
        emptyList = new ArrayList<>();
        listOfIntegers.add(1);
        listOfIntegers.add(2);
        personService = new PersonService(personRepository, mapping,
                firestationService, medicalRecordService);
        person = new Person(1L, "Test", "Test", "123 Test", "Testcity", 12345,
                "0123456789", "test@test.fr");
        medicalRecord = new MedicalRecord(1L, "Tes", "Test",
                new Date(1991 + 1900, 8, 28), new String[]{"test", "test12"},
                new String[]{"test", "test123"});
        for (int i = 0; i < 3; i++) {
            personList.add(
                    new Person(1L, "Test", "Test", "Test address", "Test city",
                            12345, "Test phone", "test@test" + ".test"));
        }
        medicalRecordList.add(new MedicalRecord(1L, "Test", "Test",
                new Date(1991 + 1900, 8, 28), new String[]{"test", "test12"},
                new String[]{"test", "test123"}));
        medicalRecordList.add(new MedicalRecord(1L, "Test", "Test",
                new Date(1991 + 1900, 8, 28), new String[]{"test", "test12"},
                new String[]{"test", "test123"}));
        medicalRecordList.add(new MedicalRecord(1L, "Test", "Test",
                new Date(1991 + 1900, 8, 28), new String[]{"test", "test12"},
                new String[]{"test", "test123"}));

    }

    @AfterEach
    void afterEach() {
        person = null;
        personList = null;
        medicalRecord = null;
        medicalRecordList = null;
        listOfIntegers = null;
        emptyList = null;
    }

    @Test
    public void should_Return_All_Persons_From_Db() {
        when(personRepository.findAll()).thenReturn(personList);

        personService.findAll();

        verify(personRepository, times(1)).findAll();
    }

    @Test
    public void should_Throw_AlreadyExistingException_If_Person_To_Save_Is_Existing() {
        when(personRepository.existsPersonsByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(true);
        Person personExisting = new Person();
        personExisting.setFirstName("John");
        personExisting.setLastName("Boyd");

        assertThrows(AlreadyExistingException.class,
                () -> personService.save(personExisting));
    }

    @Test
    public void should_Return_Person_Saved_In_Db() {
        when(personRepository.save(person)).thenReturn(person);

        Person expectedPerson = personService.save(person);

        verify(personRepository, times(1)).save(person);
        assertEquals(person, expectedPerson);
    }

    @Test
    public void should_Return_Person_Updated_In_Db() {
        when(personRepository.save(person)).thenReturn(person);

        Person expectedPerson = personService.saveUpdated(person);

        verify(personRepository, times(1)).save(person);
        assertEquals(person, expectedPerson);
    }

    @Test
    public void should_Save_A_List_Of_Person() {
        when(personRepository.saveAll(personList)).thenReturn(personList);

        List<Person> expectedList = (List<Person>) personService.saveAll(
                personList);

        verify(personRepository, times(1)).saveAll(personList);
        assertEquals(personList, expectedList);

    }

    @Test
    public void should_Find_A_Person_With_FirstName_And_LastName() {
        when(personRepository.findByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(person);

        Person expectedPerson = personService.findByFirstNameAndLastName("test",
                "test");

        verify(personRepository, times(1)).findByFirstNameAndLastName(
                anyString(), anyString());
        assertEquals(expectedPerson, person);

    }

    @Test
    public void should_Throws_A_NotFoundException_When_Person_Is_Null() {
        when(personRepository.findByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(null);

        assertThrows(NotFoundException.class,
                () -> personService.findByFirstNameAndLastName("test", "test"));
    }

    @Test
    public void should_Return_A_List_Of_Person_With_FirstName_And_LastName() {
        when(personRepository.findPersonsByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(personList);

        List<Person> expectedPersons = personService.findPersonsByFirstNameAndLastName(
                "test", "test");

        verify(personRepository, times(1)).findPersonsByFirstNameAndLastName(
                anyString(), anyString());
        assertEquals(expectedPersons, personList);
    }

    @Test
    public void should_Throws_NotFoundException_When_List_Of_Persons_By_FirstName_And_FirstName_Is_Empty() {
        List<Person> emptyList = new ArrayList<>();
        when(personRepository.findPersonsByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(emptyList);
        assertThrows(NotFoundException.class,
                () -> personService.findPersonsByFirstNameAndLastName("test",
                        "test"));
    }

    @Test
    public void should_Delete_A_Person_From_Db() {
        doNothing().when(personRepository).delete(person);
        personService.deletePerson(person);
        verify(personRepository, times(1)).delete(person);
    }

    @Test
    public void should_Update_Person_From_Db() {
        Person personFromBody = new Person();
        personFromBody.setAddress("12345 Test");
        personFromBody.setCity("city test");
        personFromBody.setEmail("john.doe@test.com");
        personFromBody.setPhone("0123456789");
        personFromBody.setZip(54321);

        Person personUpdated = personService.updatePerson(personFromBody,
                personFromBody);

        assertEquals(personUpdated.getAddress(), personFromBody.getAddress());
        assertEquals(personUpdated.getCity(), personFromBody.getCity());
        assertEquals(personUpdated.getEmail(), personFromBody.getEmail());
        assertEquals(personUpdated.getPhone(), personFromBody.getPhone());
        assertEquals(personUpdated.getZip(), personFromBody.getZip());
    }

    @Test
    public void should_Return_True_Or_False_If_A_Person_Exists_Or_Not_In_Db() {
        when(personRepository.existsPersonsByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(true);

        boolean expected = personService.existsPersonByFirstNameAndLastName(
                "test", "test");

        verify(personRepository, times(1)).existsPersonsByFirstNameAndLastName(
                anyString(), anyString());
        assertTrue(expected);
    }

    @Test
    public void should_Return_A_List_Of_Persons_By_Address() {
        when(personRepository.findPersonByAddress(anyString())).thenReturn(
                personList);

        List<Person> expectedPersons = personService.findPersonByAddress(
                "test");

        assertEquals(expectedPersons, personList);
        verify(personRepository, times(1)).findPersonByAddress(anyString());
    }

    @Test
    public void should_Throws_NotFoundException_When_No_Person_With_Address_Is_Found() {
        when(personRepository.findPersonByAddress(anyString())).thenReturn(
                emptyList);
        assertThrows(NotFoundException.class,
                () -> personService.findPersonByAddress("test"));
    }

    @Test
    public void should_Return_A_List_Of_Persons_By_Station() {
        when(personRepository.findPersonByStation(anyInt())).thenReturn(
                personList);

        List<Person> expectedPersons = personService.findPersonByStation(1);

        assertEquals(expectedPersons, personList);
        verify(personRepository, times(1)).findPersonByStation(anyInt());
    }

    @Test
    public void should_Throws_NotFoundException_When_No_Person_With_Station_Is_Found() {
        List<Person> emptyList = new ArrayList<>();
        when(personRepository.findPersonByStation(anyInt())).thenReturn(
                emptyList);
        assertThrows(NotFoundException.class,
                () -> personService.findPersonByStation(1));
    }

    @Test
    public void should_Return_A_CountAndPersonsCoveredDto() {
        when(personRepository.findPersonsWithStationNumber(1)).thenReturn(
                personList);
        when(mapping.convertPersonListToCountAndPersonsCoveredDTO(anyList(),
                anyList())).thenReturn(new CountAndPersonsCoveredDTO());

        CountAndPersonsCoveredDTO countAndPersonsCoveredDTO = personService.findPersonsWithStationNumber(
                1);

        assertTrue(
                countAndPersonsCoveredDTO instanceof CountAndPersonsCoveredDTO);
    }

    @Test
    public void should_Return_A_PersonFireDTO_List() {
        List<PersonFireDTO> personFireDTOList = new ArrayList<>();
        personFireDTOList.add(new PersonFireDTO());
        when(firestationService.findStationByAddress(anyString())).thenReturn(
                listOfIntegers);
        when(personRepository.findPersonByStation(anyInt())).thenReturn(
                personList);
        when(mapping.convertPersonListToPersonFireList(anyList(), anyInt(),
                anyList())).thenReturn(personFireDTOList);

        List<PersonFireDTO> personFireDTOListExpected = personService.getFireDtoListByStation(
                "test");

        assertTrue(personFireDTOListExpected.get(0) instanceof PersonFireDTO);
    }

    @Test
    public void should_Return_A_FloodDTO_List() {
        FloodDTO floodDTO = new FloodDTO();
        AddressDTO addressDTO = new AddressDTO();
        List<String> listOfString = new ArrayList<>();
        listOfString.add("test");
        List<AddressDTO> addressDTOList = new ArrayList<>();
        addressDTOList.add(addressDTO);
        when(firestationService.findByStation(anyInt())).thenReturn(
                listOfString);
        when(personRepository.findPersonByAddress(anyString())).thenReturn(
                personList);
        when(medicalRecordService.findByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(medicalRecord);
        when(mapping.createAddressDto(anyString(), anyList(),
                anyList())).thenReturn(addressDTO);
        when(mapping.createFloodDTO(1, addressDTOList)).thenReturn(floodDTO);

        List<FloodDTO> floodDTOListExpected = personService.getFloodDtoListByStation(
                listOfIntegers);

        assertTrue(floodDTOListExpected.get(0) instanceof FloodDTO);
    }

    @Test
    public void should_Return_A_ChildAlertDTO_Object() {
        ChildAlertDTO childAlertDTO = new ChildAlertDTO();
        List<PersonsCoveredByStationDTO> personsCoveredByStationDTOListChildren = new ArrayList<>();
        personsCoveredByStationDTOListChildren.add(
                new PersonsCoveredByStationDTO());
        List<PersonsCoveredByStationDTO> personsCoveredByStationDTOListAdult = new ArrayList<>();
        personsCoveredByStationDTOListAdult.add(
                new PersonsCoveredByStationDTO());
        childAlertDTO.setChildren(personsCoveredByStationDTOListChildren);
        childAlertDTO.setAdults(personsCoveredByStationDTOListAdult);

        when(personRepository.findPersonByAddress(anyString())).thenReturn(
                personList);
        when(medicalRecordService.findByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(medicalRecord);
        when(mapping.createChildAlertDto(anyList(), anyList())).thenReturn(
                childAlertDTO);

        ChildAlertDTO childAlertDT0Expected = personService.getListOfChildrenByAddress(
                "test");

        assertTrue(childAlertDT0Expected instanceof ChildAlertDTO);

    }

    @Test
    public void should_Return_A_PersonInfoList() {
        List<PersonInfoDTO> personInfoDTOList = new ArrayList<>();
        personInfoDTOList.add(new PersonInfoDTO());
        when(personRepository.findPersonsByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(personList);
        when(medicalRecordService.findMedicalRecordsByFirstNameAndLastName(
                anyString(), anyString())).thenReturn(medicalRecordList);
        when(mapping.convertPersonListToPersonInfoDtoList(anyList(), anyList()))
                .thenReturn(personInfoDTOList);

        List<PersonInfoDTO> personInfoDTOListExpected = personService.getPersonInfoList(
                "test", "test");

        assertTrue(personInfoDTOListExpected.get(0) instanceof PersonInfoDTO);
    }

    @Test
    public void should_Return_A_List_Of_Persons_To_Get_Their_Mails() {
        when(personRepository.findMailAddressesFromCity(
                anyString())).thenReturn(personList);

        List<Person> personListExpected = personService.getMailAddressesFromCity(
                "testcity");

        assertTrue(personListExpected.get(0) instanceof Person);
        assertEquals("test@test.test", personListExpected.get(0).getEmail());
    }

    @Test
    public void should_Throws_NotFoundException_When_City_Is_Not_Existing() {
        when(personRepository.findMailAddressesFromCity(
                anyString())).thenReturn(emptyList);

        assertThrows(NotFoundException.class,
                () -> personService.getMailAddressesFromCity("test"));
    }

    @Test
    public void should_Return_A_List_Of_Persons_To_Get_Their_Phone_Number() {
        when(personRepository.findPhoneNumberByStation(anyInt())).thenReturn(
                personList);

        List<Person> personListExpected = personService.getPhoneNumberByStation(
                1);

        assertTrue(personListExpected.get(0) instanceof Person);
        assertEquals("Test phone", personListExpected.get(0).getPhone());
    }

    @Test
    public void should_Throws_NotFoundException_When_No_Person_Is_Covered_By_Station_Number() {
        when(personRepository.findPhoneNumberByStation(anyInt())).thenReturn(
                emptyList);

        assertThrows(NotFoundException.class,
                () -> personService.getPhoneNumberByStation(1));
    }

}

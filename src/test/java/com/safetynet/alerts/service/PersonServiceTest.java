package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.NotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.CountAndPersonsCoveredDTO;
import com.safetynet.alerts.model.dto.PersonFireDTO;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.util.Mapping;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
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

    @BeforeEach
    void beforeEach() {
        personList = new ArrayList<>();
        medicalRecordList = new ArrayList<>();
        listOfIntegers = new ArrayList<>();
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
    }

    @Test
    public void should_Return_All_Persons_From_Db() {
        when(personRepository.findAll()).thenReturn(personList);

        personService.findAll();

        verify(personRepository, times(1)).findAll();
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
        List<Person> emptyList = new ArrayList<>();
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
    public void should_Throws_NoFoundException_When_No_Person_With_Station_Is_Found() {
        assertThrows(NotFoundException.class,
                () -> personService.findPersonsWithStationNumber(1));
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

    /*@Test
    public void should_Return_A_PersonFireDTO_List() {
        List<PersonFireDTO> personFireDTOList = new ArrayList<>();
        when(firestationService.findStationByAddress(anyString())).thenReturn(
                listOfIntegers);

        List<PersonFireDTO> personFireDTOListExpected = personService.getFireDtoListByStation(
                "test");

        assertTrue(personFireDTOList.get(0) instanceof PersonFireDTO);
    }*/

}

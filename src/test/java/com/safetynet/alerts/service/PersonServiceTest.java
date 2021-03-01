/*package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.NotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.util.AgeCalculator;
import com.safetynet.alerts.util.Mapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AgeCalculator ageCalculator;

    @Mock
    private Mapping mapping;

    @Mock
    private FirestationService firestationService;

    private static List<Person> personList = new ArrayList<>();

    private static Person person;

    @BeforeEach
    void beforeEach() {
        personService = new PersonService(personRepository, ageCalculator,
                mapping, firestationService);
        person = new Person(1L, "Test", "Test", "123 Test", "Testcity", 12345,
                "0123456789", "test@test.fr");
        for (int i = 0; i < 3; i++) {
            personList.add(new Person(1L, "Test first name", "Test last name",
                    "Test address", "Test city", 12345, "Test phone",
                    "test@test" + ".test"));
        }

    }

    @Test
    public void should_Return_All_Persons_From_Db() {
        // ARRANGE
        when(personRepository.findAll()).thenReturn(personList);

        // ACT
        personService.findAll();

        // ASSERT
        verify(personRepository, times(1)).findAll();
    }

    @Test
    public void should_Throws_NotFoundException_If_Person_Is_Already_Existing_In_Db() {
        // ARRANGE
        when(personService.existsPersonByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(false);

        // ACT
        personService.save(person);

        // ASSERT
        assertThrows(NotFoundException.class, () -> personService.save(person));
    }

}*/

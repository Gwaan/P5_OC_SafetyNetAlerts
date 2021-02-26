package com.safetynet.alerts.util;


import com.safetynet.alerts.model.Person;
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
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonMappingTest {

    private static PersonMapping personMapping;

    @Mock
    private static MedicalRecordService medicalRecordService;

    @Mock
    private static PersonService personService;

    @Mock
    private static FirestationService firestationService;

    @BeforeEach
    void beforeEach() {
        personMapping = new PersonMapping(medicalRecordService, personService,
                firestationService);
    }

    @Test
    public void should_Return_A_PersonCoveredByStationDto_Object() {
        // ARRANGE
        Person person = new Person(1L, "Test", "Test", "123 Test", "Testcity",
                12345, "0123456789", "test@test.fr");

        when(personService.getAge(anyString(), anyString())).thenReturn(19);

        // ACT
        PersonInfoDTO personInfoDTO = personMapping.convertPersonToPersonInfoDto(
                person);

        // ASSERT
        assertTrue(personInfoDTO instanceof PersonInfoDTO);
    }

}

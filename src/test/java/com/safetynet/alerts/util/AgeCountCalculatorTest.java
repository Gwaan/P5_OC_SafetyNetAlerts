package com.safetynet.alerts.util;

import com.safetynet.alerts.model.dto.PersonsCoveredByStationDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AgeCountCalculatorTest {

    private AgeCountCalculator ageCountCalculator;

    @BeforeEach
    public void setUpPerTest() {
        ageCountCalculator = new AgeCountCalculator();
    }

    @AfterEach
    public void afterEachTest() {
        ageCountCalculator = null;
    }


    @Test
    public void Should_Calculate_Age_When_Date_Of_Birth_Is_Not_Null() {
        // ARRANGE
        //LocalDate dateOfBirth = LocalDate.of(2011, 02, 25);
        Date dateOfBirth = new Date(2010 - 1900, 02, 01);

        // ACT
        int age = ageCountCalculator.calculateAge(dateOfBirth);

        // ARRANGE
        assertEquals(11, age);
    }

    @Test
    public void should_Return_0_When_Date_Of_Birth_Is_Null() {
        // ARRANGE
        Date dateOfBirth = null;

        // ACT
        int age = ageCountCalculator.calculateAge(dateOfBirth);

        // ARRANGE
        assertEquals(0, age);
    }

    @Test
    public void should_Return_Count_Of_Children() {
        List<PersonsCoveredByStationDTO> personsCoveredByStationDTOList = new ArrayList<>();
        PersonsCoveredByStationDTO personsCoveredByStationDTO1 = new PersonsCoveredByStationDTO();
        PersonsCoveredByStationDTO personsCoveredByStationDTO2 = new PersonsCoveredByStationDTO();
        PersonsCoveredByStationDTO personsCoveredByStationDTO3 = new PersonsCoveredByStationDTO();
        personsCoveredByStationDTO1.setAge(15);
        personsCoveredByStationDTO2.setAge(20);
        personsCoveredByStationDTO3.setAge(18);
        personsCoveredByStationDTOList.add(personsCoveredByStationDTO1);
        personsCoveredByStationDTOList.add(personsCoveredByStationDTO2);
        personsCoveredByStationDTOList.add(personsCoveredByStationDTO3);

        int countOfChildrenExpected = ageCountCalculator.countNumberOfChildren(
                personsCoveredByStationDTOList);

        assertEquals(2, countOfChildrenExpected);

    }

}

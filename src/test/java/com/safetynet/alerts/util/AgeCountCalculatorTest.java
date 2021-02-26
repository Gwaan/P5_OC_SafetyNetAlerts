package com.safetynet.alerts.util;

import com.safetynet.alerts.model.dto.PersonsCoveredByStationDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        LocalDate dateOfBirth = LocalDate.of(2011, 02, 25);

        // ACT
        int age = ageCountCalculator.calculateAge(dateOfBirth);

        // ARRANGE
        assertEquals(10, age);
    }

    @Test
    public void should_Return_0_When_Date_Of_Birth_Is_Null() {
        // ARRANGE
        LocalDate dateOfBirth = null;

        // ACT
        int age = ageCountCalculator.calculateAge(dateOfBirth);

        // ARRANGE
        assertEquals(0, age);
    }

    @Test
    public void should_Return_A_LocalDate_Object() {
        // ARRANGE
        Date date = new Date();

        // ACT
        LocalDate result = ageCountCalculator.convertToLocalDate(date);

        // ARRANGE
        assertTrue(result instanceof LocalDate);
    }

    @Test
    public void should_Sort_Children() {
        // ARRANGE
        List<PersonsCoveredByStationDTO> list = new ArrayList<>();
        for (int i = 16; i < 21; i++) {
            PersonsCoveredByStationDTO personsCoveredByStationDTO = new PersonsCoveredByStationDTO();
            personsCoveredByStationDTO.setAge(i);
            list.add(personsCoveredByStationDTO);
        }
        // ACT
        int result = ageCountCalculator.countNumberOfChildren(list);

        // ARRANGE
        assertEquals(3, result);

    }

}

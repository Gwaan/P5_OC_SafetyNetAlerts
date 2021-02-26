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
        assertEquals(10, age);
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

}

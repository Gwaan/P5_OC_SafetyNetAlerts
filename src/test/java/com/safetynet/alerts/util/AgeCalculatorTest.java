package com.safetynet.alerts.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AgeCalculatorTest {

    private AgeCalculator ageCountCalculator;

    @BeforeEach
    public void setUpPerTest() {
        ageCountCalculator = new AgeCalculator();
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

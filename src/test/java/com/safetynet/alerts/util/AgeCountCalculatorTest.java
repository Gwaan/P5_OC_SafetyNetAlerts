package com.safetynet.alerts.util;

import org.apache.tomcat.jni.Local;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AgeCountCalculatorTest {

    private AgeCountCalculator ageCountCalculator;

    @BeforeEach
    public void setUpPerTest() {
        ageCountCalculator = new AgeCountCalculator();
    }

    @AfterEach
    public void afterEachtest() {
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

}

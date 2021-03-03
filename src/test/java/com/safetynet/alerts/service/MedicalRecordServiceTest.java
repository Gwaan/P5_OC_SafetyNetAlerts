package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.AlreadyExistingException;
import com.safetynet.alerts.exceptions.NotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    private MedicalRecordService medicalRecordService;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    private static List<MedicalRecord> medicalRecordList;

    private static MedicalRecord medicalRecord;

    private static List<MedicalRecord> emptyList;

    @BeforeEach
    void beforeEach() {
        medicalRecordService = new MedicalRecordService(
                medicalRecordRepository);
        medicalRecordList = new ArrayList<>();
        emptyList = new ArrayList<>();
        medicalRecord = new MedicalRecord(1L, "Tes", "Test",
                new Date(1991 + 1900, 8, 28), new String[]{"test", "test12"},
                new String[]{"test", "test123"});
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
        medicalRecordList = null;
        medicalRecord = null;
        emptyList = null;
    }

    @Test
    public void should_Return_All_Medical_Records_From_Db() {
        when(medicalRecordRepository.findAll()).thenReturn(medicalRecordList);

        medicalRecordService.list();

        verify(medicalRecordRepository, times(1)).findAll();

    }

    @Test
    public void should_Throw_AlreadyExistingException_If_MedicalRecord_To_Save_Is_Existing() {
        when(medicalRecordRepository.existsMedicalRecordByFirstNameAndLastName(
                anyString(), anyString())).thenReturn(true);

        assertThrows(AlreadyExistingException.class,
                () -> medicalRecordService.save(medicalRecord));
    }

    @Test
    public void should_Return_A_MedicalRecord_Saved_In_Db() {
        when(medicalRecordRepository.save(medicalRecord)).thenReturn(
                medicalRecord);

        MedicalRecord medicalRecordExpected = medicalRecordService.save(
                medicalRecord);

        verify(medicalRecordRepository, times(1)).save(medicalRecord);
        assertEquals(medicalRecord, medicalRecordExpected);
    }

    @Test
    public void should_Return_MedicalRecord_Updated_In_Db() {
        when(medicalRecordRepository.save(medicalRecord)).thenReturn(
                medicalRecord);

        MedicalRecord medicalRecordExpected = medicalRecordService.saveUpdated(
                medicalRecord);

        verify(medicalRecordRepository, times(1)).save(medicalRecord);
        assertEquals(medicalRecord, medicalRecordExpected);
    }

    @Test
    public void should_Return_A_List_Of_MedicalRecord_Saved_In_Db() {
        when(medicalRecordRepository.saveAll(medicalRecordList)).thenReturn(
                medicalRecordList);

        List<MedicalRecord> medicalRecordListExpected = (List<MedicalRecord>) medicalRecordService
                .saveAll(medicalRecordList);

        verify(medicalRecordRepository, times(1)).saveAll(medicalRecordList);
        assertEquals(medicalRecordList, medicalRecordListExpected);

    }

    @Test
    public void should_Find_A_MedicalRecord_With_FirstName_And_LastName() {
        when(medicalRecordRepository.findByFirstNameAndLastName(anyString(),
                anyString())).thenReturn(medicalRecord);

        MedicalRecord medicalRecordExpected = medicalRecordService.findByFirstNameAndLastName(
                "test", "test");

        verify(medicalRecordRepository, times(1)).findByFirstNameAndLastName(
                anyString(), anyString());
        assertEquals(medicalRecord, medicalRecordExpected);
    }

    @Test
    public void should_Return_A_List_Of_MedicalRecords_With_FirstName_And_LastName() {
        when(medicalRecordRepository.findMedicalRecordsByFirstNameAndLastName(
                anyString(), anyString())).thenReturn(medicalRecordList);

        List<MedicalRecord> medicalRecordListExpected = medicalRecordService.findMedicalRecordsByFirstNameAndLastName(
                "test", "test");

        verify(medicalRecordRepository,
                times(1)).findMedicalRecordsByFirstNameAndLastName(anyString(),
                anyString());
        assertEquals(medicalRecordList, medicalRecordListExpected);
    }

    @Test
    public void should_Throws_NotFoundException_When_No_MedicalRecord_Are_Found_With_FirstName_And_LastName() {
        when(medicalRecordRepository.findMedicalRecordsByFirstNameAndLastName(
                anyString(), anyString())).thenReturn(emptyList);
        assertThrows(NotFoundException.class,
                () -> medicalRecordService.findMedicalRecordsByFirstNameAndLastName(
                        "test", "test"));
    }

    @Test
    public void should_Delete_A_MedicalRecord() {
        doNothing().when(medicalRecordRepository).delete(medicalRecord);
        medicalRecordService.deleteMedicalRecord(medicalRecord);
        verify(medicalRecordRepository, times(1)).delete(medicalRecord);
    }

    @Test
    public void should_Update_A_MedicalRecord_From_Db() {
        MedicalRecord medicalRecordFromBody = new MedicalRecord();
        medicalRecordFromBody.setMedications(new String[]{"test", "123"});
        medicalRecordFromBody.setAllergies(new String[]{"test", "123"});
        medicalRecordFromBody.setBirthDate(new Date(1991 + 1900, 8, 28));
        MedicalRecord medicalRecordToUpdate = new MedicalRecord();
        medicalRecordToUpdate.setMedications(new String[]{""});
        medicalRecordToUpdate.setAllergies(new String[]{""});
        medicalRecordToUpdate.setBirthDate(new Date());

        MedicalRecord medicalRecordUpdated = medicalRecordService.updateMedicalRecord(
                medicalRecordFromBody, medicalRecordToUpdate);

        assertEquals(medicalRecordFromBody.getMedications(),
                medicalRecordUpdated.getMedications());
        assertEquals(medicalRecordFromBody.getAllergies(),
                medicalRecordUpdated.getAllergies());
        assertEquals(medicalRecordFromBody.getBirthDate(),
                medicalRecordUpdated.getBirthDate());
    }

    @Test
    public void should_Return_True_If_A_MedicalRecord_Exists_In_Db() {
        when(medicalRecordRepository.existsMedicalRecordByFirstNameAndLastName(
                anyString(), anyString())).thenReturn(true);

        boolean expected = medicalRecordService.existsMedicalRecordByFirstNameAndLastName(
                "test", "test");

        verify(medicalRecordRepository,
                times(1)).existsMedicalRecordByFirstNameAndLastName(anyString(),
                anyString());
        assertTrue(expected);
    }


}

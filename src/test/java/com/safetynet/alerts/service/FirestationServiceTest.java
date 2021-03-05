package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.AlreadyExistingException;
import com.safetynet.alerts.exceptions.NotFoundException;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.FirestationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

    private FirestationService firestationService;

    @Mock
    private FirestationRepository firestationRepository;

    private static Firestation firestation;

    private static List<Firestation> firestationList;

    @BeforeEach
    void beforeEach() {
        firestationService = new FirestationService(firestationRepository);
        firestationList = new ArrayList<>();
        firestation = new Firestation(1L, 1, "test firestation address 1");
        firestationList.add(
                new Firestation(1L, 1, "test firestation address 1"));
        firestationList.add(
                new Firestation(2L, 2, "test firestation address 2"));
    }

    @AfterEach
    void afterEach() {
        firestation = null;
        firestationList = null;
    }

    @Test
    public void should_Return_All_Firestations_From_Db() {
        when(firestationRepository.findAll()).thenReturn(firestationList);

        List<Firestation> firestationListExpected = (List<Firestation>) firestationService
                .findAll();

        verify(firestationRepository, times(1)).findAll();
        assertEquals(firestationList, firestationListExpected);
    }

    @Test
    public void should_Throw_AlreadyExistingException_If_Firestation_To_Save_Is_Existing() {
        when(firestationRepository.existsFirestationByAddressAndStation(
                anyString(), anyInt())).thenReturn(true);

        assertThrows(AlreadyExistingException.class,
                () -> firestationService.save(firestation));
    }

    @Test
    public void should_Return_A_Firestation_Saved_In_Db() {
        when(firestationRepository.save(firestation)).thenReturn(firestation);

        Firestation firestationExpected = firestationService.save(firestation);

        verify(firestationRepository, times(1)).save(firestation);
        assertEquals(firestation, firestationExpected);
    }

    @Test
    public void should_Return_Firestation_Updated_In_Db() {
        when(firestationRepository.save(firestation)).thenReturn(firestation);

        Firestation firestationExpected = firestationService.saveUpdated(
                firestation);

        verify(firestationRepository, times(1)).save(firestation);
        assertEquals(firestation, firestationExpected);
    }

    @Test
    public void should_Return_A_List_Of_Firestation_Saved_In_Db() {
        when(firestationRepository.saveAll(firestationList)).thenReturn(
                firestationList);

        List<Firestation> firestationListExpected = (List<Firestation>) firestationService
                .saveAll(firestationList);

        verify(firestationRepository, times(1)).saveAll(firestationList);
        assertEquals(firestationList, firestationListExpected);
    }

    @Test
    public void should_Delete_A_Firestation_In_Db() {
        doNothing().when(firestationRepository).delete(firestation);
        firestationService.deleteFirestation(firestation);
        verify(firestationRepository, times(1)).delete(firestation);
    }

    @Test
    public void should_Find_Firestation_With_Address_And_Station_Number() {
        when(firestationRepository.findByAddressAndStation(anyString(),
                anyInt())).thenReturn(firestation);

        Firestation firestationExpected = firestationService.findFirestationByAddressAndStation(
                "test", 1);

        verify(firestationRepository, times(1)).findByAddressAndStation(
                anyString(), anyInt());
        assertEquals(firestation, firestationExpected);
    }

    @Test
    public void should_Throws_NotFoundException_When_No_Firestation_Are_Found_With_Address_And_Station_Number() {
        when(firestationRepository.findByAddressAndStation(anyString(),
                anyInt())).thenReturn(null);

        assertThrows(NotFoundException.class,
                () -> firestationService.findFirestationByAddressAndStation(
                        "test", 1));
    }

    @Test
    public void should_Return_A_List_Of_Firestation_By_Station_Number() {
        List<String> addresses = new ArrayList<>();
        addresses.add("test");
        when(firestationRepository.findAddressesByStation(anyInt())).thenReturn(
                addresses);

        List<String> addressesExpected = (List<String>) firestationService.findByStation(
                1);

        verify(firestationRepository, times(1)).findAddressesByStation(
                anyInt());
        assertEquals(addresses, addressesExpected);
    }

    @Test
    public void should_Update_A_Firestation_From_Db() {
        Firestation firestationFromBody = new Firestation();
        firestationFromBody.setAddress("test address");
        firestationFromBody.setStation(1);
        Firestation firestationToUpdate = new Firestation();
        firestationToUpdate.setAddress("azerty");
        firestationToUpdate.setStation(10);

        Firestation firestationUpdated = firestationService.updateFirestation(
                firestationFromBody, firestationToUpdate);

        assertEquals(firestationFromBody.getAddress(),
                firestationUpdated.getAddress());
        assertEquals(firestationFromBody.getStation(),
                firestationUpdated.getStation());
    }

    @Test
    public void should_Return_True_If_A_FireStation_Exists_In_Db() {
        when(firestationRepository.existsFirestationByAddressAndStation(
                anyString(), anyInt())).thenReturn(true);

        boolean expected = firestationService.existsFirestationByAddressAndStation(
                "test", 1);

        verify(firestationRepository,
                times(1)).existsFirestationByAddressAndStation(anyString(),
                anyInt());
        assertTrue(expected);
    }

    @Test
    public void should_Return_Station_Number_By_Address() {
        List<Integer> listOfStationsNumber = new ArrayList<>();
        listOfStationsNumber.add(1);
        listOfStationsNumber.add(2);
        when(firestationRepository.findStationByAddress(
                anyString())).thenReturn(listOfStationsNumber);

        List<Integer> listOfStationsNumberExpected = firestationService.findStationByAddress(
                "test");

        verify(firestationRepository, times(1)).findStationByAddress(
                anyString());
        assertEquals(listOfStationsNumber, listOfStationsNumberExpected);
    }

    @Test
    public void should_Throws_NotFoundException_If_No_Station_Number_Are_Found_With_Address() {
        List<Integer> emptyList = new ArrayList<>();
        when(firestationRepository.findStationByAddress(
                anyString())).thenReturn(emptyList);

        assertThrows(NotFoundException.class,
                () -> firestationService.findStationByAddress("test"));
    }

}

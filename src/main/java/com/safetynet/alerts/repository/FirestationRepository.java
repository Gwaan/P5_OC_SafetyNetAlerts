package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Firestation repository.
 *
 * @author Gwen
 * @version 1.0
 */
@Repository
public interface FirestationRepository extends CrudRepository<Firestation, Long> {

    /**
     * Find by address and station firestation.
     *
     * @param address the address
     * @param station the station
     * @return the firestation
     */
    Firestation findByAddressAndStation(String address, int station);

    /**
     * Exists firestation by address and station boolean.
     *
     * @param address the address
     * @param station the station
     * @return either true if fire station is existing or false if it's not
     */
    boolean existsFirestationByAddressAndStation(String address, int station);

    /**
     * Find addresses by station iterable.
     *
     * @param station the station
     * @return the list of fire station addresses covered by station number
     */
    @Query("SELECT f.address FROM Firestation f WHERE f.station = :station")
    Iterable<String> findAddressesByStation(int station);

    /**
     * Find station by address iterable.
     *
     * @param address the address
     * @return the list of fire station number covered at address
     */
    @Query("SELECT f.station FROM Firestation f WHERE f.address = :address")
    Iterable<Integer> findStationByAddress(String address);

}

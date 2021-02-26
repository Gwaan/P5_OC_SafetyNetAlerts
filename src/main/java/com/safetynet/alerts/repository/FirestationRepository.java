package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FirestationRepository extends CrudRepository<Firestation, Long> {

    Firestation findByAddressAndStation(String address, int station);


    boolean existsFirestationByAddressAndStation(String address, int station);

    @Query("SELECT f.address FROM Firestation f WHERE f.station = :station")
    Iterable<String> findAddressesByStation(int station);

    @Query("SELECT f.station FROM Firestation f WHERE f.address = :address")
    Iterable<Integer> findStationByAddress(String address);

}

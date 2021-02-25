package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FirestationRepository extends CrudRepository<Firestation, Long> {

    Firestation findByAddressAndStation(String address, int station);



    boolean existsFirestationByAddressAndStation(String address, int station);

}

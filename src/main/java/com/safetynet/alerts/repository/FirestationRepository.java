package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FirestationRepository extends CrudRepository<Firestation, Long> {

    @Query(value = "SELECT new com.safetynet.alerts.model.Person"
            + "(p.firstName,p.lastName,p.address,p.city,p.zip, p.phone) "
            + "FROM Person p, Firestation f WHERE f.station = :stationNumber"
            + " AND p.address = f.address")
    Iterable<Person> findPersonsWithStationNumber(byte stationNumber);

}

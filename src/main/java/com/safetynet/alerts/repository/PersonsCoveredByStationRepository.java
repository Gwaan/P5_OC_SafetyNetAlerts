package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface PersonsCoveredByStationRepository extends CrudRepository<Person, Long> {

    @Query(value = "SELECT p "
            + "FROM Person p, Firestation f WHERE f.station = :stationNumber"
            + " AND p.address = f.address")
    Iterable<Person> findPersonsWithStationNumber(int stationNumber);

    @Query(value = "SELECT m.birthDate FROM MedicalRecord m WHERE m.firstName"
            + " = " + " :firstName AND m.lastName = :lastName")
    Date findDateByFirstNameAndLastName(String firstName, String lastName);
}

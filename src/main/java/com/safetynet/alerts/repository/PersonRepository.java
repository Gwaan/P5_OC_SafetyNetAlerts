package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsPersonByFirstNameAndLastName(String firstName,
            String lastName);

    @Query(value = "SELECT p FROM Person p WHERE p.address = :address")
    Iterable<Person> findPersonByAddress(String address);

    @Query(value = "SELECT p FROM Person p WHERE p.address IN :addresses")
    Iterable<Person> findPersonByAddress(List<String> addresses);

    @Query(value = "SELECT p FROM Person p, Firestation f WHERE f.station IN "
            + ":stations AND p.address = f.address ")
    Iterable<Person> findHouseholdCoveredByStation(List<Integer> stations);


}

package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findByFirstNameAndLastName(String firstName, String lastName);

    Iterable<Person> findPersonByFirstNameAndLastName(String firstName,
            String lastName);

    boolean existsPersonsByFirstNameAndLastName(String firstName,
            String lastName);

    @Query(value = "SELECT p FROM Person p WHERE p.address = :address")
    Iterable<Person> findPersonByAddress(String address);

    @Query(value = "SELECT p FROM Person p WHERE p.address IN :addresses")
    Iterable<Person> findPersonByAddress(List<String> addresses);


    @Query(value = "SELECT p.phone FROM "
            + "Person p, Firestation f WHERE f.station = :station AND p"
            + ".address = f.address")
    Iterable<Person> findPhoneNumberByStation(int station);

    @Query(value = "SELECT p FROM Person p, Firestation f WHERE f.station = "
            + ":station AND p.address = f.address")
    Iterable<Person> findPersonByStation(int station);

    @Query(value = "SELECT p.email FROM Person p WHERE p.city = :city")
    Iterable<Person> findMailAddressesFromCity(String city);


}

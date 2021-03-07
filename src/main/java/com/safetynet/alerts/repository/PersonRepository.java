package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Person repository.
 *
 * @author Gwen
 * @version 1.0
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    /**
     * Find person by first name and last name.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the person
     */
    Person findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Find persons by first name and last name iterable.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return list of persons
     */
    Iterable<Person> findPersonsByFirstNameAndLastName(String firstName,
                                                       String lastName);

    /**
     * Exists persons by first name and last name boolean.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return either true if person is existing or false if it's not
     */
    boolean existsPersonsByFirstNameAndLastName(String firstName,
                                                String lastName);

    /**
     * Find persons by address.
     *
     * @param address the address
     * @return list of persons at address
     */
    @Query(value = "SELECT p FROM Person p WHERE p.address = :address")
    Iterable<Person> findPersonByAddress(String address);

    /**
     * Find phone number by station iterable.
     *
     * @param station the station
     * @return list of phone number covered by station number
     */
    @Query(value = "SELECT p.phone FROM "
            + "Person p, Firestation f WHERE f.station = :station AND p"
            + ".address = f.address")
    Iterable<Person> findPhoneNumberByStation(int station);

    /**
     * Find person by station iterable.
     *
     * @param station the station
     * @return list of person covered by station number
     */
    @Query(value = "SELECT p FROM Person p, Firestation f WHERE f.station = "
            + ":station AND p.address = f.address")
    Iterable<Person> findPersonByStation(int station);

    /**
     * Find mail addresses from city.
     *
     * @param city the city
     * @return list of mail addresses covered by city
     */
    @Query(value = "SELECT p.email FROM Person p WHERE p.city = :city")
    Iterable<Person> findMailAddressesFromCity(String city);

    /**
     * Find persons with station number.
     *
     * @param stationNumber the station number
     * @return person covered by station number
     */
    @Query(value = "SELECT p "
            + "FROM Person p, Firestation f WHERE f.station = :stationNumber"
            + " AND p.address = f.address")
    Iterable<Person> findPersonsWithStationNumber(int stationNumber);


}

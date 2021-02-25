package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsPersonByFirstNameAndLastName(String firstName,
            String lastName);

}

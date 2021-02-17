package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface FirestationRepository extends CrudRepository<Firestation, Long> {


}

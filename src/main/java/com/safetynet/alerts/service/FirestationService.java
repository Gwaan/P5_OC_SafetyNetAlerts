package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FirestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirestationService {

    @Autowired
    private FirestationRepository fireStationRepository;


    public Iterable<Firestation> list() {
        return fireStationRepository.findAll();
    }

    public Firestation save(Firestation firestation) {
        return fireStationRepository.save(firestation);
    }

    public Iterable<Firestation> saveAll(Iterable<Firestation> firestations) {
        return fireStationRepository.saveAll(firestations);
    }

}

package com.patientservice.controller;


import com.patientservice.entity.Patient;
import com.patientservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/getpatientbyid")
    public Patient getPatientById(@RequestParam long id){
        return patientRepository.findById(id).get();
    }

}

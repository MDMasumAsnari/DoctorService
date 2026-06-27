package com.bookingservice.client;

import com.bookingservice.dto.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "patientservice", url = "http://localhost:8082/api/v1/patient")
public interface PatientClient {
    @GetMapping("/getpatientbyid")
    Patient getPatientById(@RequestParam long id);
}
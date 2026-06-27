package com.bookingservice.client;

import com.bookingservice.dto.Doctor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "doctoreservice", url = "http://localhost:8081/api/v1/doctor")
public interface DoctorClient {
    @GetMapping("/getdoctorbyid")
    Doctor getDoctorById(@RequestParam("id") long id);
}
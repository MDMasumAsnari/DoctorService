package com.doctoreservice.controller;

import com.doctoreservice.dto.SearchResultDto;
import com.doctoreservice.entity.Doctor;
import com.doctoreservice.entity.DoctorAppointmentSchedule;
import com.doctoreservice.entity.TimeSlots;
import com.doctoreservice.repository.DoctorRepository;
import com.doctoreservice.repository.TimeSlotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
public class SearchController {

  
    private DoctorRepository doctorRepository;
    
    private TimeSlotsRepository timeSlotsRepository;

    public SearchController(DoctorRepository doctorRepository, TimeSlotsRepository timeSlotsRepository) {
        this.doctorRepository = doctorRepository;
        this.timeSlotsRepository = timeSlotsRepository;
    }

    // http://localhost:8081/api/v1/doctor/search?specialization=general&areaName=btm
    @GetMapping("/search")
    public List<Doctor> searchDoctors(
            @RequestParam String specialization,
            @RequestParam String areaName
    ) {
        LocalDate today = LocalDate.now();

        List<Doctor> doctors = doctorRepository.findBySpecializationAndArea(specialization, areaName);
          for(Doctor doctor : doctors){

              List<LocalDate> validDates = new ArrayList<>();
              List<LocalTime> allTimeSlots = new ArrayList<>();

              List<DoctorAppointmentSchedule>  schedules = doctor.getAppointmentSchedules();
              for (DoctorAppointmentSchedule schedule : schedules){
                  LocalDate scheduleDate = schedule.getDate();
                  LocalTime now = LocalTime.now();
                  List<TimeSlots> timeSlots = timeSlotsRepository.getAllTimeSlots(schedule.getId());
                  for (TimeSlots ts : timeSlots) {
                      LocalTime slotTime = ts.getTime();

                      // If schedule is today → only future times
                      if (scheduleDate.isEqual(today)) {
                          if (slotTime.isAfter(now)) {
                              allTimeSlots.add(slotTime);
                          }
                      }
                      // If schedule is in the future → add all times
                      else if (scheduleDate.isAfter(today)) {
                          allTimeSlots.add(slotTime);
                      }
                  }
              }
          }
          return doctors;
    }

    // http://localhost:8081/api/v1/doctor/getdoctorbyid?id=1
    @GetMapping("/getdoctorbyid")
    public Doctor getDoctorById(
            @RequestParam long id
    ){
        return doctorRepository.findById(id).get();
    }


}

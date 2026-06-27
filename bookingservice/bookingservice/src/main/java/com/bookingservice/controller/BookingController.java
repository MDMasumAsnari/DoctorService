package com.bookingservice.controller;

import com.bookingservice.client.DoctorClient;
import com.bookingservice.client.PatientClient;
import com.bookingservice.dto.Doctor;
import com.bookingservice.dto.DoctorAppointmentSchedule;
import com.bookingservice.dto.Patient;
import com.bookingservice.dto.TimeSlots;
import com.bookingservice.entity.BookingConfirmation;
import com.bookingservice.repository.BookingConfirmationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    @Autowired
    private DoctorClient doctorClient;

    @Autowired
    private PatientClient patientClient;

    @Autowired
    private BookingConfirmationRepository bookingConfirmationRepository;

    @GetMapping("/getdoctor")
    public String getDoctorById(
            @RequestParam Long doctorId,
            @RequestParam Long patientId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
            LocalTime time

    ) {
        Patient p = patientClient.getPatientById(patientId);
        Doctor d = doctorClient.getDoctorById(doctorId);
        BookingConfirmation bookingConfirmation = new BookingConfirmation();
        bookingConfirmation.setDoctorName(d.getName());
        bookingConfirmation.setPatientName(p.getName());
        bookingConfirmation.setAddress(d.getAddress());
        List<DoctorAppointmentSchedule> appointmentSchedules = d.getAppointmentSchedules();
        for (DoctorAppointmentSchedule app : appointmentSchedules) {
            LocalDate localDate = app.getDate();
            if (localDate.isEqual(date)) {
                List<TimeSlots> timeSlots = app.getTimeSlots();
                for (TimeSlots t : timeSlots) {
                    if (t.getTime().equals(time)) {
                        bookingConfirmation.setDate(date);
                        bookingConfirmation.setTime(time);
                    }
                }
            }
        }
        BookingConfirmation savedBookingConfirmation = bookingConfirmationRepository.save(bookingConfirmation);
                return null;
            }

    @GetMapping("/bookingid")
    public BookingConfirmation getBookingsById(@RequestParam long bookingId){
        return bookingConfirmationRepository.findById(bookingId).get();
    }


    @PutMapping("/updatestatus")
    public void confirmBooking(@RequestBody BookingConfirmation bookingConfirmation){
        bookingConfirmationRepository.save(bookingConfirmation);
    }



}

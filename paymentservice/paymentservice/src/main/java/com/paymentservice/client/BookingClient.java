package com.paymentservice.client;

import com.paymentservice.dto.BookingConfirmation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bookingservice")
public interface BookingClient {

    @GetMapping("/api/v1/booking/bookingid")
    BookingConfirmation getBookingsById(@RequestParam("bookingId") long bookingId);

    @PutMapping("/api/v1/booking/updatestatus")
    public void confirmBooking(@RequestBody BookingConfirmation bookingConfirmation);

}

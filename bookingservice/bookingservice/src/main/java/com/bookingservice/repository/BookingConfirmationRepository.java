package com.bookingservice.repository;

import com.bookingservice.entity.BookingConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingConfirmationRepository extends JpaRepository<BookingConfirmation, Long> {

}

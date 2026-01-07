package com.devendra.OnlineServiceBookingPaymentSystem.repository;

import com.devendra.OnlineServiceBookingPaymentSystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
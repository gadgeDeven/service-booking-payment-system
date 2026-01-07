package com.devendra.OnlineServiceBookingPaymentSystem.service;

import com.devendra.OnlineServiceBookingPaymentSystem.entity.Booking;
import com.devendra.OnlineServiceBookingPaymentSystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
    
    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }
}
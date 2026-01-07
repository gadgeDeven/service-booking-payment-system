package com.devendra.OnlineServiceBookingPaymentSystem.repository;

import com.devendra.OnlineServiceBookingPaymentSystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
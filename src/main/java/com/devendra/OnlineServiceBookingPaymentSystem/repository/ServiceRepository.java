package com.devendra.OnlineServiceBookingPaymentSystem.repository;

import com.devendra.OnlineServiceBookingPaymentSystem.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {}
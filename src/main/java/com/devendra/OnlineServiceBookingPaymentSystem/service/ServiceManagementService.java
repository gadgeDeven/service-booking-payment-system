package com.devendra.OnlineServiceBookingPaymentSystem.service;

import com.devendra.OnlineServiceBookingPaymentSystem.entity.ServiceEntity;
import com.devendra.OnlineServiceBookingPaymentSystem.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceManagementService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    public ServiceEntity addService(ServiceEntity service) {
        return serviceRepository.save(service);
    }
}
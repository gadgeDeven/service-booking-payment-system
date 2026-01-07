package com.devendra.OnlineServiceBookingPaymentSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devendra.OnlineServiceBookingPaymentSystem.service.UserService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class OnlineServiceBookingPaymentSystemApplication {

  
	@Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(OnlineServiceBookingPaymentSystemApplication.class, args);
    }

    @PostConstruct
    public void init() {
        userService.createAdminIfNotExists();
    }
}
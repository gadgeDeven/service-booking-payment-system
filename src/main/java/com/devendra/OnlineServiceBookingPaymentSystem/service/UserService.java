package com.devendra.OnlineServiceBookingPaymentSystem.service;

import com.devendra.OnlineServiceBookingPaymentSystem.dto.UserRegistrationDto;
import com.devendra.OnlineServiceBookingPaymentSystem.entity.User;
import com.devendra.OnlineServiceBookingPaymentSystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void createAdminIfNotExists() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@booking.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);
        }
    }

    public User registerNewUser(UserRegistrationDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
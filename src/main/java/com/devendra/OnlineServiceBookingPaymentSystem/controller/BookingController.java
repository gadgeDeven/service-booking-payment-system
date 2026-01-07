package com.devendra.OnlineServiceBookingPaymentSystem.controller;

import com.devendra.OnlineServiceBookingPaymentSystem.entity.Booking;
import com.devendra.OnlineServiceBookingPaymentSystem.entity.ServiceEntity;
import com.devendra.OnlineServiceBookingPaymentSystem.entity.User;
import com.devendra.OnlineServiceBookingPaymentSystem.repository.UserRepository;
import com.devendra.OnlineServiceBookingPaymentSystem.service.BookingService;
import com.devendra.OnlineServiceBookingPaymentSystem.service.ServiceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private ServiceManagementService serviceManagementService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepository userRepository;  // Add this

    @GetMapping("/new")
    public String showBookingForm(@RequestParam Long serviceId, Model model) {
        ServiceEntity service = serviceManagementService.getAllServices().stream()
                .filter(s -> s.getId().equals(serviceId))
                .findFirst()
                .orElse(null);

        model.addAttribute("service", service);
        model.addAttribute("booking", new Booking());
        return "booking/form";
    }

    @PostMapping("/save")
    public String saveBooking(@ModelAttribute Booking booking,
                              @RequestParam Long serviceId,
                              Authentication auth,
                              RedirectAttributes redirectAttributes) {

        ServiceEntity service = serviceManagementService.getAllServices().stream()
                .filter(s -> s.getId().equals(serviceId))
                .findFirst()
                .orElse(null);

        if (service == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid service selected!");
            return "redirect:/";
        }

        booking.setService(service);
        booking.setAmount(service.getPrice());

        // Set logged-in user
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElse(null);
        if (user != null) {
            booking.setUser(user);
        }

        bookingService.saveBooking(booking);

        redirectAttributes.addFlashAttribute("success", "Booking successful! Proceed to payment.");
        return "redirect:/payment/initiate?bookingId=" + booking.getId();
    }
}
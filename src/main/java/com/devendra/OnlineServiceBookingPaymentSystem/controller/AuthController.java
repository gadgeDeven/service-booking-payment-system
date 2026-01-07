package com.devendra.OnlineServiceBookingPaymentSystem.controller;

import com.devendra.OnlineServiceBookingPaymentSystem.dto.UserRegistrationDto;
import com.devendra.OnlineServiceBookingPaymentSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;  // Yeh import add kar

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto dto,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {  // Yeh parameter add kar

        if (result.hasErrors()) {
            return "register";
        }

        if (userService.existsByUsername(dto.getUsername())) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }

        if (userService.existsByEmail(dto.getEmail())) {
            model.addAttribute("error", "Email already registered!");
            return "register";
        }

        userService.registerNewUser(dto);
        
        // Yeh line important – flash attribute redirect ke baad bhi rahega
        redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
        
        return "redirect:/login";  // redirect: must hai
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
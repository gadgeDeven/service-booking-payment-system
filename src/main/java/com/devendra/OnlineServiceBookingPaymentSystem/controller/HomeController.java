package com.devendra.OnlineServiceBookingPaymentSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.devendra.OnlineServiceBookingPaymentSystem.service.ServiceManagementService;

@Controller
public class HomeController {

	@Autowired
	private ServiceManagementService serviceManagementService;  // inject kar

	@GetMapping({"/", "/home"})
	public String home(Model model, Authentication authentication) {  // Authentication optional for greeting
	    model.addAttribute("services", serviceManagementService.getAllServices());
	    return "home";
	}
}

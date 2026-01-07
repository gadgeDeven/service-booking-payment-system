package com.devendra.OnlineServiceBookingPaymentSystem.controller;

import com.devendra.OnlineServiceBookingPaymentSystem.entity.ServiceEntity;
import com.devendra.OnlineServiceBookingPaymentSystem.service.ServiceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ServiceManagementService serviceManagementService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("services", serviceManagementService.getAllServices());
        return "admin/dashboard";
    }

    @GetMapping("/services/add")
    public String showAddServiceForm(Model model) {
        model.addAttribute("service", new ServiceEntity());
        return "admin/add-service";
    }

    @PostMapping("/services/add")
    public String addService(@ModelAttribute ServiceEntity service) {
        serviceManagementService.addService(service);
        return "redirect:/admin/dashboard";
    }
}
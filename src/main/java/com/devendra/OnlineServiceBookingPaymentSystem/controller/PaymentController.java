package com.devendra.OnlineServiceBookingPaymentSystem.controller;

import com.devendra.OnlineServiceBookingPaymentSystem.entity.Booking;
import com.devendra.OnlineServiceBookingPaymentSystem.service.BookingService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private BookingService bookingService;

    @Value("${razorpay.key_id}")
    private String razorpayKeyId;

    @Value("${razorpay.key_secret}")
    private String razorpayKeySecret;

    // Step 1: Initiate payment – GET request
    @GetMapping("/initiate")
    public String initiatePayment(@RequestParam Long bookingId, Model model) throws Exception {
        Booking booking = bookingService.findById(bookingId);
        if (booking == null || booking.getService() == null || booking.getUser() == null) {
            model.addAttribute("error", "Invalid booking details!");
            return "error";
        }

        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int)(booking.getAmount() * 100)); // in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "booking_" + booking.getId());

        Order order = razorpay.orders.create(orderRequest);

        model.addAttribute("orderId", order.get("id"));
        model.addAttribute("amount", order.get("amount"));
        model.addAttribute("keyId", razorpayKeyId);
        model.addAttribute("bookingId", booking.getId());
        model.addAttribute("serviceName", booking.getService().getName());
        model.addAttribute("customerName", booking.getUser().getUsername());

        return "payment/checkout";
    }

    // Step 2: Success page – GET request (Razorpay redirects with query params)
    @GetMapping("/success")
    public String paymentSuccess(
            @RequestParam(required = false) String razorpayPaymentId,
            @RequestParam(required = false) String razorpayOrderId,
            @RequestParam(required = false) String razorpaySignature,
            @RequestParam Long bookingId,
            Model model) {

        // In test mode, we trust the redirect
        Booking booking = bookingService.findById(bookingId);
        if (booking != null) {
            // Optional: Update booking status to PAID
            // booking.setStatus("PAID");
            // bookingService.saveBooking(booking);
        }

        model.addAttribute("message", "Payment Successful! 🎉 Your booking is confirmed.");
        model.addAttribute("paymentId", razorpayPaymentId != null ? razorpayPaymentId : "test_" + System.currentTimeMillis());
        return "payment/success";
    }
}
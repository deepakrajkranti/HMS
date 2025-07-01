package com.appointment.Booking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @GetMapping("/book")
    ResponseEntity<?>bookSlot() {

        return ResponseEntity.ok("HELLO FROM ACCOUNTS MICROSERVICE");
    }
}

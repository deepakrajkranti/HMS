package com.appointment.Booking.controller;

import com.appointment.Booking.model.PatientBookingRequest;
import com.appointment.Booking.service.AppointmentBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentBookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<?> bookSlot(@RequestParam Long slotId,
                                      @RequestBody PatientBookingRequest request) {
        try {
            String msg = bookingService.bookSlot(slotId, request.getPatient(), request.getSymptoms());
            return ResponseEntity.ok().body(Map.of("status", "SUCCESS", "message", msg));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("status", "FAILED", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "ERROR", "message", e.getMessage()));
        }
    }
}
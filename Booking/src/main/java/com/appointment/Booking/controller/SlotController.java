package com.appointment.Booking.controller;

import com.appointment.Booking.dto.BulkSlotCreateRequest;
import com.appointment.Booking.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
public class SlotController {

    private final SlotService slotService;

    @PostMapping("/create/bulk")
    public ResponseEntity<?> createSlotsInBulk(@RequestBody BulkSlotCreateRequest request) {
        try {
            int count = slotService.createSlotsInBulk(request);
            return ResponseEntity.ok(Map.of(
                    "status", "SUCCESS",
                    "message", count + " slots created successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "ERROR",
                    "message", e.getMessage()
            ));
        }
    }
}


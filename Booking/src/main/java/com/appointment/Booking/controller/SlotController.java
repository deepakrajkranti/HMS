package com.appointment.Booking.controller;

import com.appointment.Booking.dto.BulkSlotCreateRequest;
import com.appointment.Booking.dto.SlotDto;
import com.appointment.Booking.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

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

    @GetMapping("/available-range")
    public ResponseEntity<List<SlotDto>> getAvailableSlotsInRange(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime to) {
        return ResponseEntity.ok(slotService.getAvailableSlotsInTimeRange(doctorId, date, from, to));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SlotDto>> getAllSlots(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(slotService.getAllSlots(doctorId, date));
    }


}


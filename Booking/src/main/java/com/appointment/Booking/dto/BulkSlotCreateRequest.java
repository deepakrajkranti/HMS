package com.appointment.Booking.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BulkSlotCreateRequest {
    private Long doctorId;
    private LocalDate date;
    private int durationMinutes;
    private List<TimeRange> timeRanges;
}


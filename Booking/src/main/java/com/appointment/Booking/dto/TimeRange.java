package com.appointment.Booking.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TimeRange {
    private LocalTime start;
    private LocalTime end;
}

package com.appointment.Booking.dto;

import com.appointment.Booking.model.SlotStatus;
import lombok.Data;
import java.time.LocalTime;

@Data
public class SlotDto {
    private Long slotId;
    private LocalTime time;
    private int durationMinutes;
    private SlotStatus status;
}

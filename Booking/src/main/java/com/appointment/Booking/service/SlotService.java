package com.appointment.Booking.service;
import com.appointment.Booking.dto.BulkSlotCreateRequest;
import com.appointment.Booking.dto.SlotDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SlotService {
    int createSlotsInBulk(BulkSlotCreateRequest request);
    List<SlotDto> getAllSlots(Long doctorId, LocalDate date);

    List<SlotDto> getAvailableSlotsInTimeRange(Long doctorId, LocalDate date, LocalTime from, LocalTime to);

}

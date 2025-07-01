package com.appointment.Booking.service;

import com.appointment.Booking.dto.BulkSlotCreateRequest;

public interface SlotService {
    int createSlotsInBulk(BulkSlotCreateRequest request);
}

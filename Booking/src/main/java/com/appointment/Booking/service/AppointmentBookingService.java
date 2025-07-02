package com.appointment.Booking.service;

import com.appointment.Booking.dto.PatientDTO;

public interface AppointmentBookingService {
    public String bookSlot(Long slotId, PatientDTO patientDTO, String symptoms);
}

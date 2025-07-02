package com.appointment.Booking.model;

import com.appointment.Booking.dto.PatientDTO;
import lombok.Data;

@Data
public class PatientBookingRequest {
    private PatientDTO patient;
    private String symptoms;
}
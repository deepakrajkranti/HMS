package com.appointment.Booking.dto;

import com.appointment.Booking.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationAppointmentDTO {
    private Long appointmentId;
    private String symptoms;
    private BookingStatus status;
    private LocalDateTime createdAt;

    private LocalDate slotDate;
    private LocalTime slotTime;
    private int durationMinutes;

    private String doctorName;
    private String doctorSpecialization;

    private String patientName;
    private String patientEmail;
    private String patientPhone;
}

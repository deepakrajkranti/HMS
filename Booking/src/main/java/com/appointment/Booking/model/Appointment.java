package com.appointment.Booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symptoms;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)//basically ManyToOne when think like Many class(Appointment) to one(Doctor)
    @JoinColumn(name = "patient_id", nullable = false)//and these are only foreign key relationship not like properties of entites
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "slot_id", nullable = false, unique = true)
    private Slot slot;
}


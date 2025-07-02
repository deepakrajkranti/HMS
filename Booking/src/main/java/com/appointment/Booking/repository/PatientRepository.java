package com.appointment.Booking.repository;

import com.appointment.Booking.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    Optional<Patient> findByPhone(String phone);
}

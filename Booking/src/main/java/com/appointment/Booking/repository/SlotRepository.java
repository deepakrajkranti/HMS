package com.appointment.Booking.repository;

import com.appointment.Booking.model.Slot;
import com.appointment.Booking.model.SlotStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Slot s WHERE s.id = :id")
    Optional<Slot> lockSlotForBooking(@Param("id") Long id);

    List<Slot> findByDoctorIdAndDateAndTimeBetweenAndStatus(
            Long doctorId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            SlotStatus status);

    List<Slot> findByDoctorIdAndDate(Long doctorId, LocalDate date);
}

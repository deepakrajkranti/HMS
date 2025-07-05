package com.appointment.Booking.service.impl;

import com.appointment.Booking.dto.BulkSlotCreateRequest;
import com.appointment.Booking.dto.SlotDto;
import com.appointment.Booking.dto.TimeRange;
import com.appointment.Booking.model.Doctor;
import com.appointment.Booking.model.Slot;
import com.appointment.Booking.model.SlotStatus;
import com.appointment.Booking.repository.DoctorRepository;
import com.appointment.Booking.repository.SlotRepository;
import com.appointment.Booking.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SlotServiceImpl implements SlotService {

    private final SlotRepository slotRepo;
    private final DoctorRepository doctorRepo;

    @Override
    public int createSlotsInBulk(BulkSlotCreateRequest request) {
        Doctor doctor = doctorRepo.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        List<Slot> slotsToSave = new ArrayList<>();

        for (TimeRange range : request.getTimeRanges()) {
            LocalTime current = range.getStart();
            while (current.plusMinutes(request.getDurationMinutes()).compareTo(range.getEnd()) <= 0) {
                Slot slot = new Slot();
                slot.setDoctor(doctor);
                slot.setDate(request.getDate());
                slot.setTime(current);
                slot.setDurationMinutes(request.getDurationMinutes());
                slot.setStatus(SlotStatus.AVAILABLE);
                slotsToSave.add(slot);

                current = current.plusMinutes(request.getDurationMinutes());
            }
        }

        slotRepo.saveAll(slotsToSave);
        return slotsToSave.size();
    }

    @Override
    public List<SlotDto> getAllSlots(Long doctorId, LocalDate date) {
        return slotRepo.findByDoctorIdAndDate(doctorId, date)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<SlotDto> getAvailableSlotsInTimeRange(Long doctorId, LocalDate date, LocalTime from, LocalTime to) {
        return slotRepo.findByDoctorIdAndDateAndTimeBetweenAndStatus(doctorId, date, from, to, SlotStatus.AVAILABLE)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private SlotDto toDto(Slot slot) {
        SlotDto dto = new SlotDto();
        dto.setSlotId(slot.getId());
        dto.setTime(slot.getTime());
        dto.setDurationMinutes(slot.getDurationMinutes());
        dto.setStatus(slot.getStatus());
        return dto;
    }

}

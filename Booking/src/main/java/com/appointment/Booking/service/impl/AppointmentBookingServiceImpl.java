package com.appointment.Booking.service.impl;

import com.appointment.Booking.dto.NotificationAppointmentDTO;
import com.appointment.Booking.dto.PatientDTO;
import com.appointment.Booking.kafka.KafkaProducer;
import com.appointment.Booking.model.*;
import com.appointment.Booking.repository.AppointmentRepository;
import com.appointment.Booking.repository.PatientRepository;
import com.appointment.Booking.repository.SlotRepository;
import com.appointment.Booking.service.AppointmentBookingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class AppointmentBookingServiceImpl implements AppointmentBookingService {
    private final SlotRepository slotRepo;
    private final PatientRepository patientRepo;
    private final AppointmentRepository appointmentRepo;
    private final KafkaProducer kafkaProducer;

    @Transactional
    @Override
    public String bookSlot(Long slotId, PatientDTO patientDTO, String symptoms) {
        Slot slot = slotRepo.lockSlotForBooking(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.getStatus() != SlotStatus.AVAILABLE) {
            throw new IllegalStateException("Slot already booked");
        }

        // Save patient (or fetch if already exists)
        Patient patient = patientRepo.findByPhone(patientDTO.getPhone())
                .orElseGet(() -> {
                    Patient newPatient = new Patient();
                    newPatient.setName(patientDTO.getName());
                    newPatient.setPhone(patientDTO.getPhone());
                    newPatient.setEmail(patientDTO.getEmail());
                    return patientRepo.save(newPatient);
                });

        // Mark slot as booked
        slot.setStatus(SlotStatus.BOOKED);
        slotRepo.save(slot);

        // Create appointment
        Appointment appointment = new Appointment();
        appointment.setDoctor(slot.getDoctor());
        appointment.setSlot(slot);
        appointment.setPatient(patient);
        appointment.setSymptoms(symptoms);
        appointment.setStatus(BookingStatus.CONFIRMED);
        appointment.setCreatedAt(LocalDateTime.now());
        appointmentRepo.save(appointment);
        // map data to NotificationAppointmentDTO
        // Send message to Kafka
        NotificationAppointmentDTO appointmentNotification = new NotificationAppointmentDTO();
        appointmentNotification.setAppointmentId(appointment.getId());
        appointmentNotification.setDoctorName(appointment.getDoctor().getName());
        appointmentNotification.setPatientName(appointment.getPatient().getName());
        appointmentNotification.setSymptoms(appointment.getSymptoms());
        appointmentNotification.setSlotTime(appointment.getSlot().getTime());
        appointmentNotification.setSlotDate(appointment.getSlot().getDate());
        appointmentNotification.setStatus(appointment.getStatus());
        appointmentNotification.setCreatedAt(appointment.getCreatedAt());
        appointmentNotification.setPatientPhone(appointment.getPatient().getPhone());
        appointmentNotification.setPatientEmail(appointment.getPatient().getEmail());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(appointmentNotification);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kafkaProducer.sendMessage(json);

        return "Appointment confirmed for slot: " + slot.getTime();
    }
}

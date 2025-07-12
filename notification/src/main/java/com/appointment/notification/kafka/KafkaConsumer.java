package com.appointment.notification.kafka;

import com.appointment.notification.dto.NotificationAppointmentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "hms-email", groupId = "hms-group") // Replace with your desired topic name and group ID
    public void consume(String messageJson) {
        try {
            // Assuming you have a method to convert JSON to NotificationAppointmentDTO
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            NotificationAppointmentDTO localDto = objectMapper.readValue(messageJson, NotificationAppointmentDTO.class);
            System.out.println("Received message: " + localDto);
            // process the message
        } catch (Exception e) {
            System.out.println("Failed to deserialize message" +
                    " from Kafka topic: " + e.getMessage());
        }
    }
}

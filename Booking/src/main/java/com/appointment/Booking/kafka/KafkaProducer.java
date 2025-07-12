package com.appointment.Booking.kafka;
import com.appointment.Booking.dto.NotificationAppointmentDTO;
import com.appointment.Booking.model.Appointment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC_NAME= "hms-email"; // Replace with your desired topic name

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String appointment) {
        kafkaTemplate.send(TOPIC_NAME, appointment);
        System.out.println("Message "+
                " has been successfully sent to the topic: " + TOPIC_NAME);
    }
}

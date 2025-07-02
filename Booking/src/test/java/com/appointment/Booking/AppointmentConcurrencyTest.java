package com.appointment.Booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentConcurrencyTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testConcurrentBooking() throws InterruptedException {
        int numberOfThreads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        Long slotId = 1L; // slot you want to test (must be available beforehand)

        for (int i = 0; i < numberOfThreads; i++) {
            int finalI = i;
            executor.submit(() -> {
                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    String json = """
                        {
                          "patient": {
                            "name": "TestUser%d",
                            "phone": "90000000%d",
                            "email": "test%d@example.com"
                          },
                          "symptoms": "Headache"
                        }
                        """.formatted(finalI, finalI, finalI);

                    HttpEntity<String> entity = new HttpEntity<>(json, headers);

                    ResponseEntity<String> response = restTemplate
                            .postForEntity("/api/appointments/book?slotId=" + slotId, entity, String.class);
                    System.out.println("Thread " + finalI + ": " + response.getStatusCode() + " -> " + response.getBody());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
    }
}

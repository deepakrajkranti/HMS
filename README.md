# HMS
Appointment Booking System for Patient and end to end Doctor onboarding
Here’s a concise `README.md` for your project, including setup, service usage, Docker Compose, observability, and a bulk slot creation example.

---

# Hospital Management System (HMS) Microservices

This project is a microservices-based Hospital Management System built with Java, Spring Boot, and Maven. It uses Docker Compose for orchestration and includes observability with Grafana, Loki, and Alloy.

## Prerequisites

- Docker & Docker Compose
- Java 17+
- Maven 3.8+

## Project Structure

- `Booking` — Booking service (appointments, slots)
- `gatewayServer` — API Gateway
- `notification` — Notification service
- `DockerCompose/docker-compose.yml` — Service orchestration
- `observability/` — Monitoring configs (Loki, Alloy)

## Setup & Running

1. **Clone the repository**
2. **Build Java services:**
   ```bash
   cd Booking &&  mvn clean package -DskipTests
   cd ../gatewayServer &&  mvn clean package -DskipTests
   cd ../notification &&  mvn clean package -DskipTests 
   ```
3. **Start all services:**
   ```bash
   cd DockerCompose
   docker-compose build --no-cache  
   ```

## Service Usage Flow

### 1. Booking Service

- **Create Bulk Slots**
   - Endpoint: `POST /api/slots/bulk`
   - Example Request Body:
     ```json
     {
       "doctorId": 1,
       "date": "2025-07-06",
       "durationMinutes": 5,
       "timeRanges": [
         { "start": "10:00", "end": "12:00" },
         { "start": "14:00", "end": "16:00" },
         { "start": "17:00", "end": "19:00" }
       ]
     }
     ```
   - **Description:** This will create 5-minute slots for the doctor on the specified date within the given time ranges.

- **Book Appointment**
   - Endpoint: `POST /api/appointments`
   - Body: `{ "slotId": 123, "patientId": 456 }`

### 2. Gateway Server

- Routes requests to appropriate services.
- Access all APIs via `http://localhost:8081/hms/booking/`

### 3. Notification Service

- Sends notifications on booking/cancellation.

### 4. Observability

- **Grafana:** `http://localhost:3000/` (default user: admin, password: admin)
- **Loki:** Log aggregation, used by Grafana.
- **Alloy:** Metrics and traces collection.

## Observability Setup

- Configured in `observability/` and mounted via Docker Compose.
- Logs and metrics are available in Grafana dashboards.

## Useful Docker Compose Commands

- **Start all services:**  
  `docker-compose up -d`
- **Stop all services:**  
  `docker-compose down -v`
- **View logs:**  
  `docker-compose logs -f`

---

**Bulk Slot Creation Example**

Request:
```json
{
  "doctorId": 1,
  "date": "2025-07-06",
  "durationMinutes": 5,
  "timeRanges": [
    { "start": "10:00", "end": "12:00" },
    { "start": "14:00", "end": "16:00" },
    { "start": "17:00", "end": "19:00" }
  ]
}
```
This creates 5-minute slots for doctor 1 on July 6, 2025, in the specified time ranges.

---

**Note:**  
Ensure all config files (e.g., `observability/alloy/alloy-local-config.yaml`) exist as per the Docker Compose file. For troubleshooting, see the logs or use `docker-compose config` to validate setup.
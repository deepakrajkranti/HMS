FROM eclipse-temurin:24-jdk
EXPOSE 8080
# Install curl
RUN apt-get update && apt-get install -y curl

COPY target/Booking-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
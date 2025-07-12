FROM eclipse-temurin:24-jdk
EXPOSE 8083
COPY target/notification-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
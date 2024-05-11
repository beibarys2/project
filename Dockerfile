FROM eclipse-temurin:17.0.11_9-jre-ubi9-minimal
COPY target/student-project-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
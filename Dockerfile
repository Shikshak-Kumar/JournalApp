# Stage 1: Build the application using a Maven image
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and download dependencies (caches them if pom doesn't change)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight runtime image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the generated JAR from the build stage
COPY --from=build /app/target/JournalApp-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Use official Java 21 image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy project source
COPY src src

# Build the project
RUN ./mvnw clean package -DskipTests

# Expose application port
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "target/emergency-blood-connector-0.0.1-SNAPSHOT.jar"]
# -------- Stage 1: Build --------
FROM gradle:8.14.3-jdk21-alpine AS builder
WORKDIR /app

# For caching copy gradle build files and gradle wrapper first
COPY settings.gradle.kts build.gradle.kts ./
COPY gradle ./gradle

COPY backend ./backend

RUN gradle :backend:bootJar --no-daemon

# -------- Stage 2: Run --------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/backend/build/libs/*.jar app.jar

# Environment variables (can be overridden by Docker Compose)
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/invoice_db
ENV SPRING_DATASOURCE_USERNAME=invoice_user
ENV SPRING_DATASOURCE_PASSWORD=invoice_pass
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=validate

EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java","-jar","app.jar"]

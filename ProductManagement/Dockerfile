# Stage 1: Build the Spring Boot application
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn -e -X -DskipTests dependency:go-offline

COPY src src

RUN mvn -e -X -DskipTests package

# Stage 2: Run the application
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

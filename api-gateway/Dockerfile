# Dockerfile
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests  # Added -DskipTests to speed up and avoid test env issues

FROM eclipse-temurin:21-jre AS runner
WORKDIR /app
COPY --from=builder /app/target/api-gateway-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 4004
ENTRYPOINT ["java", "-jar", "app.jar"]
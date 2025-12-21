#!/usr/bin/env bash

../member-service/mvnw -f ../member-service/pom.xml clean package -DskipTests

docker build -t member-service ../member-service

docker rm -f member-service 2>/dev/null

docker run -d \
  --name member-service \
  --network internal \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://member-service-db:5432/db \
  -e SPRING_DATASOURCE_USERNAME=admin_user \
  -e SPRING_DATASOURCE_PASSWORD=password \
  -e BILLING_SERVICE_ADDRESS=billing-service \
  -e BILLING_SERVICE_GRPC_PORT=9001 \
  -e SPRING_SQL_INIT_MODE=always \
  -e SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092 \
  member-service

echo "End of run-member-service.sh"

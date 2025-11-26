#!/usr/bin/env bash

set -e

docker network create internal 2>/dev/null || true

bash ./run-api-gateway.sh
bash ./run-patient-db.sh
bash ./run-auth-db.sh
bash ./run-billing-service.sh
bash ./run-kafka.sh
bash ./run-analytics-service.sh
bash ./run-auth-service.sh
bash ./run-patient-service.sh

#!/usr/bin/env bash

docker rm -f auth-service-db 2>/dev/null

docker run -d \
  --name auth-service-db \
  --network internal \
  -p 5001:5432 \
  -v /home/lizond/Documents/prueba/spring/database-auth:/var/lib/postgresql \
  -e POSTGRES_DB=db \
  -e POSTGRES_PASSWORD=password \
  -e POSTGRES_USER=admin_user \
  postgres:latest

echo "End of run-auth-db.sh"

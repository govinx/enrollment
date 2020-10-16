#!/bin/bash
docker build --tag  enrollment:latest .
docker create --name enrollment -p 9090:8080 enrollment:latest
docker start enrollment

# to attach to the container
# CONTAINER_ID=$(docker ps | grep "enrollment:latest" | awk '{print $1}')
# docker exec -it $CONTAINER_ID /bin/sh

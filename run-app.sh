#!/bin/bash
echo "Building project image"
docker build -t transaction-api:local .

docker-compose up -d db transactional-api
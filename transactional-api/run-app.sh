#!/bin/bash
echo "Building project image"
docker build -t transaction-api:local .

docker-compose up -d mysql transactional-api
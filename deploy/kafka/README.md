# Set up kafka

## Before
1. docker login -u ${YOUR_USERNAME} -p ${YOUR_PASSWORD}

## Base
1. docker build -f ./kafka/base/Dockerfile -t kafka-base .
2. docker tag kafka-base ${YOUR_USERNAME}/kafka-base:latest
3. docker push ${YOUR_USERNAME}/kafka-base:latest

## Kafka
1. docker build -f ./kafka/kafka/Dockerfile -t kafka .
2. docker tag kafka ${YOUR_USERNAME}/kafka:latest
3. docker push ${YOUR_USERNAME}/kafka:latest

## Zookeeper
1. docker build -f ./kafka/zookeeper/Dockerfile -t zookeeper .
2. docker tag zookeeper ${YOUR_USERNAME}/zookeeper:latest
3. docker push ${YOUR_USERNAME}/zookeeper:latest

## Run both
1. docker-compose -f ./kafka/docker-compose.yml up -d


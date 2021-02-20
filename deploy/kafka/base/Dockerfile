FROM openjdk:11-slim-buster

WORKDIR /app

RUN apt-get update && \
          apt-get -y install curl

RUN curl -L https://apache-mirror.rbc.ru/pub/apache/kafka/2.7.0/kafka_2.13-2.7.0.tgz > kafka.tgz
RUN tar -xzf kafka.tgz && mv kafka_2.13-2.7.0 kafka

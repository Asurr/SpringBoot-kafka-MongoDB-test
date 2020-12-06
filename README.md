# Spring Boot with Kafka,MongoDB and Swagger Example

## This Project covers with KafkaController:
how to use Spring Boot with Spring Kafka to Consume JSON(User)/String message from Kafka topics
how to use Spring Boot with Spring Kafka to Produce JSON(User)/String message to Kafka topics

## This Project covers with UserController:
how to use Spring Boot with MongoDB to find,create and delete users on MongoDB 

## ZOOKEEPER COMMANDS

## Start Zookeeper
- `bin/zookeeper-server-start.sh config/zookeeper.properties`

## Start Kafka Server
- `bin/kafka-server-start.sh config/server.properties`

## Create Kafka Topic
- `bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic Kafka_Example`
- `bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic Kafka_Example_json`

## Publish to the Kafka Topic via Console
- `bin/kafka-console-producer.sh --broker-list localhost:9092 --topic Kafka_Example`
- `bin/kafka-console-producer.sh --broker-list localhost:9092 --topic Kafka_Example_json`

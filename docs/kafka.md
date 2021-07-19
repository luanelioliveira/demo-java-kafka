# Kafka

## Using Kafka

### GET KAFKA

[Download](http://kafka.apache.org/downloads) the latest Kafka release and extract it:
```bash
$ tar -xzf kafka_2.13-2.8.0.tgz
$ cd kafka_2.13-2.8.0
```


### START THE KAFKA ENVIRONMENT

Run the following commands in order to start all services in the correct order:
```bash
# Start the ZooKeeper service
# Note: Soon, ZooKeeper will no longer be required by Apache Kafka.
$ bin/zookeeper-server-start.sh config/zookeeper.properties
```

Open another terminal session and run:
```bash
# Start the Kafka broker service
$ bin/kafka-server-start.sh config/server.properties
```


### CREATE A TOPIC TO STORE YOUR EVENTS

Open another terminal session and run:
```bash
$ bin/kafka-topics.sh --create --topic <TOPIC_NAME> --bootstrap-server localhost:9092
```


### DESCRIBE A TOPIC TO VIEW YOUR DETAILS 

In terminal run:
```bash
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic <TOPIC_NAME>
```


### DESCRIBE ALL TOPICS TO VIEW YOUR DETAILS

In terminal run:
```bash
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe
```


### DESCRIBE ALL CONSUMER GROUPS
```
$ bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --all-groups 
```


### LIST ALL TOPICS

In terminal run:
```bash
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --list 
```


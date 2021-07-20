# Kafka

## Using Kafka

### GET KAFKA

[Download](http://kafka.apache.org/downloads) the latest Kafka release and extract it:
```bash
$ tar -xzf kafka_2.13-2.8.0.tgz
$ cd kafka_2.13-2.8.0
```

### CONFIGURE THE KAFKA ENVIRONMENT

Create directories to store logs
```bash
$ mkdir data
$ mkdir data/kafka
$ mkdir data/zookeeper
```

Configure the file `server.properties`
```bash
$ vi config/server.properties
```

Change the parameters `log.dirs` to:
```
$ DIR_KAFKA/data/kafka
```

Configure the file `zookeeper.properties`
```bash
$ vi config/zookeeper.properties
```

Change the parameter `dataDir` to:
```
$ DIR_KAFKA/data/zookeeper
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

## Extras

### Zookeeper Properties

dataDir = Directory to store log files


### Kafka Server Properties

log.dirs = list of directories to store log files
num.partittions = Numbers of the partitions per topic

### KafkaConsumer

**ConsumerConfig.MAX_POLL_RECORDS_CONFIG**
Number of the records per poll 

**ConsumerConfig.AUTO_OFFSET_RESET_CONFIG**
- earliest -> Starts reading since FIRST message sent to offset
- latest   -> Starts reading since LAST message sent to offset

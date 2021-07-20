package io.github.luanelioliveira.samplekafka.consumer;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.CLIENT_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

import io.github.luanelioliveira.samplekafka.DeadLetterUnavailableException;
import io.github.luanelioliveira.samplekafka.Message;
import io.github.luanelioliveira.samplekafka.dispatcher.GsonSerializer;
import io.github.luanelioliveira.samplekafka.dispatcher.KafkaDispatcher;
import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaService<T> implements Closeable {

  private final KafkaConsumer<String, Message<T>> consumer;
  private final ConsumerFunction parse;

  public KafkaService(String groupId, String topic, ConsumerFunction<T> parse) {
    this(parse, groupId, Map.of());
    consumer.subscribe(Collections.singletonList(topic));
  }

  public KafkaService(String groupId, Pattern topic, ConsumerFunction<T> parse,
      Map<String, String> properties) {
    this(parse, groupId, properties);
    consumer.subscribe(topic);
  }

  private KafkaService(ConsumerFunction<T> parse, String groupId, Map<String, String> properties) {
    this.parse = parse;
    this.consumer = new KafkaConsumer<>(getProperties(groupId, properties));
  }

  public void run() throws DeadLetterUnavailableException {
    while (true) {
      var records = consumer.poll(Duration.ofMillis(100));
      if (!records.isEmpty()) {
        for (var record : records) {
          try {
            parse.consume(record);
          } catch (Exception e) {
            e.printStackTrace();
            sendDeadLetter(record);
          }
        }
      }
    }
  }

  private void sendDeadLetter(ConsumerRecord<String, Message<T>> record)
      throws DeadLetterUnavailableException {
    try (var deadLetter = new KafkaDispatcher<>()) {
      var message = record.value();
      try {
        deadLetter.send(
            "DEAD_LETTER",
            message.getId().toString(),
            message.getId().continueWith("DeadLetter"),
            new GsonSerializer<>().serialize("", message));
      } catch (ExecutionException | InterruptedException ex) {
        throw new DeadLetterUnavailableException();
      }
    }
  }

  private Properties getProperties(String groupId, Map<String, String> overrideProperties) {
    var properties = new Properties();
    properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
    properties.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
    properties.setProperty(GROUP_ID_CONFIG, groupId);
    properties.setProperty(CLIENT_ID_CONFIG, UUID.randomUUID().toString());
    properties.setProperty(MAX_POLL_RECORDS_CONFIG, "1");
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

    properties.putAll(overrideProperties);
    return properties;
  }

  @Override
  public void close() {
    consumer.close();
  }
}

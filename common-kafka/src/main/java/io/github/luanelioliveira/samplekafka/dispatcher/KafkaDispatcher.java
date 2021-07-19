package io.github.luanelioliveira.samplekafka.dispatcher;

import static org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

import io.github.luanelioliveira.samplekafka.CorrelationId;
import io.github.luanelioliveira.samplekafka.Message;
import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaDispatcher<T> implements Closeable {

  private final KafkaProducer<String, Message<T>> producer;

  public KafkaDispatcher() {
    this.producer = new KafkaProducer<>(properties());
  }

  private static Properties properties() {
    var properties = new Properties();
    properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
    properties.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
    properties.setProperty(ACKS_CONFIG, "all");
    return properties;
  }

  public void send(String topic, String key, CorrelationId id, T payload)
      throws ExecutionException, InterruptedException {
    Future<RecordMetadata> async = sendAsync(topic, key, id, payload);
    async.get();
  }

  private Future<RecordMetadata> sendAsync(String topic, String key, CorrelationId id, T payload) {
    var value = new Message<>(id.continueWith("_" + topic), payload);
    var record = new ProducerRecord<>(topic, key, value);

    Callback callback = (data, ex) -> {
      if (ex != null) {
        ex.printStackTrace();
        return;
      }
      System.out.println(
          "Sending message to " + data.topic() + "::: partition " + data.partition() + "/ offset "
              + data.offset() + "/ timestamp " + data.timestamp() + "/ message: " + value);
    };

    return producer.send(record, callback);
  }

  @Override
  public void close() {
    producer.close();
  }
}

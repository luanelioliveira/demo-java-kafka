package io.github.luanelioliveira.samplekafka.consumer;

import io.github.luanelioliveira.samplekafka.Message;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerService<T> {

  String getConsumerGroup();

  String getTopic();

  void consume(ConsumerRecord<String, Message<T>> record)
      throws ExecutionException, InterruptedException;

}

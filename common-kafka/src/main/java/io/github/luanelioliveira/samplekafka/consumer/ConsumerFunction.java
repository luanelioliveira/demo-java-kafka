package io.github.luanelioliveira.samplekafka.consumer;

import io.github.luanelioliveira.samplekafka.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerFunction<T> {

  void consume(ConsumerRecord<String, Message<T>> record) throws Exception;
}

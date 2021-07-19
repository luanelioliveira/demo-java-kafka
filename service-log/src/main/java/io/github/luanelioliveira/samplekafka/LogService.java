package io.github.luanelioliveira.samplekafka;

import io.github.luanelioliveira.samplekafka.consumer.KafkaService;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

public class LogService {

  public static void main(String[] args) throws DeadLetterUnavailableException {
    var service = new LogService();
    try (var kafkaService = new KafkaService(
        LogService.class.getSimpleName(),
        Pattern.compile(".*"),
        service::consume,
        Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))) {
      kafkaService.run();
    }
  }

  private void consume(ConsumerRecord<String, Message<String>> record) {
    System.out.println("------------------------------------------");
    System.out.println("LOG: " + record.topic());
    System.out.println(record.key());
    System.out.println(record.value());
    System.out.println(record.partition());
    System.out.println(record.offset());
  }
}

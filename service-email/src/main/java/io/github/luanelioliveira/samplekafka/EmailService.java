package io.github.luanelioliveira.samplekafka;

import io.github.luanelioliveira.samplekafka.consumer.ConsumerService;
import io.github.luanelioliveira.samplekafka.consumer.ServiceRunner;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService implements ConsumerService<String> {

  private static final int THREADS = 5;
  private static final String SEND_EMAIL_TOPIC_NAME = "SEND_EMAIL";

  public static void main(String[] args) {
    new ServiceRunner(EmailService::new).start(THREADS);
  }

  @Override
  public String getConsumerGroup() {
    return EmailService.class.getSimpleName();
  }

  @Override
  public String getTopic() {
    return SEND_EMAIL_TOPIC_NAME;
  }

  @Override
  public void consume(ConsumerRecord<String, Message<String>> record) {
    System.out.println("------------------------------------------");
    System.out.println("Sending email");
    System.out.println(record.key());
    System.out.println(record.value());
    System.out.println(record.value().getId());
    System.out.println(record.value().getPayload());
    System.out.println(record.partition());
    System.out.println(record.offset());
    System.out.println("Email sent");
  }

}

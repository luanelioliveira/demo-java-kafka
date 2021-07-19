package io.github.luanelioliveira.samplekafka;

import io.github.luanelioliveira.samplekafka.consumer.ConsumerService;
import io.github.luanelioliveira.samplekafka.consumer.ServiceRunner;
import io.github.luanelioliveira.samplekafka.dispatcher.KafkaDispatcher;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class SendEmailNewOrderService implements ConsumerService<Order> {

  private static final int THREADS = 5;
  private final static String ORDER_CREATED_TOPIC_NAME = "ORDER_CREATED";
  private final static String SEND_EMAIL_TOPIC_NAME = "SEND_EMAIL";

  public static void main(String[] args) {
    new ServiceRunner(SendEmailNewOrderService::new).start(THREADS);
  }

  @Override
  public String getConsumerGroup() {
    return SendEmailNewOrderService.class.getSimpleName();
  }

  @Override
  public String getTopic() {
    return ORDER_CREATED_TOPIC_NAME;
  }

  @Override
  public void consume(ConsumerRecord<String, Message<Order>> record)
      throws ExecutionException, InterruptedException {
    System.out.println("------------------------------------------");
    System.out.println("Processing new order, preparing email");
    System.out.println(record.value());

    var message = record.value();
    var order = message.getPayload();

    try (var dispatcher = new KafkaDispatcher<String>()) {
      var correlationId = message.getId()
          .continueWith(SendEmailNewOrderService.class.getSimpleName());
      var emailMessage = "Thank you for your order! We are processing your order!";
      dispatcher.send(SEND_EMAIL_TOPIC_NAME, order.getEmail(), correlationId, emailMessage);
    }

  }

}

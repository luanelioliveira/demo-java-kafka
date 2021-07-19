package io.github.luanelioliveira.samplekafka;

import io.github.luanelioliveira.samplekafka.consumer.ConsumerService;
import io.github.luanelioliveira.samplekafka.consumer.ServiceRunner;
import io.github.luanelioliveira.samplekafka.dispatcher.KafkaDispatcher;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class PaymentService implements ConsumerService<Order> {

  private static final int THREADS = 5;
  private static final String ORDER_CREATED_TOPIC_NAME = "ORDER_CREATED";
  private static final String PAYMENT_APPROVED_TOPIC_NAME = "PAYMENT_APPROVED";
  private static final String PAYMENT_REJECTED_TOPIC_NAME = "PAYMENT_REJECTED";

  public static void main(String[] args) {
    new ServiceRunner(PaymentService::new).start(THREADS);
  }

  @Override
  public String getConsumerGroup() {
    return PaymentService.class.getSimpleName();
  }

  @Override
  public String getTopic() {
    return ORDER_CREATED_TOPIC_NAME;
  }

  @Override
  public void consume(ConsumerRecord<String, Message<Order>> record)
      throws ExecutionException, InterruptedException {
    System.out.println("------------------------------------------");
    System.out.println("Processing payment order: " + record.value());

    var message = record.value();
    var order = message.getPayload();

    var isSuccessfully = process(order);

    try (var dispatcher = new KafkaDispatcher<>()) {
      if (isSuccessfully) {
        System.out.println("Order(" + order.getOrderId() + ") Approved!");
        var correlationId = message.getId().continueWith(PaymentService.class.getSimpleName());
        dispatcher.send(PAYMENT_APPROVED_TOPIC_NAME, order.getEmail(), correlationId, order);
      } else {
        System.out.println("Order(" + order.getOrderId() + ") Rejected!");
        var correlationId = message.getId().continueWith(PaymentService.class.getSimpleName());
        dispatcher.send(PAYMENT_REJECTED_TOPIC_NAME, order.getEmail(), correlationId, order);
      }
    }
  }

  private boolean process(Order order) {
    return isPaymentApproved(order);
  }

  private boolean isPaymentApproved(Order order) {
    return order.getAmount().compareTo(new BigDecimal("4500")) <= 0;
  }

}

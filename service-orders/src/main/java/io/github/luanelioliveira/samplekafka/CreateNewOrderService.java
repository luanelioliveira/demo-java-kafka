package io.github.luanelioliveira.samplekafka;

import io.github.luanelioliveira.samplekafka.dispatcher.KafkaDispatcher;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class CreateNewOrderService {

  private final static String ORDER_CREATED_TOPIC_NAME = "ORDER_CREATED";

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    var service = new CreateNewOrderService();

    try (var orderDispatcher = new KafkaDispatcher<>()) {
      for (var i = 0; i < 10; i++) {
        var correlationId = new CorrelationId(CreateNewOrderService.class.getSimpleName());
        var order = service.createNewOrder();
        orderDispatcher.send(ORDER_CREATED_TOPIC_NAME, order.getEmail(), correlationId, order);
      }
    }
  }

  private Order createNewOrder() {
    var email = Math.random() + "@test.com.br";
    var orderId = UUID.randomUUID().toString();
    var amount = new BigDecimal(Math.random() * 5000 + 1);
    return new Order(email, orderId, amount);
  }

}

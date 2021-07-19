package io.github.luanelioliveira.samplekafka.consumer;

import io.github.luanelioliveira.samplekafka.DeadLetterUnavailableException;
import java.util.concurrent.Callable;

public class ServiceProvider<T> implements Callable<Void> {

  private final ConsumerService<T> service;

  public ServiceProvider(ServiceFactory<T> factory) {
    this.service = factory.create();
  }

  @Override
  public Void call() throws DeadLetterUnavailableException {
    try (var kafkaService = new KafkaService(service.getConsumerGroup(), service.getTopic(),
        service::consume)) {
      kafkaService.run();
    }
    return null;
  }

}

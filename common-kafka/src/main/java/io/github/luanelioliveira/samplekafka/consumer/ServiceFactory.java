package io.github.luanelioliveira.samplekafka.consumer;

public interface ServiceFactory<T> {

  ConsumerService<T> create();

}

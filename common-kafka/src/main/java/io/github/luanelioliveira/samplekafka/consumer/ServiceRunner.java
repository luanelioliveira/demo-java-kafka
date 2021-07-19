package io.github.luanelioliveira.samplekafka.consumer;

import java.util.concurrent.Executors;

public class ServiceRunner<T> {

  private final ServiceProvider<T> provider;

  public ServiceRunner(ServiceFactory<T> factory) {
    this.provider = new ServiceProvider<>(factory);
  }

  public void start(int threadCount) {
    var pool = Executors.newFixedThreadPool(threadCount);
    for (int index = 0; index < threadCount; index++) {
      pool.submit(provider);
    }

  }
}

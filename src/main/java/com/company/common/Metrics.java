package com.company.common;

public enum Metrics {

  // Hello metrics
  HELLO_COUNT("hello_count"),
  HELLO_LATENCY("hello_requests_latency"),

  // Goodbye metrics
  GOODBYE_COUNT("goodbye_count"),
  GOODBYE_LATENCY("goodbye_requests_latency");

  private String name;

  Metrics(String name) {
    this.name = name;
  }

  public static Metrics metricsEnum(String name) {
    return Metrics.valueOf(name);
  }
}

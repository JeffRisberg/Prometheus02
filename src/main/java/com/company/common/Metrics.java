package com.company.common;

public enum Metrics {

  // Hello
  HELLO_COUNT("hello_worlds_total"),
  HELLO_LATENCY("hello_requests_latency_seconds"),

  // Goodbye
  GOODBYE_COUNT("goodbye_worlds_total"),
  GOODBYE_LATENCY("goodbye_requests_latency_seconds");

  private String name;

  Metrics(String name) {
    this.name = name;
  }

  public static Metrics metricsEnum(String name) {
    return Metrics.valueOf(name);
  }
}

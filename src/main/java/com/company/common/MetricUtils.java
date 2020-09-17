package com.company.common;

import com.codahale.metrics.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class MetricUtils {
  private static final MetricRegistry metrics = DropwizardMetrics.getInstance().getRegistry();

  private static String metricName(String metricName) {
    return MetricRegistry.name(MetricUtils.class, metricName);
  }

  private static String metricName(String tenantId, String metricName) {
    return MetricRegistry.name(MetricUtils.class, tenantId, metricName);
  }

  /** bump a counter */
  public static void increment(Metrics metric) {
    Counter counter = getCounter(metric);
    counter.inc();
  }

  /** bump a counter */
  public static void increment(String tenantId, Metrics metric) {
    Counter counter = getCounter(tenantId, metric);
    counter.inc();
  }

  /** record a timing */
  public static void time(Metrics metric, Callable<Void> callable) throws Exception {
    Timer timer = getTimer(metric);
    timer.time(callable);
  }

  /** record a timing */
  public static void time(String tenantId, Metrics metric, Callable<Void> callable)
      throws Exception {
    Timer timer = getTimer(tenantId, metric);
    timer.time(callable);
  }

  /** record a timingValue */
  public static void time(Metrics metric, long timeInMillis) {
    Timer timer = getTimer(metric);
    timer.update(timeInMillis, TimeUnit.MILLISECONDS);
  }

  /** record a timingValue */
  public static void time(String tenantId, Metrics metric, long timeInMillis) {
    Timer timer = getTimer(tenantId, metric);
    timer.update(timeInMillis, TimeUnit.MILLISECONDS);
  }

  /** add value to a histogram */
  public static void update(Metrics metric, long value) {
    Histogram histogram = getHistogram(metric);
    histogram.update(value);
  }

  /** add value to a histogram */
  public static void update(String tenantId, Metrics metric, long value) {
    Histogram histogram = getHistogram(tenantId, metric);
    histogram.update(value);
  }

  /** get or create a counter */
  public static Counter getCounter(Metrics metric) {
    return metrics.counter(metric.name());
  }

  /** get or create a counter */
  public static Counter getCounter(String tenantId, Metrics metric) {
    String name = MetricRegistry.name(tenantId, metric.name());
    return metrics.counter(name);
  }

  /** get or create a meter */
  public static Meter getMeter(Metrics metric) {
    String name = MetricRegistry.name(metric.name());
    return metrics.meter(name);
  }

  /** get or create a meter */
  public static Meter getMeter(String tenantId, Metrics metric) {
    String name = MetricRegistry.name(tenantId, metric.name());
    return metrics.meter(name);
  }

  /** get or create a timer */
  public static Timer getTimer(Metrics metric) {
    String name = MetricRegistry.name(metric.name());
    return metrics.timer(name);
  }

  /** get or create a timer */
  public static Timer getTimer(String tenantId, Metrics metric) {
    String name = MetricRegistry.name(tenantId, metric.name());
    return metrics.timer(name);
  }

  /** get or create a histogram */
  public static Histogram getHistogram(Metrics metric) {
    String name = MetricRegistry.name(metric.name());
    return metrics.histogram(name);
  }

  /** get or create a histogram */
  public static Histogram getHistogram(String tenantId, Metrics metric) {
    String name = MetricRegistry.name(tenantId, metric.name());
    return metrics.histogram(name);
  }
}

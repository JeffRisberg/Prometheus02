package com.company.common;

import com.codahale.metrics.*;

public class MetricUtils {
  private static final MetricRegistry metrics = DropwizardMetrics.getInstance().getRegistry();

  private static String metricName(String counterName) {
    return MetricRegistry.name(MetricUtils.class, counterName);
  }

  private static String metricName(String tenantId, String counterName) {
    return MetricRegistry.name(MetricUtils.class, tenantId, counterName);
  }

  public static void increment(Metrics metric) {
    metrics.counter(metricName(metric.name())).inc();
  }

  public static void increment(String tenantId, Metrics metric) {
    metrics.counter(metricName(tenantId, metric.name())).inc();
  }

  public static Meter getMeter(Metrics metric) {
    // get or create
    return metrics.meter(metric.name());
  }

  public static Timer getTimer(Metrics metric) {
    // get or create
    return metrics.timer(metric.name());
  }
}

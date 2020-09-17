package com.company;

import com.company.common.*;
import io.prometheus.client.exporter.MetricsServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {

  static class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {

      // Here we use the explicit calculation of deltaTimeMillis
      long startTime = System.currentTimeMillis();

      // Increment the number of requests.
      MetricUtils.increment(Metrics.HELLO_COUNT);

      System.out.println(req.getRequestURI());
      System.out.println(req.getMethod());
      System.out.println("count " + MetricUtils.getCounter(Metrics.HELLO_COUNT).getCount());

      try {
        resp.getWriter().println("Hello World!");
      } catch (Exception e) {
      }

      long deltaTimeMillis = System.currentTimeMillis() - startTime;
      System.out.println(deltaTimeMillis);
      MetricUtils.time(Metrics.HELLO_LATENCY, deltaTimeMillis);
    }
  }

  static class GoodbyeServlet extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {

      // Here we use the Callable lambda approach
      try {
        MetricUtils.time(
            Metrics.GOODBYE_LATENCY,
            () -> {
              // Increment the number of requests.
              MetricUtils.increment(Metrics.GOODBYE_COUNT);

              System.out.println(req.getRequestURI());
              System.out.println(req.getMethod());
              System.out.println(
                  "count " + MetricUtils.getCounter(Metrics.GOODBYE_COUNT).getCount());

              try {
                resp.getWriter().println("Goodbye World!");
              } catch (Exception e) {
              }
              return null;
            });
      } catch (Exception e) {
      }
    }
  }

  public static void main(String[] args) throws Exception {
    Server server = new Server(8000);
    ServletContextHandler context = new ServletContextHandler();
    context.setContextPath("/");
    server.setHandler(context);

    // Add metrics about CPU, JVM memory etc.
    DropwizardMetrics.getInstance();

    // Expose our example servlets.
    context.addServlet(new ServletHolder(new HelloServlet()), "/hello");
    context.addServlet(new ServletHolder(new GoodbyeServlet()), "/goodbye");

    // Expose Prometheus metrics.
    context.addServlet(new ServletHolder(new MetricsServlet()), "/metrics");

    // Start the webserver.
    server.start();
    server.join();
  }
}

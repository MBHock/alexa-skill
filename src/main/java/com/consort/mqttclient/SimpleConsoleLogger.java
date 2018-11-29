package com.consort.mqttclient;

import java.time.LocalDateTime;
import java.util.function.Supplier;

/**
 * @author <a href="mailto:mojammal.hock@consort-group.com">Mojammal Hock</a>
 */
public class SimpleConsoleLogger {

  private final String loggername;

  private SimpleConsoleLogger(String loggername) {
    this.loggername = loggername;
  }

  public static SimpleConsoleLogger getLogger(String loggername) {
    return new SimpleConsoleLogger(loggername);
  }

  public void log(Supplier<String> logSupplier) {
    System.out.println(String.format("%s %s: %s", LocalDateTime.now(), loggername, logSupplier.get()));
  }

  public void log(String format, Object... args) {
    System.out.println(String.format("%s %s: %s", LocalDateTime.now(), loggername, String.format(format, args)));
  }
}

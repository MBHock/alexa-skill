package com.consort.mqttmsg;

import java.util.function.Supplier;

/**
 * @author <a href="mailto:mojammal.hock@consort-group.com">Mojammal Hock</a>
 */
public class ConsortLogger {

  public void log(Supplier<String> logSupplier) {
    System.out.println(logSupplier.get());
  }

  public void log(String format, Object... args) {
    System.out.println(String.format(format, args));
  }
}

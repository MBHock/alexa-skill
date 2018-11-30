package com.consort.mqttclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * @author <a href="mailto:mojammal.hock@consort-group.com">Mojammal Hock</a>
 */
public enum MqttConnectConfig {

  MQTT_HOST, MQTT_LOGIN, MQTT_PWD, MQTT_PORT, MQTT_PROTOCOL;

  // private static final Logger logger =
  // LogManager.getLogger(MqttConnectConfig.class);

  private static Properties properties = new Properties();
  static {
    try (InputStream resource = MqttConnectConfig.class.getClassLoader().getResourceAsStream("mqtt.properties")) {
      properties.load(resource);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public static String getEnvironmentProperty(MqttConnectConfig key) {
    String value = System.getenv(key.name());
    if (Objects.isNull(value)) {
      value = properties.getProperty(key.name());
    }
    return Objects.nonNull(value) ? value.trim() : null;
  }

  public static Boolean getEnvironmentPropertyAsBoolean(MqttConnectConfig key) {
    return Boolean.parseBoolean(getEnvironmentProperty(key));
  }

  public static Integer getEnvironmentPropertyAsInteger(MqttConnectConfig key) {
    return Integer.parseInt(getEnvironmentProperty(key));
  }
}

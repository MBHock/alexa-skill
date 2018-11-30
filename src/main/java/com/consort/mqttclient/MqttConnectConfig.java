package com.consort.mqttclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import com.consort.logger.SimpleConsoleLogger;

/**
 * @author <a href="mailto:mojammal.hock@consort-group.com">Mojammal Hock</a>
 */
public enum MqttConnectConfig {

  MQTT_HOST, MQTT_LOGIN, MQTT_PWD, MQTT_PORT, MQTT_PROTOCOL;

  private static SimpleConsoleLogger logger = SimpleConsoleLogger.getLogger(MqttConnectConfig.class.getSimpleName());

  private static Properties properties = new Properties();
  static {
    try (InputStream resource = MqttConnectConfig.class.getClassLoader().getResourceAsStream("mqtt.properties")) {
      properties.load(resource);
    } catch (IOException e) {
      logger.log(() -> e.getMessage());
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

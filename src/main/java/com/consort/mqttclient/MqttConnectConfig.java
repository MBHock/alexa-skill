package com.consort.mqttclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import com.consort.mqttmsg.ConsortLogger;

/**
 * @author <a href="mailto:mojammal.hock@consort-group.com">Mojammal Hock</a>
 */
public enum MqttConnectConfig {

  PORT, DB_HOST, DB_PORT, DB_DATABASE, DB_ORDERS_COLLECTION, DB_HANDLING_UNITS_COLLECTION, DB_LOGIN, DB_PWD, S3_HOST,
  S3_MAIL_BUCKET, S3_LOGIN, S3_PWD, S3_MAIL_TEMPLATE, MQTT_HOST, MQTT_ORDER_IMPORT_TOPIC, MQTT_TSC_EVENTS_TOPIC,
  MQTT_LOGIN, MQTT_PWD, MQTT_CLIENT_ID, MQTT_PERSISTENT_SESSION;
  private static ConsortLogger logger = new ConsortLogger();

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

package com.consort.mqttclient;

import java.util.Objects;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

/**
 * @author <a href="mailto:mojammal.hock@consort-group.com">Mojammal Hock</a>
 */
public enum MachineMqttMessageSender {

  INSTANCE;

  private static final int THREAD_POOL_SIZE = 6;
  private SimpleConsoleLogger logger = SimpleConsoleLogger.getLogger(MachineMqttMessageSender.class.getSimpleName());
  private IMqttClient client;

  private MqttConnectOptions getMqttConnectOptions() {
    final MqttConnectOptions options = new MqttConnectOptions();
    options.setAutomaticReconnect(true);
    options.setCleanSession(false);
    // connection timeout default 30 seconds
    // keep alive interval: default 60 seconds
    String password = MqttConnectConfig.getEnvironmentProperty(MqttConnectConfig.MQTT_PWD);
    options.setPassword(Objects.nonNull(password) ? password.toCharArray() : null);
    options.setUserName(MqttConnectConfig.getEnvironmentProperty(MqttConnectConfig.MQTT_LOGIN));
    String[] serverUris = { MqttConnectConfig.getEnvironmentProperty(MqttConnectConfig.MQTT_HOST) };
    options.setServerURIs(serverUris);
    logger.log(() -> options.getDebug().toString());

    return options;
  }

  private MachineMqttMessageSender() {
    logger.log("ScheduledExecutorService is created with %d", THREAD_POOL_SIZE);

    MqttClientPersistence persistence = new MqttDefaultFilePersistence();
    try {
      client = new MqttClient(MqttConnectConfig.getEnvironmentProperty(MqttConnectConfig.MQTT_HOST),
          MqttClient.generateClientId(), persistence);
      logger.log("Cleint is created %s", client);
    } catch (MqttException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public synchronized void publish(String topic, MqttMessage message) {
    try {
      if (!client.isConnected()) {
        logger.log("Cleint is not connected %s", client);
        client.connect(getMqttConnectOptions());
      }

      logger.log("Topic=%s, Message=%s", topic, message);
      client.publish(topic, message);
    } catch (MqttException e) {
      logger.log(e::getMessage);
    }
  }

  public void shutdown() throws MqttException {
    client.disconnect();
  }
}
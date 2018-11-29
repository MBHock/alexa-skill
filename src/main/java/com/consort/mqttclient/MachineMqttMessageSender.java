package com.consort.mqttclient;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import com.consort.mqttmsg.ConsortLogger;

/**
 * @author <a href="mailto:mojammal.hock@consort-group.com">Mojammal Hock</a>
 */
public enum MachineMqttMessageSender {

  INSTANCE;

  private static final int THREAD_POOL_SIZE = 6;
  private ConsortLogger logger = new ConsortLogger();
  private ScheduledExecutorService scheduledExecutorService;
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

    scheduledExecutorService = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
    logger.log("ScheduledExecutorService is created with %d", THREAD_POOL_SIZE);

    MqttClientPersistence persistence = new MqttDefaultFilePersistence();
    try {
      client = new MqttClient(MqttConnectConfig.getEnvironmentProperty(MqttConnectConfig.MQTT_HOST),
          MqttClient.generateClientId(), persistence, scheduledExecutorService);
      logger.log("Cleint is created %s", client);
    } catch (MqttException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public void publish(String topic, MqttMessage message) throws MqttException {
    if (!client.isConnected()) {
      client.connect(getMqttConnectOptions());
    }

    logger.log("Topic=%s, Message=%s", topic, message);
    client.publish(topic, message);
  }

  public void shutdown() throws MqttException {
    client.disconnect();

    if (!scheduledExecutorService.isShutdown()) {
      scheduledExecutorService.shutdown();
    }
  }
}
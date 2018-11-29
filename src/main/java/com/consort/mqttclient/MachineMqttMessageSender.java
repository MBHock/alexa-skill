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
public class MachineMqttMessageSender {

  private SimpleConsoleLogger logger = SimpleConsoleLogger.getLogger(MachineMqttMessageSender.class.getSimpleName());
  private IMqttClient client;
  private static MachineMqttMessageSender INSTANCE;

  public static synchronized MachineMqttMessageSender getIntance() {
    if (Objects.isNull(INSTANCE)) {
      INSTANCE = new MachineMqttMessageSender();
    }

    return INSTANCE;
  }

  private MachineMqttMessageSender() {
    MqttClientPersistence persistence = new MqttDefaultFilePersistence();
    try {
      client = new MqttClient(MqttConnectConfig.getEnvironmentProperty(MqttConnectConfig.MQTT_HOST),
          MqttClient.generateClientId(), persistence);
      client.connect(getMqttConnectOptions());
      logger.log("Cleint is created %s", client);
    } catch (MqttException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private MqttConnectOptions getMqttConnectOptions() {
    final MqttConnectOptions options = new MqttConnectOptions();
    // options.setAutomaticReconnect(true);
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

  public void publish(String topic, MqttMessage message) {
    try {
      logger.log("Topic=%s, Message=%s", topic, message);
      client.publish(topic, message);
    } catch (MqttException e) {
      logger.log(e::getMessage);
    }
  }

  public void shutdown() throws MqttException {
    // client.disconnect();
    client.disconnectForcibly();
  }
}
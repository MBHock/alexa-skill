package com.consort.mqttclient;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author <a href="mailto:mojammal.hock@consort-group.com">Mojammal Hock</a>
 */
public class MachineMqttNotificationListener implements MqttCallbackExtended {

  public void connectionLost(Throwable cause) {
    System.out.print(String.format("Connection is lost to the server. Reason: %s", cause.getMessage()));
  }

  public void messageArrived(String topic, MqttMessage message) throws Exception {
    System.out.print(String.format("Msg [%s] obtained in topic [%s]", message.toString(), topic));
  }

  public void deliveryComplete(IMqttDeliveryToken token) {
    System.out.print(String.format("Msg [%b] has been delivered", token.isComplete()));
  }

  public void connectComplete(boolean reconnect, String serverURI) {
    System.out.print(String.format("Successfully connected to  [%s]", serverURI));
  }
}

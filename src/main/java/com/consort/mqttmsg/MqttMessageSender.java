package com.consort.mqttmsg;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import com.consort.mqttclient.MachineMqttMessageSender;

/**
 * @author <a href="mailto:mojammal.hock@consort-group.com">Mojammal Hock</a>
 */
public class MqttMessageSender {

  /**
   * @param args
   * @throws MqttException
   * @throws MqttSecurityException
   */
  public static void main(String[] args) throws MqttException {
    String messageToPub = "A simple message from the java world!";

    MqttMessage message = new MqttMessage();
    message.setQos(1);
    message.setPayload(messageToPub.getBytes());

    MachineMqttMessageSender.INSTANCE.publish("/alexa", message);
    MachineMqttMessageSender.INSTANCE.shutdown();
  }

}

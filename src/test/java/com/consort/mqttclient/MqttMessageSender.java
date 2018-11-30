package com.consort.mqttclient;

import java.time.LocalDateTime;

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
   * @throws InterruptedException
   * @throws MqttSecurityException
   */
  public static void main(String[] args) throws MqttException {
    System.out.println(String.format("%s Start sending message.", LocalDateTime.now()));

    for (int counter = 0; counter < 20; counter++) {
      createCommand(counter);
    }

    System.out.println(String.format("%s Message has been sent.", LocalDateTime.now()));
    MachineMqttMessageSender sender = MachineMqttMessageSender.getIntance();
    sender.shutdown();
    System.out.println(String.format("%s shutdown has been called.", LocalDateTime.now()));
    System.exit(0);
  }

  private static void createCommand(int counter) {
    MachineMqttMessageSender sender = MachineMqttMessageSender.getIntance();
    String messageToPub = String.format("Sample java programm is sending a message to MQTT server. Message number %d",
        counter);

    MqttMessage message = new MqttMessage();
    message.setPayload(messageToPub.getBytes());
    sender.publish("/alexa", message);
  }
}
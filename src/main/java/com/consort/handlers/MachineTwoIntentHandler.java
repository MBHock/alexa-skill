
package com.consort.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.consort.mqttclient.MachineMqttMessageSender;
import com.consort.skillregister.MachineConfig;

public class MachineTwoIntentHandler implements RequestHandler {
  @Override
  public boolean canHandle(HandlerInput input) {
    return input.matches(intentName(MachineConfig.MACHINE_TWO_OK.getIntentIdentity())
        .or(intentName(MachineConfig.MACHINE_TWO_NOK.getIntentIdentity())));
  }

  @Override
  public Optional<Response> handle(HandlerInput input) {
    String messageToPub = null;
    if (input.matches(intentName(MachineConfig.MACHINE_TWO_OK.getIntentIdentity()))) {
      messageToPub = String.format("MachineId: %s, Status: %s", "2", "OK");
    } else {
      messageToPub = String.format("MachineId: %s, Status: %s", "2", "NOK");
    }

    MqttMessage message = new MqttMessage();
    message.setPayload(messageToPub.getBytes());

    MachineMqttMessageSender sender = MachineMqttMessageSender.getIntance();
    sender.publish("/alexa", message);

    String speechText = "Machine two has been notified.";
    return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("MachineStatus", speechText).build();
  }

}

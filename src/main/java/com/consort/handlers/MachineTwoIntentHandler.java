
package com.consort.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.consort.event.MachineMqttEvent;
import com.consort.event.MachineStatus;
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
    MachineMqttEvent mqttEvent = new MachineMqttEvent();
    MqttMessage message = new MqttMessage();

    mqttEvent.setMachineID(MachineConfig.MACHINE_TWO_OK.getMachineIdentity());

    if (input.matches(intentName(MachineConfig.MACHINE_TWO_OK.getIntentIdentity()))) {
      mqttEvent.setMachineStatus(MachineStatus.OK);
    } else {
      mqttEvent.setMachineStatus(MachineStatus.DEFECT);
    }

    message.setPayload(mqttEvent.toString().getBytes());
    MachineMqttMessageSender sender = MachineMqttMessageSender.getIntance();
    sender.publish(MachineConfig.MACHINE_TWO_OK.getTopicName(), message);

    String speechText = "Machine two has been notified.";
    return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("MachineStatus", speechText).build();
  }

}

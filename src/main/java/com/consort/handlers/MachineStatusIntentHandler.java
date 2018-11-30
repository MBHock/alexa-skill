/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package com.consort.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Map;
import java.util.Optional;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.consort.event.MachineMqttEvent;
import com.consort.event.MachineStatus;
import com.consort.mqttclient.MachineMqttMessageSender;
import com.consort.skillregister.MachineIntentCommand;
import com.consort.skillregister.SkillSlot;

public class MachineStatusIntentHandler implements RequestHandler {

  private static final String MACHINE_ID_PREFIX = "U710-";
  private static final String TOPIC_NAME_PREFIX = "/production/data/";

  @Override
  public boolean canHandle(HandlerInput input) {

    return input.matches(intentName(MachineIntentCommand.CHANGE_MACHINE_STATUS.getIntentIdentity()));

  }

  @Override
  public Optional<Response> handle(HandlerInput input) {
    String speechText = null;
    try {
      if (input.getRequestEnvelope().getRequest() instanceof IntentRequest) {
        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        Map<String, Slot> solts = intentRequest.getIntent().getSlots();
        MachineMqttEvent mqttEvent = new MachineMqttEvent();

        Slot slot = solts.get(SkillSlot.MACHINENUMBER.getSlotKey());
        speechText = "Machine " + slot.getValue() + " has been notified.";
        String machineId = MACHINE_ID_PREFIX + slot.getValue();
        mqttEvent.setMachineID(machineId);

        slot = solts.get(SkillSlot.MACHINESTATUS.getSlotKey());
        mqttEvent.setMachineStatus(MachineStatus.fromString(slot.getValue()));

        MqttMessage message = new MqttMessage();

        // TODO: need JSON formatter
        message.setPayload(mqttEvent.toString().getBytes());
        MachineMqttMessageSender sender = new MachineMqttMessageSender();
        sender.publish(String.join("/", TOPIC_NAME_PREFIX, machineId), message);
        sender.shutdown();
        return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("MachineStatus", speechText).build();
      }

      speechText = "Required machine number and machine status were missing to fulfil the request.";

    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      speechText = "Something went wrong. I could not fullfill your request";
    }

    return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("MachineStatus", speechText).build();
  }

}

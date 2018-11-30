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

import java.util.Optional;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.consort.event.MachineMqttEvent;
import com.consort.event.MachineStatus;
import com.consort.mqttclient.MachineMqttMessageSender;
import com.consort.skillregister.MachineConfig;

public class MachineOneIntentHandler implements RequestHandler {

  @Override
  public boolean canHandle(HandlerInput input) {
    return input.matches(intentName(MachineConfig.MACHINE_ONE_OK.getIntentIdentity())
        .or(intentName(MachineConfig.MACHINE_ONE_NOK.getIntentIdentity())));
  }

  @Override
  public Optional<Response> handle(HandlerInput input) {
    MachineMqttEvent mqttEvent = new MachineMqttEvent();
    MqttMessage message = new MqttMessage();

    mqttEvent.setMachineID(MachineConfig.MACHINE_ONE_OK.getMachineIdentity());

    if (input.matches(intentName(MachineConfig.MACHINE_ONE_OK.getIntentIdentity()))) {
      mqttEvent.setMachineStatus(MachineStatus.OK);
    } else {
      mqttEvent.setMachineStatus(MachineStatus.DEFECT);
    }

    message.setPayload(mqttEvent.toString().getBytes());
    MachineMqttMessageSender sender = MachineMqttMessageSender.getIntance();
    sender.publish(MachineConfig.MACHINE_ONE_OK.getTopicName(), message);

    String speechText = "Machine one has been notified.";
    return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("MachineStatus", speechText).build();
  }

}

package com.consort.skillregister;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.consort.handlers.CancelandStopIntentHandler;
import com.consort.handlers.FallbackIntentHandler;
import com.consort.handlers.HelpIntentHandler;
import com.consort.handlers.LaunchRequestHandler;
import com.consort.handlers.MachineStatusIntentHandler;
import com.consort.handlers.SessionEndedRequestHandler;

public class MachineSkillHandler extends SkillStreamHandler {

  //@formatter:off
  private static Skill getSkill() {
    return Skills.standard()
        .addRequestHandlers(
            new CancelandStopIntentHandler(),
            new MachineStatusIntentHandler(),
            new HelpIntentHandler(),
            new LaunchRequestHandler(), 
            new SessionEndedRequestHandler(), 
            new FallbackIntentHandler()
            )
        .build();
  }
  //@formatter:on

  public MachineSkillHandler() {
    super(getSkill());
  }
}

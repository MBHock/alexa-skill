package com.consort.skillregister;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.consort.handlers.CancelandStopIntentHandler;
import com.consort.handlers.FallbackIntentHandler;
import com.consort.handlers.HelpIntentHandler;
import com.consort.handlers.LaunchRequestHandler;
import com.consort.handlers.MachineTwoIntentHandler;
import com.consort.handlers.MachineOneIntentHandler;
import com.consort.handlers.SessionEndedRequestHandler;

public class ScrumArtifactHandler extends SkillStreamHandler {

  //@formatter:off
  private static Skill getSkill() {
    return Skills.standard()
        .addRequestHandlers(
            new CancelandStopIntentHandler(), 
            new MachineTwoIntentHandler(), 
            new HelpIntentHandler(),
            new LaunchRequestHandler(), 
            new SessionEndedRequestHandler(), 
            new FallbackIntentHandler(),
            new MachineOneIntentHandler())
        .build();
  }
  //@formatter:on

  public ScrumArtifactHandler() {
    super(getSkill());
  }
}

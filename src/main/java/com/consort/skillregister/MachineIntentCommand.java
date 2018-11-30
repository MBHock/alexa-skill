package com.consort.skillregister;

/**
 * @author <a href="mailto:mojammal.hock@consort-group.com">Mojammal Hock</a>
 */
public enum MachineIntentCommand {

  //@formatter:off
  CHANGE_MACHINE_STATUS("ChangeMachineStatus");
  //@formatter:on

  private final String intentIdentity;

  private MachineIntentCommand(String intentIdentiry) {
    this.intentIdentity = intentIdentiry;
  }

  /**
   * The intent identity is triggered by the Alexa and AWS lambda-fuction calls
   * the appropriate Handler, which has been registered to the Handlers.
   * 
   * @return the intentIdentity {@link String}
   */
  public String getIntentIdentity() {
    return intentIdentity;
  }
}

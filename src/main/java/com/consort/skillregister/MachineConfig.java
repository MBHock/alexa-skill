package com.consort.skillregister;

/**
 * @author <a href="mailto:mojammal.hock@consort-group.com">Mojammal Hock</a>
 */
public enum MachineConfig {

  //@formatter:off
  MACHINE_ONE_OK("MachineOneOk", "U710-1", "/production/data/U710-1"), 
  MACHINE_ONE_NOK("MachineOneNok", "U710-1", "/production/data/U710-1"), 
  MACHINE_TWO_OK("MachineTwoOk", "U710-2", "/production/data/U710-2"),
  MACHINE_TWO_NOK("MachineTwoNok", "U710-2", "/production/data/U710-2"), 
  MACHINE_THREE_OK("MachineThreeOk", "U710-3", "/production/data/U710-3"), 
  MACHINE_THREE_NOK("MachineThreeNok", "U710-3", "/production/data/U710-3");
  //@formatter:on

  private final String intentIdentity;
  private final String machineIdentity;
  private final String topicName;

  private MachineConfig(String intentIdentiry, String machineIdentity, String desiredTopicName) {
    this.intentIdentity = intentIdentiry;
    this.machineIdentity = machineIdentity;
    this.topicName = desiredTopicName;
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

  /**
   * It returns the machine ID of an Enum, which denotes a machine
   * 
   * @return the machineIdentity {@link String}
   */
  public String getMachineIdentity() {
    return machineIdentity;
  }

  /**
   * It returns the desired topic name, where the MQTT event should be send.
   * Currently the topic name follows the following convention
   * <code>/production/data/&lt;machine-id&gt;</code>.
   * 
   * @return the topicName {@link String}
   */
  public String getTopicName() {
    return topicName;
  }
}

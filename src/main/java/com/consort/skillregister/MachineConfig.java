package com.consort.skillregister;

/**
 * @author <a href="mailto:mojammal.hock@consort-group.com">Mojammal Hock</a>
 */
public enum MachineConfig {

  MACHINE_ONE_OK("MachineOneOk"), MACHINE_ONE_NOK("MachineOneNok"), MACHINE_TWO_OK("MachineTwoOk"),
  MACHINE_TWO_NOK("MachineTwoNok"), MACHINE_THREE_OK("MachineThreeOk"), MACHINE_THREE_NOK("MachineThreeNok");

  private String intentIdentity;

  private MachineConfig(String intentIdentiry) {
    this.intentIdentity = intentIdentiry;
  }

  public String getIntentIdentity() {
    return intentIdentity;
  }
}

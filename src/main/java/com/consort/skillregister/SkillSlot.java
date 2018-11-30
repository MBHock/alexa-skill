package com.consort.skillregister;

/**
 * @author <a href="mailto:mojammal.hock@consort-group.com">Mojammal Hock</a>
 */
public enum SkillSlot {

  MACHINENUMBER("machineNumber"), MACHINESTATUS("machineStatus");

  private final String slotKey;

  private SkillSlot(String slotKey) {
    this.slotKey = slotKey;
  }

  public String getSlotKey() {
    return slotKey;
  }
}

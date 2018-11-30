package com.consort.event;

/**
 * @author <a href="mailto:mojammal.hock@consort-group.com">Mojammal Hock</a>
 */
public class MachineMqttEvent {

  private String machineID;
  private MachineStatus machineStatus;
  private EventName eventName;
  private String information;

  /**
   * @return the machineID
   */
  public String getMachineID() {
    return machineID;
  }

  /**
   * @param machineID the machineID to set
   */
  public void setMachineID(String machineID) {
    this.machineID = machineID;
  }

  /**
   * @return the machineStatus
   */
  public MachineStatus getMachineStatus() {
    return machineStatus;
  }

  /**
   * @param machineStatus the machineStatus to set
   */
  public void setMachineStatus(MachineStatus machineStatus) {
    this.machineStatus = machineStatus;
  }

  /**
   * @return the eventName
   */
  public EventName getEventName() {
    return eventName;
  }

  /**
   * @param eventName the eventName to set
   */
  public void setEventName(EventName eventName) {
    this.eventName = eventName;
  }

  /**
   * @return the information
   */
  public String getInformation() {
    return information;
  }

  /**
   * @param information the information to set
   */
  public void setInformation(String information) {
    this.information = information;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format(
        "{%n\"MachineMqttEvent\": {%n \"machineID\": \"%s\",%n \"machineStatus\": \"%s\",%n \"eventName\": \"%s\",%n \"information\": \"%s\"%n }%n}",
        machineID, machineStatus, eventName, information);
  }
}

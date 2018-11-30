package com.consort.event;

import java.util.Objects;

/**
 * @author <a href="mailto:mojammal.hock@consort-group.com">Mojammal Hock</a>
 */
public enum MachineStatus {

  OK, DEFECT, BROKEN, IN_REPAIR, UNSTABLE, UNKNOWN;

  public static MachineStatus fromString(String stringvalue) {
    if (Objects.isNull(stringvalue)) {
      return null;
    }

    MachineStatus status = null;
    try {
      status = MachineStatus.valueOf(stringvalue);
    } catch (IllegalArgumentException ex) {
      // ignore, we will convert ourselvs.
    }

    if (Objects.isNull(status)) {
      switch (stringvalue) {
      case "down":
      case "unstable":
      case "problem":
      case "not functioning":
      case "not running":
      case "not working":
      case "broken":
      case "out of order":
      case "nok":
        status = DEFECT;
        break;
      case "enable":
      case "up":
      case "stable":
      case "functioning":
      case "no problem":
      case "running":
      case "working":
      case "fine":
        status = OK;
        break;
      default:
        break;
      }
    }

    return status;
  }
}

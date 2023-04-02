package com.neves6.piazzapanic.powerups;

import java.util.ArrayList;
import java.util.Map;

public class CheaperMachineUnlock extends BasePowerUp {
  /**
   * Constructor.
   *
   * @param effectTime How long the power up lasts.
   * @param name
   */
  public CheaperMachineUnlock(Long effectTime, String name) {
    super(effectTime, name);
  }

  public Map<String, ArrayList<Float>> applyPowerUp(Map<String, ArrayList<Float>> machineStatus) {
    if (getAcquiredStatus()) {
      setStartTime();
      for (String status : machineStatus.keySet())
        machineStatus.get(status).set(0, machineStatus.get(status).get(0) / 2);
    }
    return machineStatus;
  }

  public Map<String, ArrayList<Float>> endPowerUp(Map<String, ArrayList<Float>> machineStatus) {
    if (endTime()) {
      for (String status : machineStatus.keySet()) {
        machineStatus.get(status).set(0, machineStatus.get(status).get(0) * 2);
      }
    }
    return machineStatus;
  }
}

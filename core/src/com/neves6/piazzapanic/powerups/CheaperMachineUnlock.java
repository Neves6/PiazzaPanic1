/** NEW FEATURE. */

package com.neves6.piazzapanic.powerups;

import java.util.ArrayList;
import java.util.Map;

/** Allows all machines to be unlocked for half the price. */
public class CheaperMachineUnlock extends BasePowerUp {
  /**
   * Constructor.
   *
   * @param effectTime How long the power up lasts.
   * @param name Display name given to the power up.
   */
  public CheaperMachineUnlock(Long effectTime, String name) {
    super(effectTime, name);
  }

  /**
   * Sets all values within the array list that represent price to unlock group of machines to half
   * price.
   *
   * @param machineStatus A map containing a string key and an array with 2 values where index 1 is
   *     the price and index 2 tells you whether it is unlocked or not.
   * @return Map of machines at half the price.
   */
  public Map<String, ArrayList<Float>> applyPowerUp(Map<String, ArrayList<Float>> machineStatus) {
    if (getAcquiredStatus()) {
      setStartTime();
      for (String status : machineStatus.keySet()) {
        machineStatus.get(status).set(0, machineStatus.get(status).get(0) / 2);
      }
    }
    return machineStatus;
  }

  /**
   * Sets all values within the array list that represent price to unlock group of machines to full
   * price if power-up has ended its runtime.
   *
   * @param machineStatus A map containing a string key and an array with 2 values where index 1 is
   *     the price and index 2 tells you whether it is unlocked or not.
   * @return A map of machines at the original price if they were originally modified.
   */
  public Map<String, ArrayList<Float>> endPowerUp(Map<String, ArrayList<Float>> machineStatus) {
    if (endTime()) {
      for (String status : machineStatus.keySet()) {
        machineStatus.get(status).set(0, machineStatus.get(status).get(0) * 2);
      }
    }
    return machineStatus;
  }
}

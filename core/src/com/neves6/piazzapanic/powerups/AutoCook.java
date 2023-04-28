/** NEW FEATURE. */

package com.neves6.piazzapanic.powerups;

import com.neves6.piazzapanic.gamemechanisms.Machine;
import java.util.Map;

/** Finds the first current recipe that is being process and skips the cook time for it. */
public class AutoCook extends BasePowerUp {
  /**
   * Constructor.
   *
   * @param effectTime How long the power up lasts.
   * @param noWaitOnAMachine Display name given to the power up.
   */
  public AutoCook(Long effectTime, String noWaitOnAMachine) {
    super(effectTime, noWaitOnAMachine);
  }

  /**
   * Applies power-up if any machines are currently running.
   *
   * @param machines List of machines on the map.
   */
  public void applyPowerUp(Map<String, Machine> machines) {
    if (!(this.acquired)) {
      return;
    } else if (endTime()) {
      return;
    }
    for (String machine : machines.keySet()) {
      Machine tempMachine = machines.get(machine);
      if (tempMachine.getActive()) {
        float tempTime = tempMachine.getProcessingTime();
        tempMachine.changeProcessingTime(tempMachine.getRuntime() * 2F);
        tempMachine.attemptCompleteAction();
        tempMachine.changeProcessingTime(tempTime);
        tempMachine.incrementRuntime(
            machines.get(machine).getProcessingTime() - machines.get(machine).getRuntime());
        this.acquired = false;
        this.startTime = 0L;
        return;
      }
    }
  }
}

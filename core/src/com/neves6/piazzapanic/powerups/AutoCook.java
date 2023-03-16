package com.neves6.piazzapanic.powerups;

import com.neves6.piazzapanic.Machine;
import java.util.ArrayList;

/**
 * Finds the first current recipe that is being
 * process and skips the cook time for it.
 */
public class AutoCook extends BasePowerUp {
  /**
   * Constructor.
   * @param effectTime How long the power up lasts.
   * */
  public AutoCook(Long effectTime) {
    super(effectTime);
  }

  /**
   * Applies power-up if any machines are currently running.
   * @param machines List of machines on the map.
   */
  public void applyPowerUp(ArrayList<Machine> machines) {
    for (Machine machine : machines) {
      if (machine.getActive() == true) {
        machine.incrementRuntime(machine.getProcessingTime() - machine.getRuntime());
        this.aquired = false;
        return;
      }
    }
  }
}

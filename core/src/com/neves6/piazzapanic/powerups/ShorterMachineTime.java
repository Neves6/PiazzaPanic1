package com.neves6.piazzapanic.powerups;

import com.neves6.piazzapanic.gamemechanisms.Machine;
import java.util.ArrayList;
import java.util.Map;

/** A power up which makes food take a shorter amount of time to cook. */
public class ShorterMachineTime extends BasePowerUp {
  /**
   * Constructor.
   *
   * @param effectTime How long the power up lasts.
   */
  public ShorterMachineTime(Long effectTime) {
    super(effectTime);
  }

  /**
   * Takes all active machines in the game and changes their processing speed if the power up has
   * ended.
   *
   * @param machines List of all active machines.
   * @return Machines with decreasing processing speed.
   */
  public Map<String, Machine> applyPowerUp(Map<String, Machine> machines) {
    if (machines.size() < 1) {
      throw new IllegalArgumentException("Chefs list must have at least two chefs in.");
    }
    if (getAquiredStatus()) {
      setStartTime();
      for (String machine : machines.keySet()) {
        System.out.println(machines.get(machine).getProcessingTime() / 2);
        machines.get(machine).changeProcessingTime(machines.get(machine).getProcessingTime() / 2);
      }
    }
    return machines;
  }

  /**
   * Takes all active machines in the game and changes their processing speed to normal if the power
   * up has ended.
   *
   * @param machines List of all active machines.
   * @return Machines with normal processing speed.
   */
  public Map<String, Machine> endPowerUp(Map<String, Machine> machines) {
    if (machines.size() < 1) {
      throw new IllegalArgumentException("Chefs list must have at least two chefs in.");
    }
    if (endTime()) {
      for (String machine : machines.keySet()) {
        System.out.println(machines.get(machine).getProcessingTime() * 2);
        machines.get(machine).changeProcessingTime(machines.get(machine).getProcessingTime() * 2);
      }
    }
    return machines;
  }
}

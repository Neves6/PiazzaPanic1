package com.neves6.piazzapanic.powerups;

import com.neves6.piazzapanic.people.Chef;
import java.util.ArrayList;

/** Allows the chefs to go double speed for a given time. */
public class DoubleSpeed extends BasePowerUp {

  /**
   * Constructor.
   *
   * @param effectTime How long the effect will last for.
   */
  public DoubleSpeed(Long effectTime) {
    super(effectTime);
  }

  /**
   * Takes all active chefs in the game and changes their speed to if the power up is acquired.
   *
   * @param chefs List of all active chefs.
   * @return Chefs with modified speed.
   */
  public ArrayList<Chef> applyPowerUp(ArrayList<Chef> chefs) {
    if (chefs.size() < 1) {
      throw new IllegalArgumentException("Chefs list must have at least two chefs in.");
    }
    if (this.aquired) {
      setStartTime();
      for (Chef chef : chefs) {
        chef.alterSpeed(2);
      }
    }
    return chefs;
  }

  /**
   * Takes all active chefs in the game and changes their speed to normal if the power up has ended.
   *
   * @param chefs List of all active chefs.
   * @return Chefs with normal speed.
   */
  public ArrayList<Chef> endPowerUp(ArrayList<Chef> chefs) {
    if (chefs.size() < 1) {
      throw new IllegalArgumentException("Chefs list must have at least two chefs in.");
    }
    if (this.aquired && endTime()) {
      for (Chef chef : chefs) {
        chef.alterSpeed(1);
      }
    }
    return chefs;
  }
}

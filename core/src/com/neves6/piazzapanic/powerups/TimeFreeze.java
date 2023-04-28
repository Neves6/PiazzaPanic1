/** NEW FEATURE */
package com.neves6.piazzapanic.powerups;

/** Implements a power up which makes the timer freeze. */
public class TimeFreeze extends BasePowerUp {
  /**
   * Constructor.
   *
   * @param effectTime How long the power up lasts.
   * @param name Description of power up effects.
   */
  public TimeFreeze(Long effectTime, String name) {
    super(effectTime, name);
  }

  /**
   * Changes delta time to zero if the power up is active.
   *
   * @param delta The increment for the timer.
   * @return The increment for the time that will be applied to the output.
   */
  public float getDelta(float delta) {
    if (getAcquiredStatus()) {
      if (!endTime()) {
        setStartTime();
        return 0;
      }
    }
    return delta;
  }
}

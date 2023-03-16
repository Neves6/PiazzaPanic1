package com.neves6.piazzapanic.powerups;

/***
 * Implements a power up which makes the timer freeze.
 */
public class TimeFreeze extends BasePowerUp {
  /***
   * Constructor
   *
   * @param effectTime How long the power up lasts.
   * */
  public TimeFreeze(Long effectTime) {
    super(effectTime);
  }

  /***
   * Changes delta time to zero if the power up is active.
   * @param delta The increment for the timer.
   * @return The increment for the time that will be applied to the output.
   */
  public float getDelta(float delta) {
    if (getAquiredStatus()) {
      if (endTime() == false) {
        setStartTime();
        return 0;
      }
    }
    return delta;
  }
}

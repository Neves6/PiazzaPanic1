package com.neves6.piazzapanic.powerups;

/** Base class which is used for all powerups. Timing functions all located within this. */
public class BasePowerUp {
  Boolean aquired;
  Long startTime;
  Long effectTime;

  /**
   * Constructor.
   *
   * @param effectTime How long the power up lasts.
   * */
  public BasePowerUp(Long effectTime) {
    this.aquired = false;
    this.startTime = 0L;
    this.effectTime = effectTime;
  }

  /** Sets start time if it already hasn't been run and the power up is acquired. */
  public void setStartTime() {
    if (this.startTime == 0L && this.aquired) {
      this.startTime = System.currentTimeMillis();
    }
  }

  /**
   * If the time that the power up last for is over, it sets
   * the end time.
   *
   * @return Whether the time has been stopped or not.
   */
  public Boolean endTime() {
    if (System.currentTimeMillis() - this.startTime > effectTime && this.startTime != 0L) {
      this.aquired = false;
      return true;
    } else {
      return false;
    }
  }

  /**
   * Getter method.
   * @return whether you have the power-up or not
   */
  public Boolean getAquiredStatus() {
    return this.aquired;
  }

  /**
   * Set boolean value if the power up has just been attained.
   */
  public void aquirePowerUp() {
    this.aquired = true;
  }

  /**
   * Getter method.
   * @return the time the power up was applied.
   */
  public Long getStartTime() {
    return startTime;
  }

  /**
   * Getter method.
   * @return how long the power up lasts.
   */
  public Long getEffectTime() {
    return effectTime;
  }
}

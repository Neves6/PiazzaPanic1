package com.neves6.piazzapanic.powerups;

import org.json.simple.JSONObject;

/** Base class which is used for all powerups. Timing functions all located within this. */
public class BasePowerUp {
  Boolean acquired;
  Long startTime;
  Long effectTime;
  String name;

  /**
   * Constructor.
   *
   * @param effectTime How long the power up lasts.
   */
  public BasePowerUp(Long effectTime, String name) {
    this.acquired = false;
    this.startTime = 0L;
    this.effectTime = effectTime;
    this.name = name;
  }

  /** Sets start time if it already hasn't been run and the power up is acquired. */
  public void setStartTime() {
    if (this.startTime == 0L) {
      this.startTime = System.currentTimeMillis();
    }
  }

  /**
   * If the time that the power up last for is over, it sets the end time.
   *
   * @return Whether the time has been stopped or not.
   */
  public Boolean endTime() {
    if (System.currentTimeMillis() - this.startTime > effectTime && this.startTime != 0L) {
      this.acquired = false;
      this.startTime = 0L;
      return true;
    } else {
      return false;
    }
  }

  /**
   * Getter method.
   *
   * @return whether you have the power-up or not
   */
  public Boolean getAcquiredStatus() {
    return this.acquired;
  }

  /** Set boolean value if the power up has just been attained. */
  public void acquirePowerUp() {
    this.acquired = true;
    this.startTime = System.currentTimeMillis();
  }

  /**
   * Getter method.
   *
   * @return the time the power up was applied.
   */
  public Long getStartTime() {
    return startTime;
  }

  /**
   * Method to print status of power-up in a formatted way.
   *
   * @return String containing the name and the time that the power-up has been active for so far.
   */
  public String prettyPrint() {
    if (this.acquired) {
      Long inSeconds = (System.currentTimeMillis() - startTime) / 1000;
      return name + ":" + inSeconds.toString() + "s \n";
    } else {
      return "";
    }
  }

  /**
   * Saves the properties of a single power-up.
   *
   * @return JSON object containing whether the power-up is active and the time it has run for so
   *     far if required.
   */
  public JSONObject savePowerUp() {
    JSONObject powerup = new JSONObject();
    powerup.put("active", acquired);
    if (acquired) {
      powerup.put("active time", System.currentTimeMillis() - this.startTime);
    }
    return powerup;
  }

  /**
   * Loads a previously set up power-up.
   *
   * @param details JSONObject containing the time it has run for so far if it has been previous
   *     activated.
   */
  public void loadPowerup(JSONObject details) {
    if ((Boolean) details.get("active")) {
      this.acquired = true;
      this.startTime = System.currentTimeMillis() - (Long) details.get("active time");
    }
  }
}

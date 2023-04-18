package com.neves6.piazzapanic.powerups;

import static java.lang.Math.random;

import com.neves6.piazzapanic.gamemechanisms.GameSaver;
import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.people.Chef;
import java.util.ArrayList;
import java.util.Map;
import org.json.simple.JSONObject;

/** A class that can be used to control all the power-ups within the game. */
public class PowerUpRunner {
  final CheaperMachineUnlock CHEAPERMACHINEUNLOCK =
      new CheaperMachineUnlock(30000L, "1/2 price machines for 30 seconds");
  final ShorterMachineTime SHORTERMACHINETIME =
      new ShorterMachineTime(30000L, "1/2 machine time for 30 seconds");
  final AutoCook AUTOCOOK = new AutoCook(30000L, "no wait on a machine");
  final TimeFreeze TIMEFREEZE = new TimeFreeze(30000L, "time freeze for 30 seconds");
  final DoubleMoney DOUBLEMONEY = new DoubleMoney(30000L, "double money for 30 seconds");
  ArrayList<Chef> chefs;
  Map<String, Machine> machines;
  Money money;
  GameSaver saver;

  /**
   * Constructor method.
   *
   * @param chefs Chefs that are being displayed in the game.
   * @param machines Machines that are being used in the game.
   * @param money The currency system that is being used in the game.
   * @param saver Instance of GameSaver being used by the game.
   */
  public PowerUpRunner(
      ArrayList<Chef> chefs, Map<String, Machine> machines, Money money, GameSaver saver) {
    this.chefs = chefs;
    this.machines = machines;
    this.money = money;
    this.saver = saver;
  }

  /**
   * Choose a random number between 0 and 1 and convert to an integer in the range 0 to 4 and then
   * uses this to pick a random power-up to activate.
   */
  public void activateRandomPowerUp() {
    int random = (int) Math.round(random() / 0.25);
    switch (random) {
      case 0:
        CHEAPERMACHINEUNLOCK.acquirePowerUp();
        break;
      case 1:
        SHORTERMACHINETIME.acquirePowerUp();
        break;
      case 2:
        DOUBLEMONEY.acquirePowerUp();
        break;
      case 3:
        AUTOCOOK.acquirePowerUp();
        break;
      case 4:
        TIMEFREEZE.acquirePowerUp();
        break;
    }
    machines = SHORTERMACHINETIME.applyPowerUp(machines);
    DOUBLEMONEY.applyPowerUp(money);
    CHEAPERMACHINEUNLOCK.applyPowerUp(money.getUnlockDetails());
  }

  /**
   * Runs method for all power-ups that will end the timer on them if this is required.
   *
   * @param delta Time since last render.
   * @return The delta that should be used based upon whether time freeze is active.
   */
  public Float updateValues(Float delta) {
    savePowerupStatus();
    SHORTERMACHINETIME.endPowerUp(machines);
    AUTOCOOK.applyPowerUp(machines);
    CHEAPERMACHINEUNLOCK.endPowerUp(money.getUnlockDetails());
    DOUBLEMONEY.endTime();
    return TIMEFREEZE.getDelta(delta);
  }

  /**
   * Print method to display status of power-ups.
   *
   * @return Combination of pretty print strings along with a header.
   */
  public String displayText() {
    return "Current Active Powerups: \n"
        + CHEAPERMACHINEUNLOCK.prettyPrint()
        + DOUBLEMONEY.prettyPrint()
        + SHORTERMACHINETIME.prettyPrint()
        + AUTOCOOK.prettyPrint()
        + TIMEFREEZE.prettyPrint();
  }

  /** Saves all power-ups within an object. */
  public void savePowerupStatus() {
    JSONObject powerupStatus = new JSONObject();
    powerupStatus.put("Cheaper Machine", CHEAPERMACHINEUNLOCK.savePowerUp());
    powerupStatus.put("Double Money", DOUBLEMONEY.savePowerUp());
    powerupStatus.put("Shorter Machine", SHORTERMACHINETIME.savePowerUp());
    powerupStatus.put("Skip Machine", AUTOCOOK.savePowerUp());
    powerupStatus.put("Time Freeze", TIMEFREEZE.savePowerUp());
    this.saver.setPowerups(powerupStatus);
  }

  /**
   * Loads in details of previous games power-ups.
   *
   * @param details Contains all the power-ups within the previous game and whether they have been
   *     active and how long for if this is the case.
   */
  public void reloadPowerupStatus(JSONObject details) {
    CHEAPERMACHINEUNLOCK.loadPowerup((JSONObject) details.get("Cheaper Machine"));
    DOUBLEMONEY.loadPowerup((JSONObject) details.get("Double Money"));
    SHORTERMACHINETIME.loadPowerup((JSONObject) details.get("Shorter Machine"));
    AUTOCOOK.loadPowerup((JSONObject) details.get("Skip Machine"));
    TIMEFREEZE.loadPowerup((JSONObject) details.get("Time Freeze"));
  }
}

package com.neves6.piazzapanic.gamemechanisms;

import com.neves6.piazzapanic.people.Chef;

/** Machine class. Represents a machine or station in the game. */
public class Machine {
  private final String type;
  private final String input;
  private final String output;
  private float processingTime;
  private final Boolean sticky;
  private Boolean active;
  private float runtime;
  private Chef operator;
  private final String unlockID;
  private boolean actionComplete = false;

  /**
   * Machine constructor.
   *
   * @param type Type of machine.
   * @param input Input ingredient.
   * @param output Output ingredient.
   * @param processingTime Processing time.
   * @param sticky Whether or not the machine locks the chef in place during use.
   */
  public Machine(
      String type,
      String input,
      String output,
      float processingTime,
      Boolean sticky,
      String unlockID) {
    this.type = type;
    this.input = input;
    this.output = output;
    this.processingTime = processingTime;
    this.sticky = sticky;
    this.active = false;
    this.unlockID = unlockID;
  }

  /**
   * Machine constructor.
   *
   * @param type Type of machine.
   * @param input Input ingredient.
   * @param output Output ingredient.
   * @param processingTime Processing time.
   * @param sticky Whether the machine locks the chef in place during use.
   */
  public Machine(String type, String input, String output, float processingTime, Boolean sticky) {
    this.type = type;
    this.input = input;
    this.output = output;
    this.processingTime = processingTime;
    this.sticky = sticky;
    this.active = false;
    this.unlockID = "auto";
  }

  /**
   * Begins the machine processing of the ingredient.
   *
   * @param chef Which chef is using the machine.
   */
  public void process(Chef chef, Money currency) {
    if (!(currency.isUnlocked(this.unlockID))) {
      return;
    }
    if (input.equals("") && processingTime == 0) {
      chef.addToInventory(output);
    } else if (chef.getInventory().peek().equals(input)) {
      active = true;
      actionComplete = false;
      chef.getInventory().pop();
      chef.setIsStickied(sticky);
      chef.setMachineInteractingWith(this);
      operator = chef;
    }
  }

  public void processStaffInteraction(Chef chef, Money currency) {
    if (!(currency.isUnlocked(this.unlockID))) {
      return;
    }
    chef.addToInventory(output);
  }

  /**
   * Checks if the machine is done processing and adds the output to the chef's inventory if it is.
   * Handles unsticking the chef.
   */
  public void attemptGetOutput() {
    Chef chef = operator;
    if (active && actionComplete && runtime >= processingTime) {
      chef.addToInventory(output);
      chef.setIsStickied(false);
      chef.setMachineInteractingWith(null);
      active = false;
      actionComplete = false;
      runtime = 0;
    }else if (!actionComplete && runtime > (processingTime * 2/3F)) {
      chef.addToInventory("ruined " + output);
      chef.setIsStickied(false);
      chef.setMachineInteractingWith(null);
      active = false;
      actionComplete = false;
      runtime = 0;
    }
  }

  /**
   * Checks if process is within valid time window, if so the action is marked as complete.
   */
  public void attemptCompleteAction() {
    if (actionComplete) {
      return;
    }
    if (active && runtime >= (processingTime * 1/3F) && runtime <= (processingTime * 2/3F)) {
      actionComplete = true;
    }
  }

  /**
   * Getter method for the input variable.
   *
   * @return Input required to use the machine.
   */
  public String getInput() {
    return input;
  }

  /**
   * Increase the variable which indicates how long the machine has been used for.
   *
   * @param delta Time since last render.
   */
  public void incrementRuntime(float delta) {
    this.runtime += delta;
  }

  /**
   * Getter method for the runtime variable.
   *
   * @return Time since the machine started running.
   */
  public float getRuntime() {
    return runtime;
  }

  /**
   * Getter method for the active variable.
   *
   * @return Boolean variable indicating whether the machine is being used for not.
   */
  public boolean getActive() {
    return active;
  }

  /**
   * Getter method for processingTime variable.
   *
   * @return Float variable which indicates how long the machine takes to process.
   */
  public float getProcessingTime() {
    return processingTime;
  }

  // Use this for auto cook and shorter cook.

  /**
   * Setter method for processingTime variable.
   *
   * @param newTime Float variable which indicates how long the machine should now take.
   */
  public void changeProcessingTime(float newTime) {
    this.processingTime = newTime;
  }
}

package com.neves6.piazzapanic.gamemechanisms;

/** Class to handle reputation points in game. */
public class ReputationPoints {
  int points;

  /**
   * Constructor method.
   *
   * @param value Amount of points that the user starts with.
   */
  public ReputationPoints(int value) {
    this.points = value;
  }

  /**
   * Decreases point if it is not already negative.
   *
   * @return Whether the decrease operator has been applied.
   */
  public Boolean decrement() {
    if (points <= 0) {
      return false;
    } else {
      points--;
      return true;
    }
  }

  /**
   * Getter method.
   *
   * @return the amount of points that the user currently has.
   */
  public int getPoints() {
    return points;
  }

  /**
   * Setter method - wipes all previous operations.
   *
   * @param value the amount of points that the user should have.
   */
  public void overwritePoints(int value) {
    points = value;
  }
}

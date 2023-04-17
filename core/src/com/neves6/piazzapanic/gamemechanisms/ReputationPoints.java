package com.neves6.piazzapanic.gamemechanisms;

public class ReputationPoints {
  int points;

  public ReputationPoints(int value) {
    this.points = value;
  }

  public Boolean decrement() {
    if (points <= 0) {
      return false;
    } else {
      points--;
      return true;
    }
  }

  public int getPoints() {
    return points;
  }

  public void overwritePoints(int value) {
    points = value;
  }
}

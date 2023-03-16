package com.neves6.piazzapanic;



/** Base person class. */
public class Person {
  private final String name;
  private int xCoord;
  private int yCoord;
  // For powerups.
  private int deltaMultiplier;

  /**
   * Person constructor.
   *
   * @param name Name of person.
   * @param xCoord logical x coordinate of person.
   * @param yCoord logical y coordinate of person.
   */
  public Person(String name, int xCoord, int yCoord) {
    this.name = name;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.deltaMultiplier = 1;
  }

  public int getxCoord() {
    return xCoord;
  }

  public int getyCoord() {
    return yCoord;
  }

  public String getName() {
    return name;
  }

  public void setxCoord(int xCoord) {
    if (xCoord > -1) {
      this.xCoord = xCoord;
    } else {
      throw new IllegalArgumentException();
    }
  }

  public void setyCoord(int yCoord) {
    if (yCoord > -1) {
      this.yCoord = yCoord;
    } else {
      throw new IllegalArgumentException();
    }
  }

  public void alterxCoord(int xDelta) {
    this.xCoord += xDelta * deltaMultiplier;
  }

  public void alteryCoord(int yDelta) {
    this.yCoord += yDelta * deltaMultiplier;
  }
  // TODO: Power-ups.
  public void alterSpeed(int newMultipler) {
    this.deltaMultiplier = newMultipler;
  }

  public int getSpeed() {
    return this.deltaMultiplier;
  }
}

package com.neves6.piazzapanic.people;

/** Base person class. */
public class Person {
  private final String name;
  private int xcoord;
  private int ycoord;
  // For powerups.
  private int deltaMultiplier;

  /**
   * Person constructor.
   *
   * @param name Name of person.
   * @param xcoord logical x coordinate of person.
   * @param ycoord logical y coordinate of person.
   */
  public Person(String name, int xcoord, int ycoord) {
    this.name = name;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.deltaMultiplier = 1;
  }

  /**
   * Getter method for xcoord variable.
   *
   * @return The integer representing the x coordinate of the person who is being displayed.
   */
  public int getxCoord() {
    return xcoord;
  }

  /**
   * Getter method for ycoord variable.
   *
   * @return The integer representing the y coordinate of the person who is being displayed.
   */
  public int getyCoord() {
    return ycoord;
  }

  /**
   * Getter method for name variable.
   *
   * @return The string representing a identifier for the person.
   */
  public String getName() {
    return name;
  }

  /**
   * Setter method for the xcoord variable.
   *
   * @param xcoord The new positive integer representing the coordinates of the person.
   */
  public void setxCoord(int xcoord) {
    if (xcoord > -1) {
      this.xcoord = xcoord;
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Setter method for the ycoord variable.
   *
   * @param ycoord The new positive integer representing the coordinates of the person.
   */
  public void setyCoord(int ycoord) {
    if (ycoord > -1) {
      this.ycoord = ycoord;
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Alter the xcoordinate based upon the delta pace.
   *
   * @param xdelta How many places across the person needs to move.
   */
  public void alterxCoord(int xdelta) {
    this.xcoord += xdelta * deltaMultiplier;
  }

  /**
   * Alter the ycoordinate based upon the delta pace.
   *
   * @param ydelta How many places up the person needs to move.
   */
  public void alteryCoord(int ydelta) {
    this.ycoord += ydelta * deltaMultiplier;
  }

  // TODO: Power-ups.

  /**
   * Setter method for delta multiplier.
   *
   * @param newMultipler An integer that represents the speed of the person when moving.
   */
  public void alterSpeed(int newMultipler) {
    this.deltaMultiplier = newMultipler;
  }

  /**
   * Getter method for delta multiplier.
   *
   * @return An integer that represents the speed of the person when moving.
   */
  public int getSpeed() {
    return this.deltaMultiplier;
  }
}

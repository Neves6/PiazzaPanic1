package com.neves6.piazzapanic;



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

  public int getxCoord() {
    return xcoord;
  }

  public int getyCoord() {
    return ycoord;
  }

  public String getName() {
    return name;
  }

  public void setxCoord(int xcoord) {
    if (xcoord > -1) {
      this.xcoord = xcoord;
    } else {
      throw new IllegalArgumentException();
    }
  }

  public void setyCoord(int ycoord) {
    if (ycoord > -1) {
      this.ycoord = ycoord;
    } else {
      throw new IllegalArgumentException();
    }
  }

  public void alterxCoord(int xdelta) {
    this.xcoord += xdelta * deltaMultiplier;
  }

  public void alteryCoord(int ydelta) {
    this.ycoord += ydelta * deltaMultiplier;
  }

  // TODO: Power-ups.
  public void alterSpeed(int newMultipler) {
    this.deltaMultiplier = newMultipler;
  }

  public int getSpeed() {
    return this.deltaMultiplier;
  }
}

package com.neves6.piazzapanic.people;

import com.badlogic.gdx.graphics.Texture;

/** Automated customers which extends the original person class in order to use movement. */
public class Customer extends Person {

  private String order;
  private final Texture txUp;
  private final Texture txLeft;
  private float timeArrived;

  /**
   * Customer constructor.
   *
   * @param name Name of customer.
   * @param xcoord logical x coordinate of customer.
   * @param ycoord logical y coordinate of customer.
   * @param order Order of customer.
   */
  public Customer(String name, int xcoord, int ycoord, String order, float timeArrived) {
    super(name, xcoord, ycoord);
    this.order = order;
    this.txUp = new Texture("people/cust1up.png");
    this.txLeft = new Texture("people/cust1left.png");
    this.timeArrived = timeArrived;
  }

  /**
   * Getter method for the order that the customer has.
   *
   * @return String that represents the customers order.
   */
  public String getOrder() {
    return order;
  }

  /**
   * Getter method for the txUp variable.
   *
   * @return Texture representing the customer facing upwards.
   */
  public Texture getTxUp() {
    return txUp;
  }

  /**
   * Getter method for the txDown variable.
   *
   * @return Texture representing the customer facing left.
   */
  public Texture getTxLeft() {
    return txLeft;
  }

  /**
   * Getter method for time at which the customer was generated.
   *
   * @return Time customer arrived.
   */
  public float getTimeArrived() {
    return timeArrived;
  }

  /**
   * Setter method for customer order.
   *
   * @param order Name of recipe to set order to.
   */
  public void setRecipe(String order) {
    this.order = order;
  } // UPDATE: added to set a recipe from the customer

  /**
   * Setter method for time at which the customer was generated.
   *
   * @param time Time customer arrived.
   */
  public void setTimeArrived(float time) {
    this.timeArrived = time;
  }
}
// UPDATE: time arrived of the customer

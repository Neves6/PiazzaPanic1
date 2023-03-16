package com.neves6.piazzapanic;

import com.badlogic.gdx.graphics.Texture;

/** Customer subclass. */
public class Customer extends Person {

  private String order;
  private final Texture txUp;
  private final Texture txLeft;

  /**
   * Customer constructor.
   *
   * @param name Name of customer.
   * @param xcoord logical x coordinate of customer.
   * @param ycoord logical y coordinate of customer.
   * @param order Order of customer.
   */
  public Customer(String name, int xcoord, int ycoord, String order) {
    super(name, xcoord, ycoord);
    this.order = order;
    this.txUp = new Texture("people/cust1up.png");
    this.txLeft = new Texture("people/cust1left.png");
  }

  public String getOrder() {
    return order;
  }

  public Texture getTxUp() {
    return txUp;
  }

  public Texture getTxLeft() {
    return txLeft;
  }
}

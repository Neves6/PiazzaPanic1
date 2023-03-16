package com.neves6.piazzapanic;

import com.badlogic.gdx.graphics.Texture;
import java.util.Stack;

/** Customer subclass. */
public class Customer extends Person {

  private String order;
  private final Texture txUp;
  private final Texture txLeft;
  private Stack<String> remainingSteps;

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
    generateHelper();
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

  public void generateHelper() {
    remainingSteps = new Stack<>();
    if (order == "salad") {
      this.remainingSteps.push("chopped onion");
      this.remainingSteps.push("chopped tomato");
      this.remainingSteps.push("chopped lettuce");
    } else if (order == "jacket potato") {
      this.remainingSteps.push("beans");
      this.remainingSteps.push("jacket");
    } else if (order == "pizza") {
      this.remainingSteps.push("cheese");
      this.remainingSteps.push("chopped tomato");
      this.remainingSteps.push("dough");
    } else if (order == "hamburger") {
      this.remainingSteps.push("burger");
      this.remainingSteps.push("toasted bun");
    }
  }

  public Boolean helper(String inv) {
    if (inv == this.remainingSteps.peek()) {
      this.remainingSteps.pop();
      return true;
    } else {
      return false;
    }
  }

  public Boolean finishedRecipe() {
    return remainingSteps.size() == 0;
  }
}

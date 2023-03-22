package com.neves6.piazzapanic.people;

import com.badlogic.gdx.graphics.Texture;
import java.util.Stack;

/** Automated customers which extends the original person class in order to use movement. */
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
   * Generates a stack relating to the customers order
   * indicating which order the items need to be placed on top
   * of the tray.
   */
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

  /**
   * Method to get the next item that need to be put onto
   * the tray for the customers order.
   * @param inv The top of the chefs stack.
   * @return Boolean value indicating whether the chef and the
   * customer stack matches.
   */
  public Boolean helper(String inv) {
    if (inv == this.remainingSteps.peek()) {
      this.remainingSteps.pop();
      return true;
    } else {
      return false;
    }
  }

  /**
   * Calculates the size of the array and compares to 0.
   * @return Boolean value indicating whether all items are
   * on the tray for the customers order.
   */
  public Boolean finishedRecipe() {
    return remainingSteps.size() == 0;
  }
}

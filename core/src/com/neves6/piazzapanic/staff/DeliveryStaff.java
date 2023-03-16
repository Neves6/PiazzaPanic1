package com.neves6.piazzapanic.staff;

import java.util.ArrayList;
import java.util.Stack;

/**
 * This class creates a staff member which automatically takes orders out once they have been
 * assembled on the tray by any of the staff.
 */
public class DeliveryStaff extends BaseStaff {
  private Stack<String> items;
  /**
   * Constructor method that takes 2 arrays which must be of the same length. Each index represent
   * the nth value in the sequence of movement which must take place.
   *
   * @param xSequence List of x coordinates in order.
   * @param ySequence List of y coordinates in order.
   */
  public DeliveryStaff(ArrayList<Integer> xSequence, ArrayList<Integer> ySequence) {
    super(xSequence, ySequence);
    this.items = new Stack<>();
  }

  /***
   * Collects a complete order.
   * @param item An assembled recipe.
   */
  public void collectItem(String item) {
    items.push(item);
    this.collect = true;
  }

  /***
   * Gets the recipe that the staff member currently has.
   * @return Stack containing the complete recipe.
   */
  public Stack<String> getItems() {
    Stack<String> copy = (Stack<String>) items.clone();
    items.pop();
    return copy;
  }
}

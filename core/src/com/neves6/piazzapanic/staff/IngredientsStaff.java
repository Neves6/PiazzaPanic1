package com.neves6.piazzapanic.staff;

import java.util.ArrayList;
import java.util.Stack;

/** A class to configure unlockable staff which collects item from fridges for the user. */
public class IngredientsStaff extends BaseStaff {
  Stack<String> stack;
  String currentRecipe;
  boolean generate;

  /**
   * Constructor method that takes 2 arrays which must be of the same length. Each index represent
   * the nth value in the sequence of movement which must take place. Generate tells the class that
   * all the ingredients needs adding to the stack.
   *
   * @param xsequence List of x coordinates in order.
   * @param ysequence List of y coordinates in order.
   */
  public IngredientsStaff(ArrayList<Integer> xsequence, ArrayList<Integer> ysequence) {
    super(xsequence, ysequence);
    this.stack = new Stack<>();
    this.generate = false;
  }

  /**
   * Depending on what recipe is needed, the stack is generated and designed to put the ingredients
   * you need last first. This allows the LIFO structure to optimise the way it gives ingredients to
   * the user for maximum time saving.
   */
  public void generateStack() {
    if (currentRecipe == "salad") {
      this.stack.push("onion");
      this.stack.push("tomato");
      this.stack.push("lettuce");
    } else if (currentRecipe == "jacket potato") {
      this.stack.push("beans");
      this.stack.push("potato");
    } else if (currentRecipe == "pizza") {
      this.stack.push("cheese");
      this.stack.push("tomato");
      this.stack.push("dough");
    } else if (currentRecipe == "hamburger") {
      this.stack.push("meat");
      this.stack.push("bun");
    } else {
      throw new IllegalArgumentException("Must be a valid recipe.");
    }
  }

  /**
   * Sets current recipe if the flag is set.
   *
   * @param recipe The recipe that is currently being processed.
   */
  public void setCurrentRecipe(String recipe) {
    if (this.generate) {
      this.currentRecipe = recipe;
      this.stack.clear();
      generateStack();
      this.collect = true;
      this.generate = false;
    }
  }

  /**
   * Collect ingredients that have been collected by the staff member.
   *
   * @return The ingredients at the top of the stack.
   */
  public String collectItem() {
    if (this.stack.size() > 0) {
      return this.stack.pop();
    } else {
      return null;
    }
  }

  /**
   * Getter method.
   *
   * @param generate Whether a new recipes ingredients needs to be collected yet.
   */
  public void setGenerate(boolean generate) {
    this.generate = generate;
  }
}

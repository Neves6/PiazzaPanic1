package com.neves6.piazzapanic.people;

import com.badlogic.gdx.graphics.Texture;import com.neves6.piazzapanic.gamemechanisms.Machine;
import java.util.Stack;

/**
 * Chef class which helps the user cook items and it
 * inherits methods from the person class.
 */
public class Chef extends Person {
  private boolean isStickied;
  private Stack<String> inventory;
  private String facing;
  private final Texture txUp;
  private final Texture txDown;
  private final Texture txLeft;
  private final Texture txRight;
  private Texture txNow;
  private boolean isInteracting;
  private Machine machineInteractingWith;

  /**
   * Chef constructor.
   *
   * @param name A label for chefs
   * @param xcoord X coordinate of the chef.
   * @param ycoord Y coordinate of the chef.
   * @param chopSpeed Amount of time taken for the chef to chop an item.
   * @param frySpeed Amount of time taken for the chef to fry an item.
   * @param bakeSpeed Amount of time taken for the chef to bake an item.
   * @param isStickied Whether the chef can move or not.
   * @param inventory What items are in the chefs stack.
   * @param textureSet What set of images are used to display the chef.
   */
  public Chef(
      String name,
      int xcoord,
      int ycoord,
      int chopSpeed,
      int frySpeed,
      int bakeSpeed,
      boolean isStickied,
      Stack<String> inventory,
      int textureSet) {
    super(name, xcoord, ycoord);
    this.isStickied = isStickied;
    if (inventory.size() == 0) {
      this.inventory = new Stack<String>();
    } else {
      this.inventory = inventory;
    }
    this.facing = "down";
    this.txUp = new Texture("people/chef" + textureSet + "up.png");
    this.txDown = new Texture("people/chef" + textureSet + "down.png");
    this.txLeft = new Texture("people/chef" + textureSet + "left.png");
    this.txRight = new Texture("people/chef" + textureSet + "right.png");
    this.txNow = txDown;
  }

  /**
   * Getter method used to see whether chef can move or not.
   * @return Can the chef move.
   */
  public boolean getIsStickied() {
    return isStickied;
  }

  /**
   * Getter method used to set which texture is being used
   * to render the chef.
   * @return Texture being used to render the chef.
   */
  public Texture getTxNow() {
    return txNow;
  }

  /**
   * Setter method to set whether the chef is stuck to
   * one coordinates.
   * @param flag Whether the chef is stuck to one coordinates.
   */
  public void setIsStickied(boolean flag) {
    this.isStickied = flag;
  }

  /**
   * Setter method to show what the chef is currently using.
   * @param machine The machine that the chef is interacting with.
   */
  public void setMachineInteractingWith(Machine machine) {
    this.machineInteractingWith = machine;
  }

  /**
   * Getter method to see what machine the chef is using.
   * @return The machine that the chef is interacting with.
   */
  public Machine getMachineInteractingWith() {
    return machineInteractingWith;
  }


  /***
   * Getter method to see what items the chef has.
   * @return The items that the chef has in its possession.
   */
  public Stack<String> getInventory() {
    return inventory;
  }

  /**
   * Add a new item to the top of the chef inventory.
   * @param item A ingredient to be added to the inventory.
   */
  public void addToInventory(String item) {
    this.inventory.push(item);
  }

  /**
   * Removes the item at the top of the chefs inventory.
   */
  public void removeTopFromInventory() {
    this.inventory.pop();
  }

  /**
   * Getter method for which way the chef is facing.
   * @return Which way the chef is facing.
   */
  public String getFacing() {
    return facing;
  }

  /**
   * Sets the facing direction of the chef, then changes the current texture to match.
   * @param facing sprite facing direction.
   */
  public void setFacing(String facing) {
    this.facing = facing;
    switch (facing) {
      case "up":
        this.txNow = txUp;
        break;
      case "down":
        this.txNow = txDown;
        break;
      case "left":
        this.txNow = txLeft;
        break;
      case "right":
        this.txNow = txRight;
        break;
      default:
        break;
    }
  }
}

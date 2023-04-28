package com.neves6.piazzapanic.gamemaster;

import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.people.Chef;
import com.neves6.piazzapanic.people.Customer;
import java.util.ArrayList;
import java.util.Queue;

/** Class to handle all text displayed to the user. */
public class TextMaster {
  public TextMaster() {}

  /**
   * Generates the display text for the chefs' inventories.
   *
   * @return String containing the display text.
   */
  public String generateHoldingsText(ArrayList<Chef> chefs) {
    String comp = "";
    for (int i = 0; i < chefs.size(); i++) {
      comp += "Chef " + (i + 1) + " is holding:\n";
      comp += chefs.get(i).getInventory().toString() + "\n";
    }
    return comp;
  }

  /**
   * Generates display text for customer having left.
   *
   * @param lastRepPointLost The time when the last reputation point was lost.
   * @param totalTimer The time that has elapsed since the game started.
   * @return String containing the display text.
   */
  public String generateCustomerLeftText(float totalTimer, float lastRepPointLost) {
    String comp = "";
    if (totalTimer <= (lastRepPointLost + 3) && lastRepPointLost != 0) {
      comp += "A customer was tired of waiting";
      comp += "\nReputation point lost";
    }
    return comp;
  }

  /**
   * Generates the display text for reputation point count.
   *
   * @param reputationPoints The number of points that the player has left.
   * @param lastRepPointLost The time when the last reputation point was lost.
   * @param totalTimer The time that has elapsed since the game started.
   * @return String containing the display text.
   */
  public String generateReputationPointText(
      float totalTimer, float lastRepPointLost, int reputationPoints) {
    String comp = "";
    comp += "Reputation points: ";
    comp += reputationPoints;
    if (totalTimer <= (lastRepPointLost + 3) && lastRepPointLost != 0) {
      comp += " -1";
    }
    return comp;
  }

  /**
   * Generates the display text for the timer.
   *
   * @param totalTimerDisplay The time that has elapsed since the game started.
   * @return String containing the display text.
   */
  public String generateTimerText(float totalTimerDisplay) {
    String comp = "";
    comp += "Time elapsed: ";
    comp += (int) totalTimerDisplay;
    comp += " s";
    return comp;
  }

  /**
   * Generates the display text for the customers' tray and order.
   *
   * @param customers The queue containing customer who are yet to be served.
   * @param tray1 The contents of the items in the first tray.
   * @param tray2 The contents of the items in the second tray.
   * @return String containing the display text.
   */
  public String generateCustomersTrayText(
      Queue<Customer> customers, ArrayList<String> tray1, ArrayList<String> tray2) {
    String comp = "";
    comp += "Customers remaining: ";
    comp += customers.size();
    if (customers.size() > 0) {
      comp += "\nOrder: ";
      comp += customers.peek().getOrder();
    }
    if (!tray1.isEmpty()) {
      comp += "\nTray 1 contents: ";
      comp += tray1.toString();
    }
    if (!tray2.isEmpty()) {
      comp += "\nTray 2 contents: ";
      comp += tray2.toString();
    }
    return comp;
  }

  /**
   * Generates the display text for the chef's timer.
   *
   * @param chefs chefs that are controllable by the user.
   * @param chefno chef number to check.
   * @return String containing the display text.
   */
  public String getMachineTimerForChef(int chefno, ArrayList<Chef> chefs) {
    Chef chef = chefs.get(chefno);
    if (chef.getMachineInteractingWith() != null) {
      Machine machine = chef.getMachineInteractingWith();
      return ((int) (machine.getProcessingTime() - machine.getRuntime() + 1)) + "";
    } else {
      return "";
    }
  }
}

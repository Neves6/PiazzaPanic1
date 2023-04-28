package com.neves6.piazzapanic.gamemechanisms;

import com.neves6.piazzapanic.people.Chef;
import com.neves6.piazzapanic.people.Customer;
import com.neves6.piazzapanic.staff.DeliveryStaff;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/** The class which handles all operation upon the tray used to assembly items by the chef. */
public class Tray {
  // LIST ALL RECIPES AND THEIR INGREDIENTS HERE.
  static final ArrayList<String> SALAD =
      new ArrayList<>(Arrays.asList("chopped tomato", "chopped onion", "chopped lettuce"));
  static final ArrayList<String> JACKETPOTATO = new ArrayList<>(Arrays.asList("jacket", "beans"));
  static final ArrayList<String> HAMBURGER =
      new ArrayList<>(Arrays.asList("burger", "toasted bun"));
  static final ArrayList<String> RAWPIZZA =
      new ArrayList<>(Arrays.asList("chopped tomato", "dough", "cheese"));
  Map<String, ArrayList<String>> recipes = new HashMap<>();

  ArrayList<String> content;

  /**
   * Constructor method which adds all recipes to a list. Create an list to store content of trays.
   */
  public Tray() {
    content = new ArrayList<>();
    recipes.put("salad", SALAD);
    recipes.put("jacket potato", JACKETPOTATO);
    recipes.put("hamburger", HAMBURGER);
    recipes.put("raw pizza", RAWPIZZA);
  }

  /**
   * Getter method to get items on tray.
   *
   * @return Items on the tray.
   */
  public ArrayList<String> getList() {
    return content;
  }

  /**
   * Adds the top item from the currently selected chef's inventory to the tray.
   *
   * @param customers The queue of customers who are waiting to be served.
   * @param deliveryStaff The member of staff which can take items to the counter.
   * @param machineUnlockBalance The money that the player has earned so far.
   */
  public void addToTray(
      Chef chef,
      DeliveryStaff deliveryStaff,
      Queue<Customer> customers,
      Money machineUnlockBalance) {
    if (chef.getInventory().isEmpty()
        || recipes.keySet().contains(chef.getInventory().peek())
        || chef.getInventory().peek().contains("ruined")) {
      return;
    }

    content.add(chef.getInventory().pop());

    // Pizza cannot be handled by staff because you need to put it in the oven then
    // take it to the customer.
    for (String recipe : recipes.keySet()) {
      if (content.containsAll(recipes.get(recipe))) {
        if (content.size() != recipes.get(recipe).size()) {
          chef.addToInventory("ruined " + recipe);
        } else {
          chef.addToInventory(recipe);
        }
        content.clear();
      }
    }

    if (machineUnlockBalance.isUnlocked("server-staff")
        && customers.size() > 0
        && !(chef.getInventory().isEmpty())
        && customers.peek().getOrder().equals(chef.getInventory().peek())) {
      deliveryStaff.collectItem(customers.peek().getOrder());
      chef.getInventory().pop();
    }
  }

  public void putOnTray(String item) {
    content.add(item);
  }
}

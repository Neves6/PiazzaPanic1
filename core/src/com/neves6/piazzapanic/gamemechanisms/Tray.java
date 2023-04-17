package com.neves6.piazzapanic.gamemechanisms;

import com.neves6.piazzapanic.people.Chef;
import com.neves6.piazzapanic.people.Customer;
import com.neves6.piazzapanic.staff.DeliveryStaff;

import java.util.*;

public class Tray {
    final static ArrayList<String> SALAD =
            new ArrayList<>(Arrays.asList("chopped tomato", "chopped onion", "chopped lettuce"));
    final static ArrayList<String> JACKETPOTATO = new ArrayList<>(Arrays.asList("jacket", "beans"));
    final static ArrayList<String> HAMBURGER = new ArrayList<>(Arrays.asList("burger", "toasted bun"));
    final static ArrayList<String> RAWPIZZA = new ArrayList<>(Arrays.asList("chopped tomato", "dough", "cheese"));
    Map<String, ArrayList<String>> recipes = new HashMap<>();

    ArrayList<String> content;
    public Tray(){
        content = new ArrayList<>();
        recipes.put("salad", SALAD);
        recipes.put("jacket potato", JACKETPOTATO);
        recipes.put("hamburger", HAMBURGER);
        recipes.put("raw pizza", RAWPIZZA);
    }

    public ArrayList<String> getList() {
        return content;
    }

    /**
     * Adds the top item from the currently selected chef's inventory to the tray.
     *
     * @param station Indicates which tray station is being used.
     * @return
     */
    public void addToTray(Chef chef, DeliveryStaff deliveryStaff, Queue<Customer> customers, Money machineUnlockBalance) {
        Stack<String> inv = chef.getInventory();
        if (inv.isEmpty()
                || recipes.keySet().contains(inv.peek())
                || inv.peek().equals("raw pizza")
                || inv.peek().contains("ruined")) {
            return;
        }

        content.add(inv.pop());

        // Pizza cannot be handled by staff because you need to put it in the oven then
        // take it to the customer.
        for (String recipe : recipes.keySet()){
            if (content.containsAll(recipes.get(recipe))){
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
                && !inv.isEmpty()
                && customers.peek().getOrder().equals(inv.peek())) {
            deliveryStaff.collectItem(customers.peek().getOrder());
            inv.pop();
        }

    }

    public void putOnTray(String item) {
        content.add(item);
    }
}

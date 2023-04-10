package com.neves6.piazzapanic.gamemechanisms;

import com.neves6.piazzapanic.people.Chef;
import com.neves6.piazzapanic.people.Customer;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/** Class used to save game data from a current game runtime to a json file. */
public class GameSaver {
  JSONObject gameDetails;
  String fileLoc;

  /**
   * Constructor method that creates a new json object.
   *
   * @param fileLoc The path to the file where the game data will be saved.
   */
  public GameSaver(String fileLoc) {
    gameDetails = new JSONObject();
    this.fileLoc = fileLoc;
  }

  /**
   * Set difficulty of the game.
   *
   * @param value Value between 1-3 representing ascending difficulty.
   * @return Whether the value was accepted or not.
   */
  public Boolean setDifficulty(int value) {
    if (value <= 0 || value >= 4) {
      return false;
    } else {
      gameDetails.put("Difficulty", value);
      return true;
    }
  }

  /**
   * Set whether power-ups are active or not.
   *
   * @param value Boolean value representing whether power-ups were chosen or not.
   * @return Whether the value was accepted or not.
   */
  public Boolean setPowerUp(Boolean value) {
    gameDetails.put("Power-ups", value);
    return true;
  }

  /**
   * The setter method for saving customers which only accept a positive number or -1 for endless
   * mode.
   *
   * @param value The amount of customers remaining to serve.
   * @return Whether the value was accepted or not.
   */
  public Boolean setCustomersRemaining(int value) {
    if (value <= 0 && !(value == -1)) {
      return false;
    } else {
      gameDetails.put("Customers remaining", value);
      return true;
    }
  }

  /**
   * Closes the class and saved to the location indicated in the constructor method.
   *
   * @throws IOException If the given path doesn't exist.
   */
  public void closeClass() throws IOException {
    FileWriter file = new FileWriter(fileLoc);
    file.write(gameDetails.toJSONString());
    file.flush();
    file.close();
  }

  /**
   * Decrement customer field by 1 if the key is already set and the mode is not endless.
   *
   * @return Whether the value was accepted or not.
   */
  public Boolean decrementCustomers() {
    if (!(gameDetails.containsKey("Customers remaining"))) {
      return false;
    }
    int customersCurrent = (int) gameDetails.get("Customers remaining");
    if (customersCurrent != -1) {
      gameDetails.put("Customers remaining", customersCurrent - 1);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Puts all the currency details in.
   *
   * @param moneyDetails A JSONObject which should be generated using a method within the money
   *     class.
   */
  public void setCurrencyDetails(JSONObject moneyDetails) {
    gameDetails.put("Currency System", moneyDetails);
  }

  /**
   * Puts all the chefs details within one area of the json.
   *
   * @param chefs Lists of all chefs which are currently running the game.
   * @param selectedChef Chef being controlled by the user.
   */
  public void setChefDetails(ArrayList<Chef> chefs, int selectedChef) {
    JSONArray chefCoords = new JSONArray();
    JSONArray chefStacks = new JSONArray();
    for (Chef chef : chefs) {
      chefCoords.add(Arrays.asList(chef.getxCoord(), chef.getyCoord()));
      chefStacks.add(chef.getInventory());
    }
    JSONObject chefDetails = new JSONObject();
    chefDetails.put("Selected Chef", selectedChef);
    chefDetails.put("Chef Stacks", chefStacks);
    chefDetails.put("Chef Location", chefCoords);
    chefDetails.put("Number of Chefs", chefs.size());
    gameDetails.put("Chefs", chefDetails);
  }

  /**
   * Set reputation points.
   *
   * @param value A positive integer.
   * @return Whether the value was accepted or not.
   */
  public Boolean setReputationPoints(int value) {
    if (value > 0) {
      gameDetails.put("Reputation Points", value);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Sets the recipe that is being made by the first customer.
   *
   * @param customer Customer at the front of the queue.
   * @return Whether the value was valid or not.
   */
  public Boolean setRecipe(Customer customer) {
    JSONObject customerDetails = new JSONObject();
    if (customer != null) {
      customerDetails.put("Order", customer.getOrder());
      customerDetails.put("Current Time", customer.getTimeArrived());
      gameDetails.put("Customer", customerDetails);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Sets the timer of the game so far.
   *
   * @param timeElapsed Float value to represent time elapsed that must be positive.
   * @return Whether the value was valid or not.
   */
  public Boolean setTime(Float timeElapsed) {
    if (timeElapsed > 0) {
      gameDetails.put("Time Elapsed", timeElapsed);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Sets the contents of the tray to the json.
   * @param t1 The tray being used in the first station.
   * @param t2 The tray being used in the second station.
   * @return Whether the values are valid or not.
   */
  public Boolean setTrays(ArrayList<String> t1, ArrayList<String> t2){
    JSONObject trays = new JSONObject();
    trays.put("Tray 1",t1);
    trays.put("Tray 2",t2);
    gameDetails.put("Trays", trays);
    return true;
  }

  /**
   * Saves all required details to recreate the power-up runner.
   * @param details Contains all the power-up runners details.
   * @return Whether the data is valid or not.
   */
  public Boolean setPowerups(JSONObject details){
    gameDetails.put("Powerup Runner", details);
    return true;
  }

}

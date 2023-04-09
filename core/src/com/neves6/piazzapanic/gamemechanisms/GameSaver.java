package com.neves6.piazzapanic.gamemechanisms;

import com.neves6.piazzapanic.people.Chef;
import com.neves6.piazzapanic.people.Customer;
import com.neves6.piazzapanic.screens.IntroScreen;
import com.neves6.piazzapanic.screens.PiazzaPanicGame;
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

  public GameSaver(String fileLoc) {
    gameDetails = new JSONObject();
    this.fileLoc = fileLoc;
  }

  public Boolean setDifficulty(int value) {
    if (value <= 0 || value >= 4) {
      return false;
    } else {
      gameDetails.put("Difficulty", value);
      return true;
    }
  }

  public Boolean setPowerUp(Boolean value) {
    gameDetails.put("Power-ups", value);
    return true;
  }

  public Boolean setCustomersRemaining(int value) {
    if (value <= 0) {
      return false;
    } else {
      gameDetails.put("Customers remaining", value);
      return true;
    }
  }

  public void closeClass(PiazzaPanicGame game) throws IOException {
    FileWriter file = new FileWriter(fileLoc);
    file.write(gameDetails.toJSONString());
    System.out.println(gameDetails.toJSONString());
    file.flush();
    file.close();
    game.setScreen(new IntroScreen(game));
  }

  public Boolean decrementCustomers() {
    if (!(gameDetails.containsKey("Customer remaining"))) {
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

  public void setCurrencyDetails(JSONObject moneyDetails) {
    gameDetails.put("Currency System", moneyDetails);
  }

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

  public Boolean setReputationPoints(int value) {
    if (value > 0) {
      gameDetails.put("Reputation Points", value);
      return true;
    } else {
      return false;
    }
  }

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

  public Boolean setTime(Float timeElapsed) {
    if (timeElapsed > 0) {
      gameDetails.put("Time Elapsed", timeElapsed);
      return true;
    } else {
      return false;
    }
  }
}

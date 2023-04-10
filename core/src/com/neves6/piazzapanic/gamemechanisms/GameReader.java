package com.neves6.piazzapanic.gamemechanisms;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.neves6.piazzapanic.gamemaster.ScenarioGameMaster;
import com.neves6.piazzapanic.screens.PiazzaPanicGame;
import com.neves6.piazzapanic.staff.DeliveryStaff;
import com.neves6.piazzapanic.staff.IngredientsStaff;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/** Class build to read game data. */
public class GameReader {
  JSONObject chefData;
  JSONArray chefLocations;
  JSONArray chefStacks;
  JSONObject machineData;
  JSONObject saveData;
  JSONObject currencySystem;
  JSONObject customerData;
  JSONObject trayInventory;

  /**
   * Constructor method.
   *
   * @param filename String containing the name/path of the json file containing game data.
   * @throws ParseException If the file entered does not follow the json format correctly.
   * @throws IOException If the file entered does not exist.
   */
  public GameReader(String filename) throws ParseException, IOException {
    JSONParser parser = new JSONParser();
    saveData = (JSONObject) parser.parse(new FileReader(filename));
    currencySystem = (JSONObject) saveData.get("Currency System");
    machineData = (JSONObject) currencySystem.get("Machines");
    customerData = (JSONObject) saveData.get("Customer");
    chefData = (JSONObject) saveData.get("Chefs");
    chefLocations = (JSONArray) chefData.get("Chef Location");
    chefStacks = (JSONArray) chefData.get("Chef Stacks");
    trayInventory = (JSONObject) saveData.get("Trays");
  }

  /**
   * Reads json file in order to create a loadable scenario game master class.
   *
   * @param game Instance of PiazzaPanicGame used to control the game.
   * @param map Tiled map being used in the game.
   * @param machineUnlockBalance Class used to control unlock-able machines.
   * @param ingredientsHelper Class used to control staff member that can get ingredients for the
   *     user.
   * @param deliveryStaff Class used to control staff member that can take the order to the
   *     customer.
   * @return A configured scenario game master which represents the state of the previously saved
   *     game.
   */
  public ScenarioGameMaster createGameMaster(
      PiazzaPanicGame game,
      TiledMap map,
      Money machineUnlockBalance,
      IngredientsStaff ingredientsHelper,
      DeliveryStaff deliveryStaff) {
    ScenarioGameMaster configuredMaster =
        new ScenarioGameMaster(
            game,
            map,
            ((Long) chefData.get("Number of Chefs")).intValue(),
            ((Long) saveData.get("Customers remaining")).intValue(),
            machineUnlockBalance,
            ingredientsHelper,
            deliveryStaff,
            ((Boolean) saveData.get("Power-ups")),
            ((Long) saveData.get("Difficulty")).intValue());

    // Customers are paced to release at random intervals so just wait till one exists.
    while (configuredMaster.getFirstCustomer() == null){
      configuredMaster.tickUpdate(1f);
    }

    if (customerData != null){
      configuredMaster.getFirstCustomer().setRecipe((String) customerData.get("Order"));
      configuredMaster.getFirstCustomer().setTimeArrived(((Double) customerData.get("Current Time")).floatValue());
    }


    // JSON auto converts all int to a long and all float to a double.
    configuredMaster.setSelectedChef(1 + ((Long) chefData.get("Selected Chef")).intValue());
    machineUnlockBalance.setBalance(((Double) currencySystem.get("Balance")).floatValue());
    machineUnlockBalance.loadPreviousValues(machineData);
    configuredMaster.setReputationPoints(((Long) saveData.get("Reputation Points")).intValue());
    configuredMaster.setTimeElapsed(((Double) saveData.get("Time Elapsed")).floatValue());

    // Go through all chefs and set coordinates then add everything
    // from the old stack.
    for (int i = 0; i < chefLocations.size(); i++) {
      JSONArray pairCoord = (JSONArray) chefLocations.get(i);
      configuredMaster.getChef(i+1).setxCoord(((Long) pairCoord.get(0)).intValue());
      configuredMaster.getChef(i+1).setyCoord(((Long) pairCoord.get(1)).intValue());
      JSONArray chefItems = (JSONArray) chefStacks.get(i);
      for (int x = 0; x < chefItems.size(); x++) {
        configuredMaster.getChef(i+1).addToInventory((String) chefItems.get(x));
      }
    }

    JSONArray trayOne = (JSONArray) trayInventory.get("Tray 1");
    for (int i = 0; i < trayOne.size();i++){
      configuredMaster.addtoTray(1, (String) trayOne.get(i));
    }

    JSONArray trayTwo = (JSONArray) trayInventory.get("Tray 2");
    for (int i = 0; i < trayTwo.size();i++){
      configuredMaster.addtoTray(1, (String) trayTwo.get(i));
    }

    configuredMaster.getPowerUpRunner().reloadPowerupStatus((JSONObject) saveData.get("Powerup Runner"));

    return configuredMaster;
  }
}

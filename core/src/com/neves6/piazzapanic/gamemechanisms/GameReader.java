package com.neves6.piazzapanic.gamemechanisms;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.neves6.piazzapanic.gamemaster.ScenarioGameMaster;
import com.neves6.piazzapanic.screens.PiazzaPanicGame;
import com.neves6.piazzapanic.staff.DeliveryStaff;
import com.neves6.piazzapanic.staff.IngredientsStaff;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class GameReader {
    JSONObject chefData;
    JSONArray chefLocations;
    JSONArray chefStacks;
    JSONObject machineData;
    JSONObject saveData;
    JSONObject currencySystem;

    public GameReader(String filename) throws ParseException, IOException {
        JSONParser parser = new JSONParser();
        saveData = (JSONObject) parser.parse(new FileReader(filename));
        currencySystem = (JSONObject) saveData.get("Currency System");
        machineData = (JSONObject) currencySystem.get("Machines");
        chefData = (JSONObject) saveData.get("Chefs");
        chefLocations = (JSONArray) chefData.get("Chef Location");
        chefStacks = (JSONArray) chefData.get("Chef Stacks");

    }

    public ScenarioGameMaster createGameMaster(PiazzaPanicGame game, TiledMap map, Money machineUnlockBalance, IngredientsStaff ingredientsHelper, DeliveryStaff deliveryStaff){
        ScenarioGameMaster configuredMaster = new ScenarioGameMaster(game,
                map,
                ((Long) chefData.get("Number of Chefs")).intValue(),
                ((Long) saveData.get("Customers remaining")).intValue(),
                machineUnlockBalance,
                ingredientsHelper,
                deliveryStaff,
                !((Boolean) saveData.get("Power-ups")),
                ((Long) saveData.get("Difficulty")).intValue()
                );
        configuredMaster.setSelectedChef(1 + ((Long) chefData.get("Selected Chef")).intValue());
        machineUnlockBalance.setBalance(((Double) currencySystem.get("Balance")).floatValue());
        machineUnlockBalance.loadPreviousValues(machineData);
        configuredMaster.setReputationPoints(((Long) saveData.get("Reputation Points")).intValue());

        for (int i=1; i<chefLocations.size(); i++){
            JSONArray pairCoord = (JSONArray) chefLocations.get(i);
            configuredMaster.getChef(i).setxCoord(((Long) pairCoord.get(0)).intValue());
            configuredMaster.getChef(i).setxCoord(((Long) pairCoord.get(1)).intValue());
        }
        return configuredMaster;
    }
}

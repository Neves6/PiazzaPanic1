package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertFalse;import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;import com.neves6.piazzapanic.gamemaster.ScenarioGameMaster;
import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.screens.PiazzaPanicGame;
import com.neves6.piazzapanic.staff.DeliveryStaff;
import com.neves6.piazzapanic.staff.IngredientsStaff;
import java.awt.*;import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestScenarioGameMaster {
  TiledMap map = new TmxMapLoader().load("tilemaps/level1.tmx");
  ArrayList<Integer> defValues = new ArrayList<>(Arrays.asList(1, 2));
  ScenarioGameMaster testMaster =
      new ScenarioGameMaster(
          new PiazzaPanicGame(),
          map,
          1,
          1,
          new Money(),
          new IngredientsStaff(defValues, defValues),
          new DeliveryStaff(defValues, defValues));

  @Test
  public void tryMoveValidUp() {
    testMaster.tryMove("up");
    assertTrue(testMaster.getChef(1).getyCoord() == 6);
    assertTrue(testMaster.getChef(1).getxCoord() == 6);
  }

  @Test
  public void tryMoveValidDown() {
    testMaster.tryMove("down");
    assertTrue(testMaster.getChef(1).getyCoord() == 4);
    assertTrue(testMaster.getChef(1).getxCoord() == 6);
  }

  @Test
  public void tryMoveValidRight() {
    testMaster.tryMove("right");
    assertTrue(testMaster.getChef(1).getxCoord() == 7);
    assertTrue(testMaster.getChef(1).getyCoord() == 5);
  }

  @Test
  public void tryMoveValidLeft() {
    testMaster.tryMove("left");
    assertTrue(testMaster.getChef(1).getxCoord() == 5);
    assertTrue(testMaster.getChef(1).getyCoord() == 5);
  }

  @Test
  public void tryMoveInvalidUp() {
    testMaster.getChef(1).setxCoord(7);
    testMaster.getChef(1).setyCoord(6);
    testMaster.tryMove("up");
    assertTrue(testMaster.getChef(1).getyCoord() == 6);
    assertTrue(testMaster.getChef(1).getxCoord() == 7);
  }

  @Test
  public void tryMoveInvalidDown() {
    testMaster.getChef(1).setxCoord(6);
    testMaster.getChef(1).setyCoord(4);
    testMaster.tryMove("down");
    assertTrue(testMaster.getChef(1).getyCoord() == 4);
    assertTrue(testMaster.getChef(1).getxCoord() == 6);
  }

  @Test
  public void tryMoveInvalidRight() {
    testMaster.getChef(1).setxCoord(6);
    testMaster.getChef(1).setyCoord(8);
    testMaster.tryMove("left");
    assertTrue(testMaster.getChef(1).getxCoord() == 6);
    assertTrue(testMaster.getChef(1).getyCoord() == 8);
  }

  @Test
  public void tryMoveInvalidLeft() {
    testMaster.getChef(1).setxCoord(2);
    testMaster.getChef(1).setyCoord(8);
    testMaster.tryMove("left");
    assertTrue(testMaster.getChef(1).getxCoord() == 2);
    assertTrue(testMaster.getChef(1).getyCoord() == 8);
  }

  @Test
  public void testDisplayTextEmpty() {
    assertTrue(testMaster.generateHoldingsText().equals("Chef 1 is holding:\n[]\n"));
  }

  ScenarioGameMaster testMasterII =
      new ScenarioGameMaster(
          new PiazzaPanicGame(),
          map,
          2,
          1,
          new Money(),
          new IngredientsStaff(defValues, defValues),
          new DeliveryStaff(defValues, defValues));

  @Test
  public void testDisplayTextFull() {
    testMasterII.getChef(1).addToInventory("t");
    testMasterII.getChef(1).addToInventory("e");
    testMasterII.getChef(2).addToInventory("s");
    testMasterII.getChef(2).addToInventory("t");
    assertTrue(
        testMasterII
            .generateHoldingsText()
            .equals("Chef 1 is holding:\n[t, e]\nChef 2 is holding:\n[s, t]\n"));
  }

  ScenarioGameMaster testMasterIV =
      new ScenarioGameMaster(
          new PiazzaPanicGame(),
          map,
          3,
          3,
          new Money(),
          new IngredientsStaff(defValues, defValues),
          new DeliveryStaff(defValues, defValues));

  @Test
  public void testGenerateCustomersTrayText() {
    String testString = testMasterIV.generateCustomersTrayText();
    assertTrue(
        testString.equals(
                "Customers remaining: 3\n"
                    + "Order: jacket potato\n"
                    + "Tray 1 contents: []\n"
                    + "Tray 2 contents: []")
            || testString.equals(
                "Customers remaining: 3\n"
                    + "Order: hamburger\n"
                    + "Tray 1 contents: []\n"
                    + "Tray 2 contents: []")
            || testString.equals(
                "Customers remaining: 3\n"
                    + "Order: pizza\n"
                    + "Tray 1 contents: []\n"
                    + "Tray 2 contents: []")
            || testString.equals(
                "Customers remaining: 3\n"
                    + "Order: salad\n"
                    + "Tray 1 contents: []\n"
                    + "Tray 2 contents: []"));
  }

  @Test
  public void testGenerateTimerText() {
    assertTrue(testMasterIV.generateTimerText().equals("Time elapsed: 0 s"));
  }

  @Test
  public void testGetMachineTimerForChefNull() {
    assertTrue(testMasterIV.getMachineTimerForChef(1).equals(""));
  }

  @Test
  public void testGetMachineTimerForChef() {
    Machine cooker = new Machine("Cooker", "Patty", "Burger", 3, true, "1234");
    testMasterIV.getChef(2).setMachineInteractingWith(cooker);
    assertTrue(testMasterIV.getMachineTimerForChef(1).equals(4 + ""));
  }

  @Test
  public void testGetCorrectChef() {
    testMaster.setSelectedChef(1);
    assertTrue(testMaster.getSelectedChef() == 1);
  }

  @Test
  public void testChefIsFrozenWhenStuck() {
    testMaster.getChef(1).setIsStickied(true);
    assertTrue(testMaster.wouldNotCollide(1, 1, 0) == false);
  }

  @Test
  public void testChefCanNotCoverAnotherChef() {
    testMasterIV.getChef(2).setyCoord(4);
    testMasterIV.getChef(2).setxCoord(5);
    assertTrue(testMasterIV.wouldNotCollide(5, 4, 0) == false);
  }

  @Test
  public void testGetNumberOfCustomers() {
    assertTrue(testMasterIV.getCustomersRemaining() == 3);
    // Need another to test after completing recipe
  }

  @Test
  public void testGetUnlockLayer() {
    ArrayList<String> testFinder =
        new ArrayList<>(
            Arrays.asList(
                "potato",
                "chopping",
                "forming",
                "grill",
                "server-staff",
                "pizza",
                "ingredients-staff"));
    MapObjects testLayer = testMasterIV.getObjectLayers("Unlock Layer");
    for (MapObject item : testLayer) {
      assertTrue(testFinder.contains(item.getName()));
    }
  }

  @Test
  public void testGetFridgeLayer() {
    ArrayList<String> testFinder =
        new ArrayList<>(
            Arrays.asList(
                "fridge-dough",
                "fridge-beans",
                "fridge-potato",
                "fridge-cheese",
                "fridge-onion",
                "fridge-lettuce",
                "fridge-lettuce",
                "fridge-tomato",
                "fridge-meat",
                "fridge-bun"));
    MapObjects testLayer = testMasterIV.getObjectLayers("Fridge Layer");
    for (MapObject item : testLayer) {
      assertTrue(testFinder.contains(item.getName()));
    }
  }

  @Test
  public void testCookingLayer() {
    ArrayList<String> testFinder =
        new ArrayList<>(
            Arrays.asList(
                "forming-1",
                "oven-pizza-2",
                "oven-pizza-1",
                "oven-potato-1",
                "oven-potato-2",
                "chopping-onion-2",
                "chopping-tomato-2",
                "chopping-lettuce-2",
                "chopping-tomato-1",
                "chopping-onion-1",
                "chopping-lettuce-1",
                "forming-2",
                "grill-bun-2",
                "grill-patty-2",
                "grill-bun-1",
                "grill-patty-1"));
    MapObjects testLayer = testMasterIV.getObjectLayers("Cooking Layer");
    for (MapObject item : testLayer) {
      assertTrue(testFinder.contains(item.getName()));
    }
  }

  @Test
  public void testMiscLayer() {
    ArrayList<String> testFinder =
        new ArrayList<>(Arrays.asList("bin", "fast-track-collect", "tray-1", "tray-2", "serving"));
    MapObjects testLayer = testMasterIV.getObjectLayers("Misc Layer");
    for (MapObject item : testLayer) {
      assertTrue(testFinder.contains(item.getName()));
    }
  }

  @Test
  public void testValidTiledOverlap() {
    MapObjects testLayer = testMasterIV.getObjectLayers("Misc Layer");
    Rectangle testRec = testMasterIV.loadRectangle(testLayer.get("bin"));
    assertTrue(testMaster.detectInteractionFromTiledObject(testRec, 14, 4));
  }

  @Test
  public void testInvalidTiledOverlap() {
    MapObjects testLayer = testMasterIV.getObjectLayers("Misc Layer");
    Rectangle testRec = testMasterIV.loadRectangle(testLayer.get("bin"));
    assertFalse(testMaster.detectInteractionFromTiledObject(testRec, 4, 4));
  }

  @Test
  public void testLockedChef(){
    final int ORIGINAL_X = testMasterIV.getChef(1).getxCoord();
    final int ORIGINAL_Y = testMasterIV.getChef(1).getyCoord();
    testMasterIV.getChef(1).setIsStickied(true);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertTrue(testMasterIV.getChef(1).getxCoord() == ORIGINAL_X);
    assertTrue(testMasterIV.getChef(1).getyCoord() == ORIGINAL_Y);
  }

  @Test
  public void testInteractWithUnlockLayerDown(){
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getChef(1).setFacing("down");
    testMasterIV.getChef(1).setxCoord(2);
    testMasterIV.getChef(1).setyCoord(7);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertTrue(testMasterIV.getUnlockClass().unlockMachine("ingredients-staff"));
  }

  @Test
  public void testInteractWithUnlockLayerUp(){
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getChef(1).setFacing("up");
    testMasterIV.getChef(1).setxCoord(2);
    testMasterIV.getChef(1).setyCoord(9);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertTrue(testMasterIV.getUnlockClass().unlockMachine("ingredients-staff"));
  }
  @Test
  public void testInteractWithUnlockLayerRight(){
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getChef(1).setFacing("right");
    testMasterIV.getChef(1).setxCoord(1);
    testMasterIV.getChef(1).setyCoord(8);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertTrue(testMasterIV.getUnlockClass().unlockMachine("ingredients-staff"));
  }

  @Test
  public void testInteractWithUnlockLayerLeft(){
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getChef(1).setFacing("left");
    testMasterIV.getChef(1).setxCoord(3);
    testMasterIV.getChef(1).setyCoord(8);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertTrue(testMasterIV.getUnlockClass().unlockMachine("ingredients-staff"));
  }

}

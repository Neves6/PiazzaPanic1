package com.neves6.piazzapanic.tests;

import static org.junit.Assert.*;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.neves6.piazzapanic.gamemaster.ScenarioGameMaster;
import com.neves6.piazzapanic.gamemaster.TiledMapMaster;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.screens.PiazzaPanicGame;
import com.neves6.piazzapanic.staff.DeliveryStaff;
import com.neves6.piazzapanic.staff.IngredientsStaff;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestScenarioGameMaster {
  PiazzaPanicGame testGame = new PiazzaPanicGame();
  TiledMap map = new TmxMapLoader().load("tilemaps/testdouble.tmx");
  TiledMapMaster testTiledLoader = new TiledMapMaster(map);
  ArrayList<Integer> defValues = new ArrayList<>(Arrays.asList(1, 2));
  ScenarioGameMaster testMaster =
      new ScenarioGameMaster(
          testGame,
          map,
          1,
          1,
          new Money(),
          new IngredientsStaff(defValues, defValues),
          new DeliveryStaff(defValues, defValues),
          false,
          1);

  //  @Test
  //  public void tryMoveValidUp() {
  //    testMaster.tryMove("up");
  //    assertEquals(
  //        "Moving up should change the y axis in the positive direction",
  //        6,
  //        testMaster.getChef(1).getyCoord());
  //    assertEquals("Moving up should not effect the x axis", 6,
  // testMaster.getChef(1).getxCoord());
  //  }
  //
  //  @Test
  //  public void tryMoveValidDown() {
  //    testMaster.tryMove("down");
  //    assertEquals(
  //        "Moving up should change the y axis in the negative direction",
  //        4,
  //        testMaster.getChef(1).getyCoord());
  //    assertEquals("Moving up should not effect the x axis", 6,
  // testMaster.getChef(1).getxCoord());
  //  }
  //
  //  @Test
  //  public void tryMoveValidRight() {
  //    testMaster.tryMove("right");
  //    assertEquals(
  //        "Moving up should change the x axis in the positive direction",
  //        7,
  //        testMaster.getChef(1).getxCoord());
  //    assertEquals("Moving up should not effect the y axis", 5,
  // testMaster.getChef(1).getyCoord());
  //  }
  //
  //  @Test
  //  public void tryMoveValidLeft() {
  //    testMaster.tryMove("left");
  //    assertEquals(
  //        "Moving up should change the x axis in the negative direction",
  //        5,
  //        testMaster.getChef(1).getxCoord());
  //    assertEquals("Moving up should not effect the y axis", 5,
  // testMaster.getChef(1).getyCoord());
  //  }

  @Test
  public void tryMoveInvalidUp() {
    testMaster.getChef(1).setxCoord(7);
    testMaster.getChef(1).setyCoord(6);
    testMaster.tryMove("up");
    assertEquals(
        "Moving up should not effect the x axis if there is a collision tile",
        6,
        testMaster.getChef(1).getyCoord());
    assertEquals(
        "Moving up should not effect the y axis if there is a collision tile",
        7,
        testMaster.getChef(1).getxCoord());
  }

  @Test
  public void tryMoveInvalidDown() {
    testMaster.getChef(1).setxCoord(6);
    testMaster.getChef(1).setyCoord(4);
    testMaster.tryMove("down");
    assertEquals(
        "Moving down should not effect the y axis if there is a collision tile",
        4,
        testMaster.getChef(1).getyCoord());
    assertEquals(
        "Moving down should not effect the y axis if there is a collision tile",
        6,
        testMaster.getChef(1).getxCoord());
  }

  @Test
  public void tryMoveInvalidRight() {
    testMaster.getChef(1).setxCoord(6);
    testMaster.getChef(1).setyCoord(8);
    testMaster.tryMove("left");
    assertEquals(
        "Moving right should not effect the y axis if there is a collision tile",
        6,
        testMaster.getChef(1).getxCoord());
    assertEquals(
        "Moving right should not effect the y axis if there is a collision tile",
        8,
        testMaster.getChef(1).getyCoord());
  }

  @Test
  public void tryMoveInvalidLeft() {
    testMaster.getChef(1).setxCoord(2);
    testMaster.getChef(1).setyCoord(8);
    testMaster.tryMove("left");
    assertEquals(
        "Moving left should not effect the y axis if there is a collision tile",
        2,
        testMaster.getChef(1).getxCoord());
    assertEquals(
        "Moving left should not effect the y axis if there is a collision tile",
        8,
        testMaster.getChef(1).getyCoord());
  }

  ScenarioGameMaster testMasterII =
      new ScenarioGameMaster(
          testGame,
          map,
          2,
          5,
          new Money(),
          new IngredientsStaff(defValues, defValues),
          new DeliveryStaff(defValues, defValues),
          true,
          1);

  ScenarioGameMaster testMasterIV =
      new ScenarioGameMaster(
          testGame,
          map,
          3,
          3,
          new Money(),
          new IngredientsStaff(defValues, defValues),
          new DeliveryStaff(defValues, defValues),
          false,
          1);

  /* TODO: Create new test for text based on customer orders
           Customers are no longer generated upon loading game scenario, instead are produced at slight random intervals throughout
           So value cannot be easily tested without public setter method for customer objects
  @Test
  public void testGenerateCustomersTrayText() {
    String testString = testMasterIV.generateCustomersTrayText();
    assertTrue(
        "Text with at least one order should display either a jacket potato, hamburger, pizza or"
            + " salad",
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
   */

  @Test
  public void testGetCorrectChef() {
    testMaster.setSelectedChef(1);
    assertEquals(
        "setSelectedChef should set the selected chef variable", 1, testMaster.getSelectedChef());
  }

  @Test
  public void testChefIsFrozenWhenStuck() {
    testMaster.getChef(1).setIsStickied(true);
    assertFalse(
        "If a chef is stuck, the testMaster should not try and move it",
        testMaster.wouldNotCollide(1, 1, 0));
  }

  @Test
  public void testChefCanNotCoverAnotherChef() {
    testMasterIV.getChef(2).setyCoord(4);
    testMasterIV.getChef(2).setxCoord(5);
    assertFalse("Two chefs must not overlap", testMasterIV.wouldNotCollide(5, 4, 0));
  }

  /* TODO: Create new test for get number of customers
          Customers are no longer generated upon loading game scenario, instead are produced at slight random intervals throughout
          So value cannot be easily tested without public setter method for customer objects
   @Test
   public void testGetNumberOfCustomers() {
     assertEquals(
         "Initially before a recipe is complete, the amount of customers should be the same as"
             + " passed in, in the constructor",
         3,
         testMasterIV.getCustomersRemaining());
   }
  */

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
    MapObjects testLayer = testTiledLoader.getObjectLayers("Unlock Layer");
    for (MapObject item : testLayer) {
      assertTrue(
          "All of the objects in the unlock layer should be detected.",
          testFinder.contains(item.getName()));
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
    MapObjects testLayer = testTiledLoader.getObjectLayers("Fridge Layer");
    for (MapObject item : testLayer) {
      assertTrue(
          "All of the objects in the fridge layer should be detected.",
          testFinder.contains(item.getName()));
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
    MapObjects testLayer = testTiledLoader.getObjectLayers("Cooking Layer");
    for (MapObject item : testLayer) {
      assertTrue(
          "All of the objects in the cooking layer should be detected.",
          testFinder.contains(item.getName()));
    }
  }

  @Test
  public void testMiscLayer() {
    ArrayList<String> testFinder =
        new ArrayList<>(Arrays.asList("bin", "fast-track-collect", "tray-1", "tray-2", "serving"));
    MapObjects testLayer = testTiledLoader.getObjectLayers("Misc Layer");
    for (MapObject item : testLayer) {
      assertTrue(
          "All of the objects in the misc layer should be detected.",
          testFinder.contains(item.getName()));
    }
  }

  @Test
  public void testValidTiledOverlap() {
    MapObjects testLayer = testTiledLoader.getObjectLayers("Misc Layer");
    Rectangle testRec = testTiledLoader.loadRectangle(testLayer.get("bin"));
    assertTrue(
        "The user should be able to interact with objects on the tiled map if in the right"
            + " position",
        testTiledLoader.detectInteractionFromTiledObject(testRec, 14, 4));
  }

  @Test
  public void testInvalidTiledOverlap() {
    MapObjects testLayer = testTiledLoader.getObjectLayers("Misc Layer");
    Rectangle testRec = testTiledLoader.loadRectangle(testLayer.get("bin"));
    assertFalse(
        "The user should not be able to interact with objects on the tiled map if the wrong"
            + " position",
        testTiledLoader.detectInteractionFromTiledObject(testRec, 4, 4));
  }

  @Test
  public void testLockedChef() {
    final int ORIGINAL_X = testMasterIV.getChef(1).getxCoord();
    final int ORIGINAL_Y = testMasterIV.getChef(1).getyCoord();
    testMasterIV.getChef(1).setIsStickied(true);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertEquals(
        "You should not be able to move an unlocked chef no matter the position",
        testMasterIV.getChef(1).getxCoord(),
        ORIGINAL_X);
    assertEquals(
        "You should not be able to move an unlocked chef no matter the position",
        testMasterIV.getChef(1).getyCoord(),
        ORIGINAL_Y);
  }

  @Test
  public void testInteractWithUnlockLayerDown() {
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getChef(1).setFacing("down");
    testMasterIV.getChef(1).setxCoord(2);
    testMasterIV.getChef(1).setyCoord(7);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertTrue(
        "Should be able to interact with a object from the unlock layer when facing down",
        testMasterIV.getUnlockClass().unlockMachine("ingredients-staff"));
  }

  @Test
  public void testInteractWithUnlockLayerUp() {
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getChef(1).setFacing("up");
    testMasterIV.getChef(1).setxCoord(2);
    testMasterIV.getChef(1).setyCoord(9);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertTrue(
        "Should be able to interact with a object from the unlock layer when facing up",
        testMasterIV.getUnlockClass().unlockMachine("ingredients-staff"));
  }

  @Test
  public void testInteractWithUnlockLayerRight() {
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getChef(1).setFacing("right");
    testMasterIV.getChef(1).setxCoord(1);
    testMasterIV.getChef(1).setyCoord(8);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertTrue(
        "Should be able to interact with a object from the unlock layer when facing right",
        testMasterIV.getUnlockClass().unlockMachine("ingredients-staff"));
  }

  @Test
  public void testInteractWithUnlockLayerLeft() {
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getChef(1).setFacing("left");
    testMasterIV.getChef(1).setxCoord(3);
    testMasterIV.getChef(1).setyCoord(8);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertTrue(
        "Should be able to interact with a object from the unlock layer when facing left",
        testMasterIV.getUnlockClass().unlockMachine("ingredients-staff"));
  }

  @Test
  public void testInteractWithFridgeLayerDown() {
    testMasterIV.getChef(1).setFacing("down");
    testMasterIV.getChef(1).setxCoord(1);
    testMasterIV.getChef(1).setyCoord(9);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertSame(
        "Should be able to interact with a object from the fridge layer when facing down",
        "bun",
        testMasterIV.getChef(1).getInventory().peek());
  }

  @Test
  public void testInteractWithFridgeLayerUp() {
    testMasterIV.getChef(1).setFacing("up");
    testMasterIV.getChef(1).setxCoord(1);
    testMasterIV.getChef(1).setyCoord(7);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertSame(
        "Should be able to interact with a object from the fridge layer when facing up",
        "bun",
        testMasterIV.getChef(1).getInventory().peek());
  }

  @Test
  public void testInteractWithFridgeLayerRight() {
    testMasterIV.getChef(1).setFacing("right");
    testMasterIV.getChef(1).setxCoord(0);
    testMasterIV.getChef(1).setyCoord(8);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertSame(
        "Should be able to interact with a object from the fridge layer when facing right",
        "bun",
        testMasterIV.getChef(1).getInventory().peek());
  }

  @Test
  public void testInteractWithFridgeLayerLeft() {
    testMasterIV.getChef(1).setFacing("left");
    testMasterIV.getChef(1).setxCoord(2);
    testMasterIV.getChef(1).setyCoord(8);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertSame(
        "Should be able to interact with a object from the fridge layer when facing left",
        "bun",
        testMasterIV.getChef(1).getInventory().peek());
  }

  @Test
  public void testInteractWithCookingLayerDown() {
    testMasterIV.getChef(1).setFacing("down");
    testMasterIV.getChef(1).setxCoord(9);
    testMasterIV.getChef(1).setyCoord(8);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertNull(testMasterIV.getChef(1).getMachineInteractingWith());
    testMasterIV.getChef(1).addToInventory("meat");
    testMasterIV.tryInteract();
    assertSame(
        "Should be able to interact with a object from the fridge layer when facing down",
        "meat",
        testMasterIV.getChef(1).getMachineInteractingWith().getInput());
  }

  @Test
  public void testInteractWithCookingLayerUp() {
    testMasterIV.getChef(1).setFacing("up");
    testMasterIV.getChef(1).setxCoord(9);
    testMasterIV.getChef(1).setyCoord(6);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertNull(testMasterIV.getChef(1).getMachineInteractingWith());
    testMasterIV.getChef(1).addToInventory("meat");
    testMasterIV.tryInteract();
    assertSame(
        "Should be able to interact with a object from the fridge layer when facing up",
        "meat",
        testMasterIV.getChef(1).getMachineInteractingWith().getInput());
  }

  @Test
  public void testInteractWithCookingLayerRight() {
    testMasterIV.getChef(1).setFacing("right");
    testMasterIV.getChef(1).setxCoord(8);
    testMasterIV.getChef(1).setyCoord(7);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertNull(testMasterIV.getChef(1).getMachineInteractingWith());
    testMasterIV.getChef(1).addToInventory("meat");
    testMasterIV.tryInteract();
    assertSame(
        "Should be able to interact with a object from the cooking layer when facing right",
        "meat",
        testMasterIV.getChef(1).getMachineInteractingWith().getInput());
  }

  @Test
  public void testInteractWithCookingLayerLeft() {
    testMasterIV.getChef(1).setFacing("left");
    testMasterIV.getChef(1).setxCoord(10);
    testMasterIV.getChef(1).setyCoord(7);
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertNull(testMasterIV.getChef(1).getMachineInteractingWith());
    testMasterIV.getChef(1).addToInventory("meat");
    testMasterIV.tryInteract();
    assertEquals(
        "Should be able to interact with a object from the fridge layer when facing left",
        "meat",
        testMasterIV.getChef(1).getMachineInteractingWith().getInput());
  }

  @Test
  public void testBinInteraction() {
    testMasterIV.getChef(1).addToInventory("keep");
    testMasterIV.getChef(1).addToInventory("remove");
    testMasterIV.getChef(1).setxCoord(14);
    testMasterIV.getChef(1).setyCoord(5);
    testMasterIV.getChef(1).setFacing("down");
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertEquals(
        "Interaction with bin must remove top of the chefs stack",
        testMasterIV.getChef(1).getInventory().peek(),
        "keep");
  }

  @Test
  public void testIngredientsStaffInteractionLocked() {
    testMasterIV.getChef(1).addToInventory("keep");
    testMasterIV.getChef(1).setxCoord(2);
    testMasterIV.getChef(1).setyCoord(7);
    testMasterIV.getChef(1).setFacing("down");
    testMasterIV.setSelectedChef(1);
    testMasterIV.tryInteract();
    assertEquals(
        "Interaction with ingredients staff if not unlocked shouldn't add anything to the stack",
        testMasterIV.getChef(1).getInventory().peek(),
        "keep");
  }

  @Test
  public void testIngredientsStaffInteractionUnlocked() {
    testMasterIV.getChef(1).addToInventory("keep");
    testMasterIV.getChef(1).setxCoord(2);
    testMasterIV.getChef(1).setyCoord(9);
    testMasterIV.getChef(1).setFacing("down");
    testMasterIV.setSelectedChef(1);
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getUnlockClass().incrementBalance();
    testMasterIV.getUnlockClass().unlockMachine("ingredients-staff");
    testMasterIV.tryInteract();
    assertEquals(
        "Interaction with ingredients staff unlocked should add an ingredient to the stack",
        testMasterIV.getChef(1).getInventory().peek(),
        "keep");
  }

  ScenarioGameMaster testMasterV =
      new ScenarioGameMaster(
          testGame,
          map,
          3,
          0,
          new Money(),
          new IngredientsStaff(defValues, defValues),
          new DeliveryStaff(defValues, defValues),
          false,
          1);

  @Test
  public void testServeFoodNoCust() {
    testMasterIV.serveFood();
    assertTrue(testMasterIV.getChef(testMasterIV.getSelectedChef()).getInventory().size() == 0);
  }

  @Test
  public void testServeFoodEmptyChefInv() {
    while (testMasterIV.getCustomers().size() < 1) {
      testMasterIV.tickUpdate(1);
    }
    testMasterIV.serveFood();
    assertTrue(testMasterIV.getChef(testMasterIV.getSelectedChef()).getInventory().size() == 0);
  }

  @Test
  public void testServeFood() {
    while (testMasterIV.getCustomers().size() < 1) {
      testMasterIV.tickUpdate(1);
    }
    int custOriginal = testMasterIV.getCustomers().size();
    testMasterIV
        .getChef(testMasterIV.getSelectedChef())
        .addToInventory(testMasterIV.getFirstCustomer().getOrder());
    testMasterIV.serveFood();
    assertTrue(testMasterIV.getCustomers().size() == custOriginal - 1);
  }

  @Test
  public void testServeFoodJunk() {
    while (testMasterIV.getCustomers().size() < 1) {
      testMasterIV.tickUpdate(1);
    }
    int custOriginal = testMasterIV.getCustomers().size();
    testMasterIV.getChef(testMasterIV.getSelectedChef()).addToInventory("Junk");
    testMasterIV.serveFood();
    assertTrue(testMasterIV.getCustomers().size() == custOriginal);
  }

  @Test
  public void testServeFoodNoPowerUp() {
    while (testMasterII.getCustomers().size() < 1) {
      testMasterII.tickUpdate(1);
    }
    testMasterII
        .getChef(testMasterII.getSelectedChef())
        .addToInventory(testMasterII.getFirstCustomer().getOrder());
    testMasterII.serveFood();
    assertTrue(testMasterII.getChef(testMasterII.getSelectedChef()).getInventory().size() == 0);
    assertTrue(
        "If no powerup has been activated, text must just contain Current Active Powerups: '",
        testMasterII.getPowerUpRunner().displayText().equals("Current Active Powerups: \n"));
  }
}

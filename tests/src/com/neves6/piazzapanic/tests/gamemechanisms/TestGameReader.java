package com.neves6.piazzapanic.tests.gamemechanisms;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.neves6.piazzapanic.gamemaster.ScenarioGameMaster;
import com.neves6.piazzapanic.gamemechanisms.GameReader;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.screens.PiazzaPanicGame;
import com.neves6.piazzapanic.staff.DeliveryStaff;
import com.neves6.piazzapanic.staff.IngredientsStaff;
import com.neves6.piazzapanic.tests.GdxTestRunner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestGameReader {
  @Test
  public void testLoadFromFileI() throws ParseException, IOException {
    Money testMoney = new Money();

    Stack<String> testStack = new Stack<>();
    testStack.add("object");

    TiledMap map = new TmxMapLoader().load("tilemaps/testdouble.tmx");

    GameReader testReader = new GameReader("testLoadFromFileI.json");
    ScenarioGameMaster testgm =
        testReader.createGameMaster(
            new PiazzaPanicGame(),
            map,
            testMoney,
            new IngredientsStaff(
                new ArrayList<>(Arrays.asList(1)), new ArrayList<>(Arrays.asList(1))),
            new DeliveryStaff(
                new ArrayList<>(Arrays.asList(1)), new ArrayList<>(Arrays.asList(1))));

    assertTrue("The selected chef must be loaded from the json", 2 == testgm.getSelectedChef());
    assertTrue("The balance must be loaded in from the file", 25.5f == testMoney.getBalance());
    assertTrue(
        "All unlocks should be used from previously saved game", testMoney.isUnlocked("auto"));
    assertTrue(
        "All unlocks should be used from previously saved game", testMoney.isUnlocked("forming"));
    assertFalse(
        "All unlocks should be used from previously saved game", testMoney.isUnlocked("potato"));
    assertTrue("The time must be continued from the save", testgm.getTimer() == 2);
    assertTrue("The chefs position must be maintained.", testgm.getChef(1).getxCoord() == 1);
    assertTrue("The chefs position must be maintained.", testgm.getChef(1).getyCoord() == 2);
    assertTrue(
        "The chefs inventory should be maintained",
        testgm.getChef(1).getInventory().equals(testStack));
    assertTrue(
        "Order for the first customer must be the same",
        testgm.getFirstCustomer().getOrder().equals("potato"));
    assertTrue(
        "Order time for the first customer must be the same",
        testgm.getFirstCustomer().getTimeArrived() == 2.0f);
    assertTrue(
        "All the power-ups must only be active if they were activated before",
        testgm.getPowerUpRunner().displayText().startsWith("Current Active Powerups: \n"));
  }

  @Test
  public void testLoadFromFileII() throws ParseException, IOException {
    Money testMoney = new Money();

    Stack<String> testStack = new Stack<>();
    testStack.add("object");

    TiledMap map = new TmxMapLoader().load("tilemaps/testdouble.tmx");

    GameReader testReader = new GameReader("testLoadFromFileII.json");
    ScenarioGameMaster testgm =
        testReader.createGameMaster(
            new PiazzaPanicGame(),
            map,
            testMoney,
            new IngredientsStaff(
                new ArrayList<>(Arrays.asList(1)), new ArrayList<>(Arrays.asList(1))),
            new DeliveryStaff(
                new ArrayList<>(Arrays.asList(1)), new ArrayList<>(Arrays.asList(1))));

    assertTrue("The selected chef must be loaded from the json", 2 == testgm.getSelectedChef());
    assertTrue("The balance must be loaded in from the file", 25.5f == testMoney.getBalance());
    assertTrue(
        "All unlocks should be used from previously saved game", testMoney.isUnlocked("auto"));
    assertTrue(
        "All unlocks should be used from previously saved game", testMoney.isUnlocked("forming"));
    assertFalse(
        "All unlocks should be used from previously saved game", testMoney.isUnlocked("potato"));
    assertTrue("The time must be continued from the save", testgm.getTimer() == 2);
    assertTrue("The chefs position must be maintained.", testgm.getChef(1).getxCoord() == 1);
    assertTrue("The chefs position must be maintained.", testgm.getChef(1).getyCoord() == 2);
    assertTrue(
        "The chefs inventory should be maintained",
        testgm.getChef(1).getInventory().equals(testStack));
    assertTrue(
        "All the power-ups must only be active if they were activated before",
        testgm.getPowerUpRunner().displayText().startsWith("Current Active Powerups: \n"));
    assertTrue(
        "Any active powerups should be continued from the json",
        testgm.getPowerUpRunner().displayText().contains("1/2 price machines for 30 seconds"));
  }
}

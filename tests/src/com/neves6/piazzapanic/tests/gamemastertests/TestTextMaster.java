package com.neves6.piazzapanic.tests.gamemastertests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.neves6.piazzapanic.gamemaster.ScenarioGameMaster;
import com.neves6.piazzapanic.gamemaster.TextMaster;
import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.people.Customer;
import com.neves6.piazzapanic.screens.PiazzaPanicGame;
import com.neves6.piazzapanic.staff.DeliveryStaff;
import com.neves6.piazzapanic.staff.IngredientsStaff;
import com.neves6.piazzapanic.tests.GdxTestRunner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestTextMaster {
  PiazzaPanicGame testGame = new PiazzaPanicGame();
  TiledMap map = new TmxMapLoader().load("tilemaps/testdouble.tmx");
  TextMaster tm = new TextMaster();
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

  @Test
  public void testDisplayTextEmpty() {
    TextMaster testTextMaster = new TextMaster();
    assertEquals(
        "Expected format for empty inventorys is: 'Chef 1 is holding:\n[]\n'",
        "Chef 1 is holding:\n[]\n",
        testTextMaster.generateHoldingsText(testMaster.getChefs()));
  }

  ScenarioGameMaster testMasterII =
      new ScenarioGameMaster(
          testGame,
          map,
          2,
          1,
          new Money(),
          new IngredientsStaff(defValues, defValues),
          new DeliveryStaff(defValues, defValues),
          false,
          1);

  @Test
  public void testDisplayTextFull() {
    TextMaster testTextMaster = new TextMaster();
    testMasterII.getChef(1).addToInventory("t");
    testMasterII.getChef(1).addToInventory("e");
    testMasterII.getChef(2).addToInventory("s");
    testMasterII.getChef(2).addToInventory("t");

    assertEquals(
        "Expected format for empty inventorys is: 'Chef 1 is holding:\n"
            + "[item1, item2, itemn]\n"
            + "Chef n is holding:\n"
            + "[item1..itemn]\n"
            + "'",
        "Chef 1 is holding:\n[t, e]\nChef 2 is holding:\n[s, t]\n",
        testTextMaster.generateHoldingsText(testMasterII.getChefs()));
  }

  @Test
  public void testReputationPointTextStart() {
    assertTrue(
        "If no reputation points have been lost in the first 3 seconds" + " shown",
        tm.generateReputationPointText(2f, 0f, 3).equals("Reputation points: 3"));
  }

  @Test
  public void testReputationPointText() {
    assertTrue(
        "If no reputation points have been lost in the past 3 seconds only the reputation points"
            + " should be shown",
        tm.generateReputationPointText(100f, 0f, 3).equals("Reputation points: 3"));
  }

  @Test
  public void testReputationPointTextLost() {
    assertTrue(
        "If reputation points have been lost in the past 3 seconds only the reputation points"
            + " should be along with -1 shown",
        tm.generateReputationPointText(102f, 101f, 3).equals("Reputation points: 3 -1"));
  }

  @Test
  public void testGenerateCustomerLeft() {
    assertTrue(
        "If a customer has left it should notify the user",
        tm.generateCustomerLeftText(100f, 102f)
            .equals("A customer was tired of waiting\n" + "Reputation point lost"));
  }

  @Test
  public void testGenerateCustomerLeftStarting() {
    assertTrue(
        "A customer should not lose points in the first 3 seconds.",
        tm.generateCustomerLeftText(2f, 0f).equals(""));
  }

  @Test
  public void testGenerateCustomerLeftNot() {
    assertTrue(
        "If a customer has left it should notify the user",
        tm.generateCustomerLeftText(100f, 0f).equals(""));
  }

  @Test
  public void testGenerateTimerText() {
    assertEquals(
        "Timer text should be in the format: 'Time elapsed: x s",
        "Time elapsed: 0 s",
        tm.generateTimerText(testMaster.getTotalTimerDisplay()));
  }

  @Test
  public void testGetMachineTimerForChef() {
    Machine cooker = new Machine("Cooker", "Patty", "Burger", 3, true, "1234");
    testMasterIV.getChef(2).setMachineInteractingWith(cooker);
    assertEquals(
        "If chef is assigned to a machine, the chef machine text should display the number of"
            + " seconds left of the interaction",
        4 + "",
        tm.getMachineTimerForChef(1, testMasterIV.getChefs()));
  }

  @Test
  public void testGetMachineTimerForChefNull() {
    assertEquals(
        "If chef is not assigned to a machine, the chef machine text should be blank",
        "",
        tm.getMachineTimerForChef(1, testMasterII.getChefs()));
  }

  @Test
  public void testCustomerTrayText() {
    Queue<Customer> customers = new LinkedList<>();
    customers.add(new Customer("test", 1, 1, "pizza", 1f));
    ArrayList<String> tray1 = new ArrayList<>(Arrays.asList("tomato"));
    ArrayList<String> tray2 = new ArrayList<>(Arrays.asList("ruined burger"));
    tm.generateCustomersTrayText(customers, tray1, tray2);

    assertEquals(
        "Order and tray contents must be shown",
        tm.generateCustomersTrayText(customers, tray1, tray2),
        "Customers remaining: 1\n"
            + "Order: pizza\n"
            + "Tray 1 contents: [tomato]\n"
            + "Tray 2 contents: [ruined burger]");
  }

  @Test
  public void testCustomerTrayTextEmpty() {
    Queue<Customer> customers = new LinkedList<>();
    ArrayList<String> tray1 = new ArrayList<>();
    ArrayList<String> tray2 = new ArrayList<>();
    tm.generateCustomersTrayText(customers, tray1, tray2);

    assertEquals(
        "Order and tray contents must be shown",
        tm.generateCustomersTrayText(customers, tray1, tray2),
        "Customers remaining: 0");
  }
}

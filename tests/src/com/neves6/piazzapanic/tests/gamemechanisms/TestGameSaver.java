package com.neves6.piazzapanic.tests.gamemechanisms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neves6.piazzapanic.gamemechanisms.GameSaver;
import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.people.Chef;
import com.neves6.piazzapanic.people.Customer;
import com.neves6.piazzapanic.powerups.PowerUpRunner;
import com.neves6.piazzapanic.tests.GdxTestRunner;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestGameSaver {
  GameSaver testSaver = new GameSaver("ignore.json");

  @Test
  public void testInvalidDifficultyLower() {
    assertFalse(
        "Entering a value between 1 and 3 should be" + "the only valid number.",
        testSaver.setDifficulty(0));
  }

  @Test
  public void testInvalidDifficultyHigher() {
    assertFalse(
        "Entering a value between 1 and 3 should be" + "the only valid number.",
        testSaver.setDifficulty(4));
  }

  @Test
  public void testAddNoCustomers() {
    assertFalse("Entering 0 or less customers is invalid", testSaver.setCustomersRemaining(-2));
  }

  @Test
  public void testDecrementCustomerEndless() {
    testSaver.setCustomersRemaining(-1);
    assertFalse(
        "You cannot decrement customers when running in endless mode.",
        testSaver.decrementCustomers());
  }

  @Test
  public void testAddNegativeReputationPoints() {
    assertFalse(
        "You can not have negative reputation point during a running game",
        testSaver.setReputationPoints(-1));
  }

  @Test
  public void testCustomersNull() {
    assertFalse("Customers cannot be null when adding details", testSaver.setRecipe(null));
  }

  @Test
  public void testTimeNeg() {
    assertFalse("Time cannot be negative", testSaver.setTime(-1f));
  }

  @Test
  public void testCorrectFileFormat() throws IOException {
    testSaver.setDifficulty(2);
    testSaver.setPowerUp(true);
    testSaver.setCustomersRemaining(2);

    Money testMoney = new Money();
    testMoney.addGroup("test1", 200f);
    testMoney.addGroup("test2", 25.5f);
    testMoney.incrementBalance();
    testMoney.incrementBalance();
    testMoney.unlockMachine("test1");
    testMoney.saveMoneyDetails(testSaver);

    Chef testChef = new Chef("c1", 1, 1, 1, 1, 1, false, new Stack<>(), 1);
    testChef.addToInventory("object");
    ArrayList<Chef> testChefs = new ArrayList<>(Arrays.asList(testChef));
    testSaver.setChefDetails(testChefs, 1);

    Customer testCustomer = new Customer("c1", 1, 1, "potato", 2f);

    testSaver.setRecipe(testCustomer);

    testSaver.setReputationPoints(2);
    testSaver.setTime(2f);

    PowerUpRunner testPURunner = new PowerUpRunner(testChefs, new HashMap<String, Machine>(), testMoney, testSaver);
    testPURunner.savePowerupStatus();

    testSaver.setTrays(new ArrayList<>(Arrays.asList("test1")), new ArrayList<>(Arrays.asList("test2")));

    testSaver.closeClass();

    ObjectMapper mapper = new ObjectMapper();
    assertEquals(
        "The file must contain all data required to reload the game",
        mapper.readTree(new FileReader("ignore.json")),
        mapper.readTree(new FileReader("testCorrectFileFormatCorrect.json")));
  }
}

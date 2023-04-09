package com.neves6.piazzapanic.tests.gamemechanisms;

import static org.junit.Assert.assertFalse;

import com.neves6.piazzapanic.gamemechanisms.GameSaver;
import com.neves6.piazzapanic.tests.GdxTestRunner;
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
    assertFalse("Entering 0 or less customers is invalid", testSaver.setCustomersRemaining(-1));
  }

  @Test
  public void testDecrementCustomerEndless() {
    testSaver.setCustomersRemaining(-1);
    assertFalse(
        "You cannot decrement customers when running in endless mode.",
        testSaver.decrementCustomers());
  }

  @Test
  public void testDecrementCustomerNotPreset() {
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
  public void testCorrectFileFormat() {
    testSaver.setDifficulty(2);
    testSaver.setPowerUp(true);
    testSaver.setCustomersRemaining(2);
  }
}

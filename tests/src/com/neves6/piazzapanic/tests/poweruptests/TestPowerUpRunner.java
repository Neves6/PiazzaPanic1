package com.neves6.piazzapanic.tests.poweruptests;

import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.gamemechanisms.GameSaver;
import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.people.Chef;
import com.neves6.piazzapanic.powerups.PowerUpRunner;
import com.neves6.piazzapanic.tests.GdxTestRunner;
import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestPowerUpRunner {
  Chef c1 = new Chef("test", 1, 1, 1, 1, 1, false, new Stack<>(), 1);
  Money mo1 = new Money();
  Machine m1 = new Machine("test", "test", "test", 1, false);
  Map<String, Machine> machines = new HashMap<>();
  ArrayList<Chef> testChefs = new ArrayList<>(Arrays.asList(c1));

  @Test
  public void testPrettyPrintNoPowerups() {
    machines.put("test", m1);
    PowerUpRunner testRunner = new PowerUpRunner(testChefs, machines, mo1, new GameSaver("ignore"));
    assertTrue(
        "If no powerup has been activated, text must just contain Current Active Powerups: '",
        testRunner.displayText().equals("Current Active Powerups: \n"));
  }

  @Test
  public void testPrettyPrintPowerups() {
    machines.put("test", m1);
    PowerUpRunner testRunner = new PowerUpRunner(testChefs, machines, mo1, new GameSaver("ignore"));
    testRunner.activateRandomPowerUp();
    String comparisionString = testRunner.displayText();
    assertTrue(
        "Text must just contain ' Current Active Powerups: ' even if a powerup is activated.",
        testRunner.displayText().startsWith("Current Active Powerups: \n"));
    assertTrue(
        "Once a powerup is active, it should show the text for the powerup activated.",
        comparisionString.contains("1/2 price machines for 30 seconds")
            || comparisionString.contains("1/2 machine time for 30 seconds")
            || comparisionString.contains("no wait on a machine")
            || comparisionString.contains("time freeze for 30 seconds")
            || comparisionString.contains("double money for 30 seconds"));
  }

  @Test
  public void testGetCorrectDelta() {
    machines.put("test", m1);
    PowerUpRunner testRunner = new PowerUpRunner(testChefs, machines, mo1, new GameSaver("ignore"));
    testRunner.activateRandomPowerUp();
    assertTrue(
        "Delta must be 0 or the parameter entered",
        testRunner.updateValues(1f) == 0f || testRunner.updateValues(1f) == 1f);
  }


}

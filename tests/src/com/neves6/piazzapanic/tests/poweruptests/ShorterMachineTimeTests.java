package com.neves6.piazzapanic.tests.poweruptests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.powerups.ShorterMachineTime;
import com.neves6.piazzapanic.tests.GdxTestRunner;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class ShorterMachineTimeTests {
  ShorterMachineTime testMachineTime =
      new ShorterMachineTime(1L, "1/2 machine time for 30 seconds");

  @Test
  public void testConstructor() {
    assertEquals(
        "Constructor must not modify effect time", 1L, (long) testMachineTime.getEffectTime());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyPowerUpEmpty() {
    // "Attempting to apply powerup to empty list of machine should return IllegalArgumentExcpetion"
    testMachineTime.applyPowerUp(new HashMap<>());
  }

  Machine m1 = new Machine("test", "test", "test", 5, false);
  Machine m2 = new Machine("test", "test", "test", 10, true);
  Map<String, Machine> testMachines = new HashMap<>();

  @Test
  public void testApplyPowerUpUnattained() {
    testMachines.put("m1", m1);
    testMachines.put("m2", m2);
    testMachines = testMachineTime.applyPowerUp(testMachines);

    assertTrue(
        "Applying shorter time powerup should only be done when powerup is active",
        m1.getProcessingTime() == 5 && m2.getProcessingTime() == 10);
  }

  @Test
  public void testApplyPowerUpAttained() {
    testMachines.put("m1", m1);
    testMachines.put("m2", m2);
    testMachineTime.acquirePowerUp();
    testMachines = testMachineTime.applyPowerUp(testMachines);

    assertTrue(
        "Applying shorter time powerup should half the processing time",
        m1.getProcessingTime() == 2.5 && m2.getProcessingTime() == 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEndPowerUpEmpty() {
    testMachineTime.endPowerUp(new HashMap<>());
  }

  @Test
  public void testEndPowerUpUnattained() {
    testMachines.put("m1", m1);
    testMachines.put("m2", m2);
    testMachines = testMachineTime.endPowerUp(testMachines);

    assertTrue(
        "Ending a power-up for inactive activation should return the machines without modification",
        m1.getProcessingTime() == 5 && m2.getProcessingTime() == 10);
  }

  @Test
  public void testEndPowerUpAttained() throws InterruptedException {
    testMachines.put("m1", m1);
    testMachines.put("m2", m2);
    testMachineTime.acquirePowerUp();

    testMachines = testMachineTime.applyPowerUp(testMachines);
    TimeUnit.MILLISECONDS.sleep(5);

    testMachines = testMachineTime.endPowerUp(testMachines);

    assertTrue(
        "Ending a power-up for active activation should return the machines to the original times",
        m1.getProcessingTime() == 5 && m2.getProcessingTime() == 10);
  }
}

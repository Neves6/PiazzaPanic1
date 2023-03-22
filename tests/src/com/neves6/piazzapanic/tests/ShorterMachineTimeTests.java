package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.powerups.ShorterMachineTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class ShorterMachineTimeTests {
  ShorterMachineTime testMachineTime = new ShorterMachineTime(1L);

  @Test
  public void testConstructor() {
    assertTrue("Constructor must not modify effect time", testMachineTime.getEffectTime() == 1L);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyPowerUpEmpty() {
    //"Attempting to apply powerup to empty list of machine should return IllegalArgumentExcpetion"
    testMachineTime.applyPowerUp(new ArrayList<>());
  }

  Machine m1 = new Machine("test", "test", "test", 5, false);
  Machine m2 = new Machine("test", "test", "test", 10, true);
  ArrayList<Machine> testMachines = new ArrayList<>();

  @Test
  public void testApplyPowerUpUnattained() {
    testMachines.add(m1);
    testMachines.add(m2);
    testMachines = testMachineTime.applyPowerUp(testMachines);

    Machine updatedm1 = testMachines.get(0);
    Machine updatedm2 = testMachines.get(1);

    assertTrue("Applying shorter time powerup should only be done when powerup is active",
            updatedm1.getProcessingTime() == 5 && updatedm2.getProcessingTime() == 10);
  }

  @Test
  public void testApplyPowerUpAttained() {
    testMachines.add(m1);
    testMachines.add(m2);
    testMachineTime.aquirePowerUp();
    testMachines = testMachineTime.applyPowerUp(testMachines);

    Machine updatedm1 = testMachines.get(0);
    Machine updatedm2 = testMachines.get(1);

    assertTrue("Applying shorter time powerup should half the processing time",
            updatedm1.getProcessingTime() == 2.5 && updatedm2.getProcessingTime() == 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEndPowerUpEmpty() {
    testMachineTime.endPowerUp(new ArrayList<>());
  }

  @Test
  public void testEndPowerUpUnattained() {
    testMachines.add(m1);
    testMachines.add(m2);
    testMachines = testMachineTime.endPowerUp(testMachines);

    Machine updatedm1 = testMachines.get(0);
    Machine updatedm2 = testMachines.get(1);

    assertTrue("Ending a power-up for inactive activation should return the machines without modification"
            ,updatedm1.getProcessingTime() == 5 && updatedm2.getProcessingTime() == 10);
  }

  @Test
  public void testEndPowerUpAttained() throws InterruptedException {
    testMachines.add(m1);
    testMachines.add(m2);
    testMachineTime.aquirePowerUp();

    testMachines = testMachineTime.applyPowerUp(testMachines);
    TimeUnit.MILLISECONDS.sleep(5);

    testMachines = testMachineTime.endPowerUp(testMachines);

    Machine updatedm1 = testMachines.get(0);
    Machine updatedm2 = testMachines.get(1);

    assertTrue("Ending a power-up for active activation should return the machines to the original times",
            updatedm1.getProcessingTime() == 5 && updatedm2.getProcessingTime() == 10);
  }
}

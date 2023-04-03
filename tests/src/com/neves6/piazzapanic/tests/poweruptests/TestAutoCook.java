package com.neves6.piazzapanic.tests.poweruptests;

import static org.junit.Assert.*;

import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.people.Chef;
import com.neves6.piazzapanic.powerups.AutoCook;
import com.neves6.piazzapanic.tests.GdxTestRunner;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestAutoCook {
  AutoCook testAutoCook = new AutoCook(1L, "no wait on a machine");
  AutoCook testAutoCookII = new AutoCook(30000L, "no wait on a machine");

  Machine m1 = new Machine("test", "test", "testProcessed", 5, false);
  Machine m2 = new Machine("test", "test", "test", 10, true);
  Map<String, Machine> testMachines = new HashMap<>();

  @Test
  public void testApplyPowerUpNoRunningMachine() {
    testMachines.put("m1", m1);
    testMachines.put("m2", m2);
    testAutoCook.acquirePowerUp();
    testAutoCook.applyPowerUp(testMachines);
    assertTrue(
        "If no machine is running, the game should save it till this occurs",
        testAutoCook.getAcquiredStatus());
    assertTrue(
        "If no machine is running, the start time should be set to zero",
        testAutoCook.getAcquiredStatus());
  }

  @Test
  public void testApplyPowerUpCompleteMachine() {
    Chef t1 = new Chef("Test 1", 1, 1, 1, 1, 1, false, new Stack<>(), 1);
    t1.addToInventory("test");
    m1.process(t1, new Money());
    testMachines.put("m1", m1);
    testMachines.put("m2", m2);
    testAutoCookII.acquirePowerUp();
    testAutoCookII.applyPowerUp(testMachines);
    m1.attemptGetOutput();
    assertTrue(
        "If a machine is running, when this powerup is attained, it should be used immediately",
        t1.getInventory().peek() == "testProcessed");
    assertFalse("Once a powerup is used it must be disabled.", testAutoCookII.getAcquiredStatus());
  }

  @Test
  public void testApplyPowerUpRanOutOfTime() throws InterruptedException {
    Chef t1 = new Chef("Test 1", 1, 1, 1, 1, 1, false, new Stack<>(), 1);
    t1.addToInventory("test");
    m1.process(t1, new Money());
    testMachines.put("m1", m1);
    testMachines.put("m2", m2);
    testAutoCook.acquirePowerUp();
    TimeUnit.MILLISECONDS.sleep(5);
    testAutoCook.applyPowerUp(testMachines);
    assertFalse(
        "Once the timer is up, it must be disposed even if the powerup has not been used.",
        testAutoCook.getAcquiredStatus());
  }

  @Test
  public void testApplyPowerUpInactive() {
    Chef t1 = new Chef("Test 1", 1, 1, 1, 1, 1, false, new Stack<>(), 1);
    t1.addToInventory("test");
    m1.process(t1, new Money());
    testMachines.put("m1", m1);
    testMachines.put("m2", m2);
    testAutoCook.applyPowerUp(testMachines);
    assertFalse(
        "If the powerup is not attained, it should not be applied.",
        testAutoCook.getAcquiredStatus());
  }
}

package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.people.Chef;
import com.neves6.piazzapanic.powerups.AutoCook;
import java.util.ArrayList;
import java.util.Stack;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestAutoCook {
  AutoCook testAutoCook = new AutoCook(1L);

  @Test
  public void testConstructor() {
    assertTrue(
        "The constructor should not modify the effect time", testAutoCook.getEffectTime() == 1L);
  }

  Machine m1 = new Machine("test", "test", "test", 5, false);
  Machine m2 = new Machine("test", "test", "test", 10, true);
  ArrayList<Machine> testMachines = new ArrayList<>();

  @Test
  public void testApplyPowerUpNoRunningMachine() {
    testMachines.add(m1);
    testMachines.add(m2);
    testAutoCook.aquirePowerUp();
    testAutoCook.applyPowerUp(testMachines);
    assertTrue(
        "If no machine is running, the game should save it till this occurs",
        testAutoCook.getAquiredStatus() == true);
  }

  @Test
  public void testApplyPowerUpCompleteMachine() {
    Chef t1 = new Chef("Test 1", 1, 1, 1, 1, 1, false, new Stack<>(), 1);
    t1.addToInventory("test");
    m1.process(t1, new Money());
    testMachines.add(m1);
    testAutoCook.aquirePowerUp();
    testAutoCook.applyPowerUp(testMachines);
    assertTrue(
        "If a machine is running, when this powerup is attained, it should be used immediately",
        testAutoCook.getAquiredStatus() == false);
  }
}

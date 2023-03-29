package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.people.Chef;
import com.neves6.piazzapanic.powerups.DoubleSpeed;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestDoubleSpeed {
  DoubleSpeed testDoubleSpeed = new DoubleSpeed(1L, "x2 Speed");

  @Test
  public void testConstructor() {
    assertEquals(
        "Constructor must not edit effect time", 1L, (long) testDoubleSpeed.getEffectTime());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyPowerUpEmpty() {
    testDoubleSpeed.applyPowerUp(new ArrayList<>());
  }

  Chef t1 = new Chef("Test 1", 1, 1, 1, 1, 1, false, new Stack<>(), 1);
  Chef t2 = new Chef("Test 2", 1, 1, 1, 1, 1, false, new Stack<>(), 1);
  ArrayList<Chef> testChefs = new ArrayList<>();

  @Test
  public void testApplyPowerUpUnattained() {
    testChefs.add(t1);
    testChefs.add(t2);
    testChefs = testDoubleSpeed.applyPowerUp(testChefs);

    Chef updatedt1 = testChefs.get(0);
    Chef updatedt2 = testChefs.get(1);

    assertTrue(
        "Apply the powerup while unattained should not have an effect on speed",
        updatedt1.getSpeed() == 1 && updatedt2.getSpeed() == 1);
  }

  @Test
  public void testApplyPowerUpNormal() {
    testChefs.add(t1);
    testChefs.add(t2);
    testDoubleSpeed.aquirePowerUp();
    testChefs = testDoubleSpeed.applyPowerUp(testChefs);

    Chef updatedt1 = testChefs.get(0);
    Chef updatedt2 = testChefs.get(1);

    assertTrue(
        "Apply the powerup while attained should double the speed",
        updatedt1.getSpeed() == 2 && updatedt2.getSpeed() == 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEndPowerUpEmpty() {
    testDoubleSpeed.endPowerUp(new ArrayList<>());
  }

  @Test
  public void testEndPowerUpUnattained() {
    testChefs.add(t1);
    testChefs.add(t2);
    testChefs = testDoubleSpeed.endPowerUp(testChefs);

    Chef updatedt1 = testChefs.get(0);
    Chef updatedt2 = testChefs.get(1);

    assertTrue(
        "Ending an unattained powerup should have no effect on speed",
        updatedt1.getSpeed() == 1 && updatedt2.getSpeed() == 1);
  }

  @Test
  public void testEndPowerUpNormal() throws InterruptedException {
    testChefs.add(t1);
    testChefs.add(t2);
    testDoubleSpeed.aquirePowerUp();
    testChefs = testDoubleSpeed.applyPowerUp(testChefs);

    TimeUnit.MILLISECONDS.sleep(5);

    testChefs = testDoubleSpeed.endPowerUp(testChefs);

    Chef updatedt1 = testChefs.get(0);
    Chef updatedt2 = testChefs.get(1);

    assertTrue(
        "Ending an unattained powerup should return speed to normal values",
        updatedt1.getSpeed() == 1 && updatedt2.getSpeed() == 1);
  }
}

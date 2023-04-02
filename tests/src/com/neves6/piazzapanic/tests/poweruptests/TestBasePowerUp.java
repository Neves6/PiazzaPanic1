package com.neves6.piazzapanic.tests.poweruptests;

import com.neves6.piazzapanic.powerups.BasePowerUp;
import com.neves6.piazzapanic.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class TestBasePowerUp {
  BasePowerUp testPowerUp = new BasePowerUp(1L, "Test Powerup");

  @Test
  public void testConstructorI() {
    assertFalse("Constructor should not modify acquired status", testPowerUp.getAcquiredStatus());
  }

  @Test
  public void testConstructorII() {
    assertEquals("Constructor should not modify start time", 0L, (long) testPowerUp.getStartTime());
  }

  @Test
  public void testConstructorIII() {
    assertEquals(
        "Constructor should not modify effect time", 1L, (long) testPowerUp.getEffectTime());
  }

  @Test
  public void validActivation() {
    testPowerUp.setStartTime();
    assertTrue(
        "Start time should be set if power-up is attained.", testPowerUp.getStartTime() != 0L);
  }

  @Test
  public void validEndTime() throws InterruptedException {
    testPowerUp.acquirePowerUp();
    testPowerUp.setStartTime();
    TimeUnit.MILLISECONDS.sleep(5);
    assertTrue("Power-up should be deactivated once the time is up", testPowerUp.endTime());
  }

  BasePowerUp testPowerUpII = new BasePowerUp(5000L, "Test powerup #2");

  @Test
  public void invalidEndTime() {
    assertFalse(
        "End time should not change anything if it was never activated ", testPowerUpII.endTime());
  }

  @Test
  public void inactivePrettyPrint(){
    assertEquals("Inactive powerup should not return a message", "", testPowerUp.prettyPrint());
  }

  @Test
  public void activePrettyPrint(){
    testPowerUpII.acquirePowerUp();
    assertFalse("String must contain the name of the powerup", testPowerUpII.prettyPrint() == "");
    assertTrue(testPowerUpII.prettyPrint().contains("Test powerup #2:"));
  }
}

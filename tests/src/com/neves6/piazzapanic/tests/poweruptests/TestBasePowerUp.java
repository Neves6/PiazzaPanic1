package com.neves6.piazzapanic.tests.poweruptests;

import static org.junit.Assert.*;

import com.neves6.piazzapanic.powerups.BasePowerUp;
import com.neves6.piazzapanic.tests.GdxTestRunner;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestBasePowerUp {
  BasePowerUp testPowerUp = new BasePowerUp(1L, "Test Powerup");

  @Test
  public void testConstructor() {
    assertEquals("Constructor should not modify start time", 0L, (long) testPowerUp.getStartTime());
  }

  @Test
  public void testValidActivation() {
    testPowerUp.setStartTime();
    assertTrue(
        "Start time should be set if power-up is attained.", testPowerUp.getStartTime() != 0L);
  }

  @Test
  public void testValidEndTime() throws InterruptedException {
    testPowerUp.acquirePowerUp();
    testPowerUp.setStartTime();
    TimeUnit.MILLISECONDS.sleep(5);
    assertTrue("Power-up should be deactivated once the time is up", testPowerUp.endTime());
  }

  BasePowerUp testPowerUpII = new BasePowerUp(5000L, "Test powerup #2");

  @Test
  public void testInvalidEndTime() {
    assertFalse(
        "End time should not change anything if it was never activated ", testPowerUpII.endTime());
  }

  @Test
  public void testInactivePrettyPrint() {
    assertEquals("Inactive powerup should not return a message", "", testPowerUp.prettyPrint());
  }

  @Test
  public void testActivePrettyPrint() {
    testPowerUpII.acquirePowerUp();
    assertTrue(
        "String must contain the name of the powerup",
        testPowerUpII.prettyPrint().contains("Test powerup #2:"));
  }
}

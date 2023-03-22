package com.neves6.piazzapanic.tests;

import static org.junit.Assert.*;

import com.neves6.piazzapanic.powerups.BasePowerUp;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestBasePowerUp {
  BasePowerUp testPowerUp = new BasePowerUp(1L);

  @Test
  public void testConstructorI() {
    assertFalse("Constructor should not modify acquired status", testPowerUp.getAquiredStatus());
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
  public void invalidActivation() {
    System.out.println(testPowerUp.getStartTime());
    testPowerUp.setStartTime();
    assertEquals(
        "Start time should not be set if power-up is attained.",
        0L,
        (long) testPowerUp.getStartTime());
  }

  @Test
  public void validActivation() {
    testPowerUp.aquirePowerUp();
    testPowerUp.setStartTime();
    assertTrue(
        "Start time should be set if power-up is attained.", testPowerUp.getStartTime() != 0L);
  }

  @Test
  public void validEndTime() throws InterruptedException {
    testPowerUp.aquirePowerUp();
    testPowerUp.setStartTime();
    TimeUnit.MILLISECONDS.sleep(5);
    assertTrue("Power-up should be deactivated once the time is up", testPowerUp.endTime());
  }

  BasePowerUp testPowerUpII = new BasePowerUp(5000L);

  @Test
  public void invalidEndTime() {
    assertFalse(
        "End time should not change anything if it was never activated ", testPowerUpII.endTime());
  }
}

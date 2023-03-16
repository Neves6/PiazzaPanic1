package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.powerups.BasePowerUp;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestBasePowerUp {
  BasePowerUp testPowerUp = new BasePowerUp(1L);

  @Test
  public void testConstructorI() {
    assertTrue(testPowerUp.getAquiredStatus() == false);
  }

  @Test
  public void testConstructorII() {
    assertTrue(testPowerUp.getStartTime() == 0L);
  }

  @Test
  public void testConstructorIII() {
    assertTrue(testPowerUp.getEffectTime() == 1L);
  }

  @Test
  public void invalidActivation() {
    System.out.println(testPowerUp.getStartTime());
    testPowerUp.setStartTime();
    assertTrue(testPowerUp.getStartTime() == 0L);
  }

  @Test
  public void validActivation() {
    testPowerUp.aquirePowerUp();
    testPowerUp.setStartTime();
    assertTrue(testPowerUp.getStartTime() != 0L);
  }

  @Test
  public void validEndTime() throws InterruptedException {
    testPowerUp.aquirePowerUp();
    testPowerUp.setStartTime();
    TimeUnit.MILLISECONDS.sleep(5);
    assertTrue(testPowerUp.endTime() == true);
  }

  BasePowerUp testPowerUpII = new BasePowerUp(5000L);

  @Test
  public void invalidEndTime() {
    assertTrue(testPowerUpII.endTime() == false);
  }
}

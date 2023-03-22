package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertEquals;

import com.neves6.piazzapanic.powerups.TimeFreeze;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestTimeFreeze {
  TimeFreeze testFreeze = new TimeFreeze(1L);

  @Test
  public void testConstructor() {
    assertEquals("Constructor must not edit effect time.", 1L, (long) testFreeze.getEffectTime());
  }

  @Test
  public void testUnactivatedFreeze() {
    assertEquals("If power up isn't acquired, delta stays at 1", 1, testFreeze.getDelta(1), 0.0);
  }

  @Test
  public void testActivatedFreeze() {
    testFreeze.aquirePowerUp();
    assertEquals("If power up is acquired delta must be 0", 0, testFreeze.getDelta(1), 0.0);
  }
}

package com.neves6.piazzapanic.tests.poweruptests;

import static org.junit.Assert.assertEquals;

import com.neves6.piazzapanic.powerups.TimeFreeze;
import com.neves6.piazzapanic.tests.GdxTestRunner;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestTimeFreeze {
  TimeFreeze testFreeze = new TimeFreeze(1L, "time freeze for test");

  @Test
  public void testUnactivatedFreeze() {
    assertEquals("If power up isn't acquired, delta stays at 1", 1, testFreeze.getDelta(1), 0.0);
  }

  @Test
  public void testActivatedFreeze() {
    testFreeze.acquirePowerUp();
    assertEquals("If power up is acquired delta must be 0", 0, testFreeze.getDelta(1), 0.0);
  }

  @Test
  public void testActivatedFreezeExpired() throws InterruptedException {
    testFreeze.acquirePowerUp();
    TimeUnit.MILLISECONDS.sleep(5);
    assertEquals("If power up is acquired delta must be 0", 1.0, testFreeze.getDelta(1), 0.0);
  }
}

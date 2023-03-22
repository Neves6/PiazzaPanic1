package com.neves6.piazzapanic.tests;

import com.neves6.piazzapanic.staff.BaseStaff;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(GdxTestRunner.class)
public class TestBaseStaff {
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDimensions() {
    // Matching dimensions for both arrays are required.
    new BaseStaff(new ArrayList<>(), new ArrayList<>(Arrays.asList(1)));
  }

  @Test
  public void testValidTransitions() {
    BaseStaff test =
        new BaseStaff(new ArrayList<>(Arrays.asList(1, 2)), new ArrayList<>(Arrays.asList(1, 3)));
    assertEquals("X and Y coordinates should be mapped onto each other", test.getCoordInSeq(), new ArrayList<>(Arrays.asList(1, 1)));
  }

  @Test
  public void testStopTransitions() throws InterruptedException {
    BaseStaff test =
        new BaseStaff(new ArrayList<>(Arrays.asList(1, 2)), new ArrayList<>(Arrays.asList(1, 3)));
    test.getCoordInSeq();
    TimeUnit.MILLISECONDS.sleep(500);
    test.getCoordInSeq();
    TimeUnit.MILLISECONDS.sleep(500);
    assertFalse("Once the mapping of the arrays has been iterated over, collect should be set to false.", test.getCollect());
  }
}

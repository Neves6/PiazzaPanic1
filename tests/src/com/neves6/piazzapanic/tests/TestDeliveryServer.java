package com.neves6.piazzapanic.tests;

import com.neves6.piazzapanic.staff.IngredientsStaff;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestDeliveryServer {
  IngredientsStaff test =
      new IngredientsStaff(
          new ArrayList<>(Arrays.asList(7, 6, 5, 4, 3, 2, 1, 2, 2, 2)),
          (new ArrayList<>(Arrays.asList(9, 9, 9, 9, 9, 9, 9, 9, 8, 9))));

  @Test
  public void testIncrements() {
    test.getCoordInSeq();
    test.getCoordInSeq();
    test.getCoordInSeq();
  }
}

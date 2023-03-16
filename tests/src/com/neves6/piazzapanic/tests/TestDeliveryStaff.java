package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.staff.DeliveryStaff;
import java.util.ArrayList;
import java.util.Stack;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestDeliveryStaff {
  DeliveryStaff test = new DeliveryStaff(new ArrayList<>(), new ArrayList<>());

  @Test
  public void testCollect() {
    test.collectItem("pizza");
    assertTrue(test.getCollect());
  }

  @Test
  public void testPickup() {
    test.collectItem("pizza");
    Stack<Object> testStack = new Stack<>();
    testStack.add("pizza");
    assertTrue(test.getItems().equals(testStack));
  }
}

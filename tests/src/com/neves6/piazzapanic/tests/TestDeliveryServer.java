package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.staff.DeliveryStaff;
import java.util.ArrayList;
import java.util.Stack;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestDeliveryServer {
  DeliveryStaff test = new DeliveryStaff(new ArrayList<>(), new ArrayList<>());

  @Test
  public void testCollect() {
    test.collectItem("pizza");
    assertTrue(
        "Once an item is collected, the collected flag must be set to true", test.getCollect());
  }

  @Test
  public void testPickup() {
    test.collectItem("pizza");
    Stack<Object> testStack = new Stack<>();
    testStack.add("pizza");
    assertEquals(
        "Once an item is collected, it must be pushed onto the stack", test.getItems(), testStack);
  }
}

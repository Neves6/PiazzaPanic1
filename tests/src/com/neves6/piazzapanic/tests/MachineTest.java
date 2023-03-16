package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.Chef;
import com.neves6.piazzapanic.Machine;
import com.neves6.piazzapanic.Money;
import java.util.Stack;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class MachineTest {

  @Test
  public void testProcess() {
    // test chef inventory and if sticky
    Stack<String> inventory = new Stack<>();
    Chef testChef = new Chef("Xavier", 0, 0, 5, 5, 5, false, inventory, 1);
    Machine testMachine = new Machine("Grill", "", "Cooked Patty", 0, true);
    testMachine.process(testChef, new Money());

    Stack<String> inventory2 = new Stack<>();
    inventory2.push("Burger");
    inventory2.push("Lettuce");
    Chef testChef2 = new Chef("Bill", 0, 0, 5, 5, 5, false, inventory2, 1);
    Machine testMachine2 = new Machine("Board", "Lettuce", "Chopped Lettuce", 0, true);
    testMachine2.process(testChef2, new Money());
    assertTrue(
        testChef.getInventory().peek() == "Cooked Patty"
            && testChef2.getIsStickied() == true
            && testChef2.getInventory().peek() == "Burger");
  }

  @Test
  public void testAttemptGetOutput() {
    Stack<String> inventory = new Stack<>();
    inventory.push("Patty");
    Chef testChef = new Chef("Xavier", 0, 0, 5, 5, 5, false, inventory, 1);
    Machine testMachine = new Machine("Grill", "Patty", "Cooked Patty", 0, true);
    testMachine.process(testChef, new Money());
    testMachine.attemptGetOutput();
    assertTrue(
        testChef.getInventory().peek() == "Cooked Patty" && testChef.getIsStickied() == false);
  }

  @Test
  public void testIncrementRuntime() {
    // this.runtime += delta
    float delta = 5;
    Machine testMachine = new Machine("Grill", "Patty", "Cooked Patty", 5, true);
    testMachine.incrementRuntime(delta);
    assertTrue(testMachine.getRuntime() == 5);
  }
}

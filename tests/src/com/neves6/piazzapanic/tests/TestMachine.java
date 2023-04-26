package com.neves6.piazzapanic.tests;

import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.people.Chef;
import java.util.Objects;
import java.util.Stack;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class TestMachine {

  @Test
  public void testProcessValid() {
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
        "Processing an item must stick chef",
        Objects.equals(testChef.getInventory().peek(), "Cooked Patty")
            && testChef2.getIsStickied()
            && Objects.equals(testChef2.getInventory().peek(), "Burger"));
  }

  @Test
  public void testProcessInvalid() {
    // test chef inventory and if sticky
    Stack<String> inventory = new Stack<>();
    Chef testChef = new Chef("Xavier", 0, 0, 5, 5, 5, false, inventory, 1);
    Machine testMachine = new Machine("Grill", "patty", "Cooked Patty", 0, true);
    Money currency = new Money();
    testChef.addToInventory("test");
    currency.addGroup("Grill", 100f);
    testMachine.process(testChef, currency);
    assertEquals("Chef should not interact with locked machine", "test", testChef.getInventory().peek());
    currency.unlockMachine(testMachine.getUnlockID());
    assertFalse("Chef should not interact with machine if incorrect item is held", testChef.getIsStickied());
  }

  @Test
  public void testAttemptGetOutput() {
    Stack<String> inventory = new Stack<>();
    inventory.push("Patty");
    Chef testChef = new Chef("Xavier", 0, 0, 5, 5, 5, false, inventory, 1);
    Machine testMachine = new Machine("Grill", "Patty", "Cooked Patty", 0, true);
    testMachine.process(testChef, new Money());
    testMachine.attemptCompleteAction();
    testMachine.attemptGetOutput();
    assertTrue(
        "Input must be popped from stack and output pushed to stack",
        Objects.equals(testChef.getInventory().peek(), "Cooked Patty")
            && !testChef.getIsStickied());
  }

  @Test
  public void testIncrementRuntime() {
    // this.runtime += delta
    float delta = 5;
    Machine testMachine = new Machine("Grill", "Patty", "Cooked Patty", 5, true);
    testMachine.incrementRuntime(delta);
    assertEquals(
        "Increment runtime should not be changed by any method apart from alterRuntime()",
        5,
        testMachine.getRuntime(),
        0.0);
  }

  @Test
  public void testProcessStaffInteraction() {
    Stack<String> inventory = new Stack<>();
    inventory.push("Patty");
    Chef testChef = new Chef("Xavier", 0, 0, 5, 5, 5, false, inventory, 1);
    Money testMoney = new Money();
    testMoney.addGroup("test", 20f);
    Machine testMachine = new Machine("Grill", "Patty", "Cooked Patty", 5, true, "test");
    testMoney.incrementBalance();
    testMoney.unlockMachine("test");
    testMachine.processStaffInteraction(testChef, testMoney);
    assertTrue(
        "Unlock machine with unlockID and if unlocked add machine output to chefs inventory",
        testChef.getInventory().pop().equals("Cooked Patty"));
  }

  @Test
  public void testRuinedOutput() {
    Stack<String> inventory = new Stack<>();
    inventory.push("Patty");
    Chef testChef = new Chef("Xavier", 0, 0, 5, 5, 5, false, inventory, 1);
    Machine testMachine = new Machine("Grill", "Patty", "Cooked Patty", 1, true);
    testMachine.process(testChef, new Money());
    testMachine.incrementRuntime(5);
    testMachine.attemptGetOutput();
    assertTrue(
        "No button pressed to ruin the burger",
        Objects.equals(testChef.getInventory().peek(), "ruined Cooked Patty")
            && !testChef.getIsStickied());
  }
}

package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.people.Chef;
import java.util.Stack;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class ChefTests {
  @Test
  public void testChefConstructorValid() {
    int x = 4;
    int y = 20;
    String name = "@<-little monkey :)";
    int chopSpeed = 0;
    int frySpeed = 1;
    int bakeSpeed = 5;
    boolean isStickied = true;
    Stack<String> inv = new Stack<>();
    inv.push("i");
    inv.push("t");
    inv.push("e");
    inv.push("m");

    int texSet = 1;
    String direction = "down";
    Chef testChef = new Chef(name, x, y, chopSpeed, frySpeed, bakeSpeed, isStickied, inv, texSet);
    assertTrue(
        "Chef Constructor Valid :D",
        testChef.getxCoord() == x
            && testChef.getxCoord() == 4
            && testChef.getyCoord() == y
            && testChef.getyCoord() == 20
            && testChef.getName().equals(name)
            && testChef.getName().equals("@<-little monkey :)")
            && testChef.getIsStickied() == isStickied
            && testChef.getInventory() == inv
        /** testChef.getTxNow()==txDown TEXTURESET ETC?? */
        );
  }

  @Test // NOT SURE (think needs chef construction?)
  public void testChefSetMachine() {
    Machine testMachine = new Machine("Fryer", "Uncooked Chips", "Cooked Chips", 0.5F, true);
    Stack<String> inv = new Stack<>();
    inv.push("i");
    inv.push("t");
    inv.push("e");
    inv.push("m");
    Chef testChef =
        new Chef(
            "Bob",
            6,
            9,
            4,
            2,
            0,
            true,
            inv,
            /** texture set int */
            1);
    testChef.setMachineInteractingWith(testMachine);
    assertTrue("Set Machine Works :D", testChef.getMachineInteractingWith() == testMachine);
  }

  @Test // NOT SURE (same again)
  public void testChefSetSticky() {
    Stack<String> inv = new Stack<>();
    inv.push("i");
    inv.push("t");
    inv.push("e");
    inv.push("m");
    Chef testChef =
        new Chef(
            "Bob",
            6,
            9,
            4,
            2,
            0,
            true,
            inv,
            /** texture set int */
            1);
    boolean stickiness = true;
    testChef.setIsStickied(stickiness);
    assertTrue(
        "Set Sticky Works :D",
        testChef.getIsStickied() == true && testChef.getIsStickied() == stickiness);
  }

  @Test
  public void testInvAdd() {
    Stack<String> inv = new Stack<>();
    inv.push("i");
    inv.push("t");
    inv.push("e");
    inv.push("m");
    Chef testChef =
        new Chef(
            "Bob",
            6,
            9,
            4,
            2,
            0,
            true,
            inv,
            /** texture set int */
            1);
    Stack<String> testInvA = testChef.getInventory();
    testChef.addToInventory("s");
    testInvA.add("s");
    assertTrue("Add to Inventory Works :D", testInvA == testChef.getInventory());
  }

  @Test
  public void testInvPop() {
    Stack<String> inv = new Stack<>();
    inv.push("i");
    inv.push("t");
    inv.push("e");
    inv.push("m");
    Chef testChef =
        new Chef(
            "Bob",
            6,
            9,
            4,
            2,
            0,
            true,
            inv,
            /** texture set int */
            1);
    Stack<String> testInvP = testChef.getInventory();
    testChef.removeTopFromInventory();
    testInvP.pop();
    assertTrue("Pop from Inventory Works :D", testChef.getInventory() == testInvP);
  }

  @Test
  public void testSetFacing() {
    Stack<String> inv = new Stack<>();
    inv.push("i");
    inv.push("t");
    inv.push("e");
    inv.push("m");
    Chef testChef =
        new Chef(
            "Bob",
            6,
            9,
            4,
            2,
            0,
            true,
            inv,
            /** texture set int */
            1);
    String facing =
        "down"; // i think should be testChef.facing="" //on further reading might not need it at
    // all, textures are confusing
    testChef.setFacing("up");
    assertTrue("Set Facing Works :D", testChef.getFacing() == "up");
  }
}

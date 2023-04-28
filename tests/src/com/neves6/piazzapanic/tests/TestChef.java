package com.neves6.piazzapanic.tests;

import static org.junit.Assert.*;

import com.badlogic.gdx.graphics.Texture;
import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.people.Chef;
import java.util.Stack;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestChef {
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
    Chef testChef = new Chef(name, x, y, chopSpeed, frySpeed, bakeSpeed, isStickied, inv, texSet);
    assertTrue(
        "Chef object cannot be instantiated",
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
    assertSame(
        "Machine being interacted with is not accurate",
        testChef.getMachineInteractingWith(),
        testMachine);
  }

  @Test
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
            false,
            inv,
            /** texture set int */
            1);
    boolean stickiness = true;
    testChef.setIsStickied(stickiness);
    assertTrue(
        "Chef has not been stuck",
        testChef.getIsStickied() && (testChef.getIsStickied() == stickiness));
    stickiness = false;
    testChef.setIsStickied(stickiness);
    assertTrue(
        "Chef has not been unstuck",
        !testChef.getIsStickied() && (testChef.getIsStickied() == stickiness));
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
    assertSame("Item was not added to chef inventory", testInvA, testChef.getInventory());
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
    assertSame("Item was not removed from chef inventory", testInvP, testChef.getInventory());
  }

  @Test
  public void testSetFacingValid() {
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

    testChef.setFacing("up");
    assertSame("Direction chef is facing is not accurate", "up", testChef.getFacing());
    testChef.setFacing("down");
    assertEquals("Chef down facing texture is not correct", "down", testChef.getFacing());
    testChef.setFacing("left");
    assertEquals("Chef left facing texture is not correct", "left", testChef.getFacing());
    testChef.setFacing("right");
    assertEquals("Chef right facing texture is not correct", "right", testChef.getFacing());
  }

  @Test
  public void testSetFacingInvalid() {
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

    testChef.setFacing("left");
    testChef.setFacing("test");
    assertSame("Chef facing value should not have been changed", "left", testChef.getFacing());
  }

  @Test
  public void testTxtNowValid() {
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
    Texture txUp = new Texture("people/chef1up.png");
    Texture txDown = new Texture("people/chef1down.png");
    Texture txLeft = new Texture("people/chef1left.png");
    Texture txRight = new Texture("people/chef1right.png");

    testChef.setFacing("up");
    assertEquals(
        "Chef up facing texture is not correct", txUp.toString(), testChef.getTxNow().toString());
    testChef.setFacing("down");
    assertEquals(
        "Chef down facing texture is not correct",
        txDown.toString(),
        testChef.getTxNow().toString());
    testChef.setFacing("left");
    assertEquals(
        "Chef left facing texture is not correct",
        txLeft.toString(),
        testChef.getTxNow().toString());
    testChef.setFacing("right");
    assertEquals(
        "Chef right facing texture is not correct",
        txRight.toString(),
        testChef.getTxNow().toString());
  }

  @Test
  public void testTxtNowInvalid() {
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
    Texture txRight = new Texture("people/chef1right.png");

    testChef.setFacing("right");
    testChef.setFacing("test");
    assertEquals(
        "Chef facing texture should not have been changed",
        txRight.toString(),
        testChef.getTxNow().toString());
  }

  @Test
  public void testGetLastMove() {
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

    testChef.setLastMove(100);
    assertTrue("Cannot get the time of the chef's last movement", 100 == testChef.getLastMove());
  }
}

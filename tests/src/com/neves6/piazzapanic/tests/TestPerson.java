package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.people.Person;
import java.util.Objects;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestPerson {
  @Test
  public void testPersonConstructorValid() {
    int x = 20;
    int y = 50;
    String name = "Bob";
    Person testPerson = new Person(name, x, y);
    assertTrue(
        "X, Y and name should be saved according to constructor input.",
        (testPerson.getxCoord() == x)
            && (testPerson.getyCoord() == y)
            && Objects.equals(testPerson.getName(), name));
  }

  @Test
  public void testXSet() {
    int xOld = 0;
    int yOld = 0;
    Person testPerson = new Person("Bob", xOld, yOld);
    testPerson.setxCoord(50);
    assertTrue(
        "Changing the x coordinate using setxCoord should only effect the x variable",
        50 == testPerson.getxCoord() && testPerson.getyCoord() == yOld);
  }

  @Test
  public void testYSet() {
    int xOld = 0;
    int yOld = 0;
    Person testPerson = new Person("Bob", xOld, yOld);
    testPerson.setyCoord(50);
    assertTrue(
        "Changing the y coordinate using setyCoord should only effect the x variable",
        50 == testPerson.getyCoord() && testPerson.getxCoord() == xOld);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testXSetErroneous() {
    // Cannot set negative coordinates.
    Person testPerson = new Person("Bob", 0, 0);
    testPerson.setxCoord(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testYSetErronous() {
    // Cannot set negative coordinates.
    Person testPerson = new Person("Bob", 0, 0);
    testPerson.setyCoord(-1);
  }

  @Test
  public void testMoveRight() {
    Person testPerson = new Person("Bob", 5, 5);
    testPerson.alteryCoord(1);
    assertTrue(
        "Altering y coordinates in the positive direction should move to the right.",
        testPerson.getyCoord() == 6 && testPerson.getxCoord() == 5);
  }

  @Test
  public void testMoveLeft() {
    Person testPerson = new Person("Bob", 5, 5);
    testPerson.alteryCoord(-1);
    assertTrue(
        "Altering y coordinates in the negative direction should move to the left.",
        testPerson.getyCoord() == 4 && testPerson.getxCoord() == 5);
  }

  @Test
  public void testMoveForward() {
    Person testPerson = new Person("Bob", 5, 5);
    testPerson.alterxCoord(1);
    assertTrue(
        "Altering x coordinates in the positive direction should move forwards.",
        testPerson.getxCoord() == 6 && testPerson.getyCoord() == 5);
  }

  @Test
  public void testMoveBackwards() {
    Person testPerson = new Person("Bob", 5, 5);
    testPerson.alterxCoord(-1);
    assertTrue(
        "Altering x coordinates in the negative direction should move backwards.",
        testPerson.getxCoord() == 4 && testPerson.getyCoord() == 5);
  }
}

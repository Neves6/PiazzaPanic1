package com.neves6.piazzapanic.tests;

import com.neves6.piazzapanic.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class PersonTests {
    @Test
    public void testPersonConstructorValid(){
        int x = 20;
        int y = 50;
        String name = "Bob";
        Person testPerson = new Person(name, x, y);
        assertTrue("", testPerson.getxCoord() == x &&
            testPerson.getyCoord() == y &&
            testPerson.getName() == name);
    }

    @Test
    public void testXSet(){
        int xOld = 0;
        int yOld = 0;
        Person testPerson = new Person("Bob", xOld, yOld);
        testPerson.setxCoord(50);
        assertTrue("**",  50 == testPerson.getxCoord() && testPerson.getyCoord() == yOld);
    }

    @Test
    public void testYSet(){
        int xOld = 0;
        int yOld = 0;
        Person testPerson = new Person("Bob", xOld, yOld);
        testPerson.setyCoord(50);
        assertTrue("**",  50 == testPerson.getyCoord() && testPerson.getxCoord() == xOld);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testXSetErroneous(){
        Person testPerson = new Person("Bob", 0, 0);
        testPerson.setxCoord(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testYSetErronous(){
        Person testPerson = new Person("Bob", 0, 0);
        testPerson.setyCoord(-1);
    }

    @Test
    public void testMoveRight(){
        Person testPerson = new Person("Bob", 5, 5);
        testPerson.alteryCoord(1);
        assertTrue("", testPerson.getyCoord() == 6 && testPerson.getxCoord() == 5);
    }

    @Test
    public void testMoveLeft(){
        Person testPerson = new Person("Bob", 5, 5);
        testPerson.alteryCoord(-1);
        assertTrue("", testPerson.getyCoord() == 4 && testPerson.getxCoord() == 5);
    }

    @Test
    public void testMoveForward(){
        Person testPerson = new Person("Bob", 5, 5);
        testPerson.alterxCoord(1);
        assertTrue("", testPerson.getxCoord() == 6 && testPerson.getyCoord() == 5);
    }

    @Test
    public void testMoveBackwards(){
        Person testPerson = new Person("Bob", 5, 5);
        testPerson.alterxCoord(-1);
        assertTrue("", testPerson.getxCoord() == 4 && testPerson.getyCoord() == 5);
    }
}

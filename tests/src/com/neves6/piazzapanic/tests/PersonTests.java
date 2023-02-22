package com.neves6.piazzapanic.tests;

import com.badlogic.gdx.Gdx;
import com.neves6.piazzapanic.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class PersonTests {
    @Test
    public void testXMovement(){
        int xOld = 0;
        int yOld = 0;
        Person testPerson = new Person("Bob", xOld, yOld);
        testPerson.setxCoord(50);
        assertTrue("**",  50 == testPerson.getxCoord() && testPerson.getyCoord() == yOld);
    }
}

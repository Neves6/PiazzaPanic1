package com.neves6.piazzapanic.tests;

import com.neves6.piazzapanic.Chef;
import com.neves6.piazzapanic.Person;
import com.neves6.piazzapanic.Machine;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Stack;

import static org.junit.Assert.assertTrue;

//import static org.junit.api.Assertions.*;
@RunWith(GdxTestRunner.class)
public class MachineTest {

    @Test
    void testProcess() {
        //test chef inventory and if sticky
        Stack<String> inventory = new Stack<String>();
        Chef testChef = new Chef("Xavier", 0, 0, 5,5,5,false,inventory,1);
        Machine testMachine = new Machine("Grill", "Patty", "Cooked Patty", 5, true);
        testMachine.process(testChef);
        //assertTrue(testChef.getInventory().pop() =="Cooked Patty");
        assertTrue(testChef.getIsStickied()==true);
    }

    @Test
    public void testIncrementRuntime() {
        //this.runtime += delta
        float delta = 5;
        Machine testMachine = new Machine("Grill", "Patty", "Cooked Patty", 5, true);
        testMachine.incrementRuntime(delta);
        assertTrue("Runtime", testMachine.getRuntime()==5);
    }
}
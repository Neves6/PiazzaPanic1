package com.neves6.piazzapanic.tests;

import com.neves6.piazzapanic.Chef;
import com.neves6.piazzapanic.Machine;
import com.neves6.piazzapanic.Money;
import com.neves6.piazzapanic.powerups.AutoCook;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class TestAutoCook {
    AutoCook testAutoCook = new AutoCook(1L);
    @Test
    public void testConstructor(){
        assertTrue(testAutoCook.getEffectTime() == 1L);
    }

    Machine m1 = new Machine("test", "test", "test", 5, false);
    Machine m2 = new Machine("test", "test", "test", 10, true);
    ArrayList<Machine> testMachines = new ArrayList<>();

    @Test
    public void testApplyPowerUpNoRunningMachine(){
        testMachines.add(m1);
        testMachines.add(m2);
        testAutoCook.aquirePowerUp();
        testAutoCook.applyPowerUp(testMachines);
        assertTrue(testAutoCook.getAquiredStatus() == true);
    }

    @Test
    public void testApplyPowerUpCompleteMachine(){
        Chef t1 = new Chef("Test 1", 1, 1, 1, 1, 1, false,
                new Stack<>(), 1);
        t1.addToInventory("test");
        m1.process(t1);
        testMachines.add(m1);
        testAutoCook.aquirePowerUp();
        testAutoCook.applyPowerUp(testMachines);
        assertTrue(testAutoCook.getAquiredStatus() == false);
    }

}

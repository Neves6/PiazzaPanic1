package com.neves6.piazzapanic.tests;

import com.neves6.piazzapanic.Machine;
import com.neves6.piazzapanic.powerups.AutoCook;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

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
        assertTrue();
    }

}

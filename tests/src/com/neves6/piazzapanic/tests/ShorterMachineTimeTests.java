package com.neves6.piazzapanic.tests;

import com.neves6.piazzapanic.Machine;
import com.neves6.piazzapanic.powerups.ShorterMachineTime;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class ShorterMachineTimeTests {
    ShorterMachineTime testMachineTime = new ShorterMachineTime(1L);

    @Test
    public void testConstructor(){
        assertTrue(testMachineTime.getEffectTime() == 1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApplyPowerUpEmpty(){
        testMachineTime.applyPowerUp(new ArrayList<>());
    }

    Machine m1 = new Machine("test", "test", "test", 5, false);
    Machine m2 = new Machine("test", "test", "test", 10, true);
    ArrayList<Machine> testMachines = new ArrayList<>();

    @Test
    public void testApplyPowerUpUnattained(){
        testMachines.add(m1);
        testMachines.add(m2);
        testMachines = testMachineTime.applyPowerUp(testMachines);

        Machine updatedm1 = testMachines.get(0);
        Machine updatedm2  = testMachines.get(1);

        assertTrue(updatedm1.getProcessingTime() == 5 &&
                updatedm2.getProcessingTime() == 10);
    }

    @Test
    public void testApplyPowerUpAttained(){
        testMachines.add(m1);
        testMachines.add(m2);
        testMachineTime.aquirePowerUp();
        testMachines = testMachineTime.applyPowerUp(testMachines);

        Machine updatedm1 = testMachines.get(0);
        Machine updatedm2  = testMachines.get(1);

        assertTrue(updatedm1.getProcessingTime() == 2.5 &&
                updatedm2.getProcessingTime() == 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEndPowerUpEmpty(){
        testMachineTime.endPowerUp(new ArrayList<>());
    }

    @Test
    public void testEndPowerUpUnattained(){
        testMachines.add(m1);
        testMachines.add(m2);
        testMachines = testMachineTime.applyPowerUp(testMachines);

        Machine updatedm1 = testMachines.get(0);
        Machine updatedm2  = testMachines.get(1);

        assertTrue(updatedm1.getProcessingTime() == 5 &&
                updatedm2.getProcessingTime() == 10);
    }

    @Test
    public void testEndPowerUpAttained() throws InterruptedException {
        testMachines.add(m1);
        testMachines.add(m2);
        testMachineTime.aquirePowerUp();

        testMachines = testMachineTime.applyPowerUp(testMachines);
        TimeUnit.MILLISECONDS.sleep(5);

        testMachines = testMachineTime.endPowerUp(testMachines);

        Machine updatedm1 = testMachines.get(0);
        Machine updatedm2  = testMachines.get(1);

        assertTrue(updatedm1.getProcessingTime() == 5 &&
                updatedm2.getProcessingTime() == 10);
    }



}

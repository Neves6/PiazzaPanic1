package com.neves6.piazzapanic.tests;

import com.neves6.piazzapanic.powerups.ShorterMachineTime;
import com.neves6.piazzapanic.powerups.TimeFreeze;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class ShorterMachineTimeTests {
    ShorterMachineTime testMachineTime = new ShorterMachineTime(1L);
    @Test
    public void testConstructor(){
        assertTrue(testMachineTime.getEffectTime() == 1L);
    }

}

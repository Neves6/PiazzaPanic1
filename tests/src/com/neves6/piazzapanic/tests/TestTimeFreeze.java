package com.neves6.piazzapanic.tests;

import com.neves6.piazzapanic.powerups.TimeFreeze;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class TestTimeFreeze {
    TimeFreeze testFreeze = new TimeFreeze(1L);
    @Test
    public void testConstructor(){
        assertTrue(testFreeze.getEffectTime() == 1L);
    }

    @Test
    public void testUnactivatedFreeze(){
        assertTrue(testFreeze.getDelta(1) == 1);
    }

    @Test
    public void testActivatedFreeze(){
        testFreeze.aquirePowerUp();
        assertTrue(testFreeze.getDelta(1) == 0);
    }


}

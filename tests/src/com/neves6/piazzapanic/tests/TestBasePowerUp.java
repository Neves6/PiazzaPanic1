package com.neves6.piazzapanic.tests;

import com.badlogic.gdx.Gdx;
import com.neves6.piazzapanic.powerups.BasePowerUp;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class TestBasePowerUp {
    BasePowerUp testPowerUp = new BasePowerUp(5000L);
    @Test
    public void testConstructorI(){
        assertTrue(testPowerUp.getAquiredStatus() == false);
    }

    @Test
    public void testConstructorII(){
        assertTrue(testPowerUp.getStartTime() == 0L);
    }

    @Test
    public void testConstructorIII(){
        assertTrue(testPowerUp.getEffectTime() == 5000L);
    }

    @Test
    public void invalidActivation(){
        System.out.println(testPowerUp.getStartTime() );
        testPowerUp.setStartTime();
        assertTrue(testPowerUp.getStartTime() == 0L);
    }

    @Test
    public void validActivation(){
        testPowerUp.aquirePowerUp();
        Long timeSet = System.currentTimeMillis();
        testPowerUp.setStartTime();
        assertTrue(testPowerUp.getStartTime() > timeSet);
    }

    BasePowerUp testPowerUpII = new BasePowerUp(5000L);

    @Test
    public void invalidEndTime(){
        assertTrue(testPowerUpII.endTime() == false);
    }

}


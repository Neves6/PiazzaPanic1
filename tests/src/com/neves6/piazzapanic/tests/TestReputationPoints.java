package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.neves6.piazzapanic.gamemechanisms.ReputationPoints;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestReputationPoints {
    @Test
    public void testDecrement(){
        ReputationPoints testPoints1 = new ReputationPoints(5);
        ReputationPoints testPoints2 = new ReputationPoints(-5);
        assertTrue(testPoints1.decrement() && !testPoints2.decrement());
    }

}

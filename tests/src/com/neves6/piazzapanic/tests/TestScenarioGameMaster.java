package com.neves6.piazzapanic.tests;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.neves6.piazzapanic.Money;
import com.neves6.piazzapanic.PiazzaPanicGame;
import com.neves6.piazzapanic.ScenarioGameMaster;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class TestScenarioGameMaster {
    TiledMap map = new TmxMapLoader().load("tilemaps/level1.tmx");
    ScenarioGameMaster testMaster = new ScenarioGameMaster(new PiazzaPanicGame(), map, 1, 1, new Money());
    @Test
    public void tryMoveValidUp(){
        testMaster.tryMove("up");
        assertTrue(testMaster.getChef(1).getyCoord() == 6);
        assertTrue(testMaster.getChef(1).getxCoord() == 6);
    }

    @Test
    public void tryMoveValidDown(){
        testMaster.tryMove("down");
        assertTrue(testMaster.getChef(1).getyCoord() == 4);
        assertTrue(testMaster.getChef(1).getxCoord() == 6);
    }

    @Test
    public void tryMoveValidRight(){
        testMaster.tryMove("right");
        assertTrue(testMaster.getChef(1).getxCoord() == 7);
        assertTrue(testMaster.getChef(1).getyCoord() == 5);
    }

    @Test
    public void tryMoveValidLeft(){
        testMaster.tryMove("left");
        assertTrue(testMaster.getChef(1).getxCoord() == 5);
        assertTrue(testMaster.getChef(1).getyCoord() == 5);

    }

    @Test
    public void tryMoveInvalidUp(){
        testMaster.getChef(1).setxCoord(7);
        testMaster.getChef(1).setyCoord(6);
        testMaster.tryMove("up");
        assertTrue(testMaster.getChef(1).getyCoord() == 6);
        assertTrue(testMaster.getChef(1).getxCoord() == 7);
    }

    @Test
    public void tryMoveInvalidDown(){
        testMaster.getChef(1).setxCoord(6);
        testMaster.getChef(1).setyCoord(4);
        testMaster.tryMove("down");
        assertTrue(testMaster.getChef(1).getyCoord() == 4);
        assertTrue(testMaster.getChef(1).getxCoord() == 6);

    }

    @Test
    public void tryMoveInvalidRight(){
        testMaster.getChef(1).setxCoord(6);
        testMaster.getChef(1).setyCoord(8);
        testMaster.tryMove("left");
        assertTrue(testMaster.getChef(1).getxCoord() == 6);
        assertTrue(testMaster.getChef(1).getyCoord() == 8);
    }

    @Test
    public void tryMoveInvalidLeft(){
        testMaster.getChef(1).setxCoord(2);
        testMaster.getChef(1).setyCoord(8);
        testMaster.tryMove("left");
        assertTrue(testMaster.getChef(1).getxCoord() == 2);
        assertTrue(testMaster.getChef(1).getyCoord() == 8);
    }

    @Test
    public void testDisplayTextEmpty(){
        assertTrue(testMaster.generateHoldingsText().equals("Chef 1 is holding:\n[]\n"));
    }

    ScenarioGameMaster testMasterII = new ScenarioGameMaster(new PiazzaPanicGame(), map, 2, 1, new Money());

    @Test
    public void testDisplayTextFull(){
        testMasterII.getChef(1).addToInventory("t");
        testMasterII.getChef(1).addToInventory("e");
        testMasterII.getChef(2).addToInventory("s");
        testMasterII.getChef(2).addToInventory("t");
        assertTrue(testMasterII.generateHoldingsText().equals("Chef 1 is holding:\n[t, e]\nChef 2 is holding:\n[s, t]\n"));
    }

    ScenarioGameMaster testMasterIII = new ScenarioGameMaster(new PiazzaPanicGame(), map, 2, 0, new Money());
}


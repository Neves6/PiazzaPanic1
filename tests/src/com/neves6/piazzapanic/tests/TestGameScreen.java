package com.neves6.piazzapanic.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.neves6.piazzapanic.screens.GameScreen;
import com.neves6.piazzapanic.screens.PiazzaPanicGame;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class TestGameScreen {
    TiledMap map = new TmxMapLoader().load("tilemaps/level1.tmx");
    GameScreen testGameScreen = new GameScreen(new PiazzaPanicGame(true), 1);

    @Test
    public void testMoveChefUpViaInput(){
        Gdx.gl = Gdx.gl20;
        testGameScreen.keyDown(Input.Keys.W);
        assertTrue(testGameScreen.getGameMaster().getChef(1).getyCoord() == 6);
        assertTrue(testGameScreen.getGameMaster().getChef(1).getxCoord() == 6);
    }

    @Test
    public void testMoveValidDownViaInput() {
        Gdx.gl = Gdx.gl20;
        testGameScreen.keyDown(Input.Keys.S);
        assertTrue(testGameScreen.getGameMaster().getChef(1).getyCoord() == 4);
        assertTrue(testGameScreen.getGameMaster().getChef(1).getxCoord() == 6);
    }

    @Test
    public void testMoveValidRightViaInput()  {
        Gdx.gl = Gdx.gl20;
        testGameScreen.keyDown(Input.Keys.D);
        assertTrue(testGameScreen.getGameMaster().getChef(1).getxCoord() == 6);
        assertTrue(testGameScreen.getGameMaster().getChef(1).getyCoord() == 5);
    }

    @Test
    public void testMoveValidLeftViaInput(){
        Gdx.gl = Gdx.gl20;
        testGameScreen.keyDown(Input.Keys.A);
        assertTrue(testGameScreen.getGameMaster().getChef(1).getxCoord() == 5);
        assertTrue(testGameScreen.getGameMaster().getChef(1).getyCoord() == 5);
    }

    @Test
    public void tryAttainChefOne() {
        Gdx.gl = Gdx.gl20;
        testGameScreen.keyDown(Input.Keys.NUM_1);
        assertTrue(testGameScreen.getGameMaster().getSelectedChef() == 1);
    }

    @Test
    public void tryAttainChefTwo() {
        Gdx.gl = Gdx.gl20;
        testGameScreen.keyDown(Input.Keys.NUM_2);
        assertTrue(testGameScreen.getGameMaster().getSelectedChef() == 2);
    }

    @Test
    public void tryAttainChefThree() {
        Gdx.gl = Gdx.gl20;
        testGameScreen.keyDown(Input.Keys.NUM_3);
        assertTrue(testGameScreen.getGameMaster().getSelectedChef() == 3);
    }

    @Test
    public void tryInteractDefault(){
        // Always first chef facing down if nothing is changed.
        Gdx.gl = Gdx.gl20;
        testGameScreen.keyDown(Input.Keys.NUM_3);
        assertTrue(testGameScreen.getGameMaster().getSelectedChef() == 3);
    }



}

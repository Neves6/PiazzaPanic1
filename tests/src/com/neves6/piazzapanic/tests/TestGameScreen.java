package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.neves6.piazzapanic.screens.GameScreen;
import com.neves6.piazzapanic.screens.PiazzaPanicGame;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestGameScreen {
  GameScreen testGameScreen = new GameScreen(new PiazzaPanicGame(true), 1);

  @Test
  public void testMoveChefUpViaInput() {
    Gdx.gl = Gdx.gl20;
    testGameScreen.keyDown(Input.Keys.W);
    assertEquals(
        "+1 to the y when moving up", 6, testGameScreen.getGameMaster().getChef(1).getyCoord());
    assertEquals(
        "no effect to the x when moving up",
        6,
        testGameScreen.getGameMaster().getChef(1).getxCoord());
  }

  @Test
  public void testMoveValidDownViaInput() {
    Gdx.gl = Gdx.gl20;
    testGameScreen.keyDown(Input.Keys.S);
    assertEquals(
        "-1 to the y when moving down", 4, testGameScreen.getGameMaster().getChef(1).getyCoord());
    assertEquals(
        "no effect to the x when moving up",
        6,
        testGameScreen.getGameMaster().getChef(1).getxCoord());
  }

  @Test
  public void testMoveValidRightViaInput() {
    Gdx.gl = Gdx.gl20;
    testGameScreen.keyDown(Input.Keys.D);
    assertEquals(
        "+1 to the x when moving right", 6, testGameScreen.getGameMaster().getChef(1).getxCoord());
    assertEquals(
        "no effect on the y when moving right",
        5,
        testGameScreen.getGameMaster().getChef(1).getyCoord());
  }

  @Test
  public void tryAttainChefOne() {
    Gdx.gl = Gdx.gl20;
    testGameScreen.keyDown(Input.Keys.NUM_1);
    assertEquals(
        "Selecting one should select chef 1", 1, testGameScreen.getGameMaster().getSelectedChef());
  }

  @Test
  public void tryAttainChefTwo() {
    Gdx.gl = Gdx.gl20;
    testGameScreen.keyDown(Input.Keys.NUM_2);
    assertEquals(
        "Selecting two should select chef 2", 2, testGameScreen.getGameMaster().getSelectedChef());
  }

  @Test
  public void tryAttainChefThree() {
    Gdx.gl = Gdx.gl20;
    testGameScreen.keyDown(Input.Keys.NUM_3);
    assertEquals(
        "Selecting three should select chef 3",
        3,
        testGameScreen.getGameMaster().getSelectedChef());
  }
}

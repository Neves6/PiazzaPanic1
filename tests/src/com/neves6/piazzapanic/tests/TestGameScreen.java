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
  GameScreen testGameScreen = new GameScreen(new PiazzaPanicGame(true), 1, false, false);

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

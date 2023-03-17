package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.Gdx;
import com.neves6.piazzapanic.screens.IntroScreen;
import com.neves6.piazzapanic.screens.PiazzaPanicGame;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestScreenTransitions {
  @Test
  public void testIntroScreenOnLaunch() {
    Gdx.gl20 = Gdx.gl;
    PiazzaPanicGame testGame = new PiazzaPanicGame();
    testGame.create();
    assertTrue(testGame.getScreen().getClass() == IntroScreen.class);
  }
}

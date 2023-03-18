package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.Gdx;
import com.neves6.piazzapanic.screens.CreditsScreen;import com.neves6.piazzapanic.screens.IntroScreen;
import com.neves6.piazzapanic.screens.PiazzaPanicGame;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestScreenTransitions {
  PiazzaPanicGame testGame = new PiazzaPanicGame();

  @Test
  public void testIntroScreenOnLaunch() {
    Gdx.gl20 = Gdx.gl;
    testGame.create();
    assertTrue(testGame.getScreen().getClass() == IntroScreen.class);
  }

  @Test
  public void testCreditsScreenToTitle(){
    Gdx.gl20 = Gdx.gl;
    CreditsScreen testCredits = new CreditsScreen(testGame);
    testCredits.keyDown(2);
    assertTrue(testGame.getScreen().getClass() == IntroScreen.class);
  }
}

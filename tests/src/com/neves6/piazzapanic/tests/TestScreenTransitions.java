package com.neves6.piazzapanic.tests;

import static org.junit.Assert.*;

import com.badlogic.gdx.Gdx;
import com.neves6.piazzapanic.screens.*;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestScreenTransitions {
  PiazzaPanicGame testGame = new PiazzaPanicGame(true);

  @Test
  public void testIntroScreenOnLaunch() {
    Gdx.gl20 = Gdx.gl;
    testGame.create();
    assertSame(
        "The game must go straight to the introScreen on Launch",
        testGame.getScreen().getClass(),
        IntroScreen.class);
  }

  @Test
  public void testCreditsScreenToTitle() {
    Gdx.gl20 = Gdx.gl;
    CreditsScreen testCredits = new CreditsScreen(testGame);
    assertTrue(testCredits.keyDown(2));
    assertSame(
        "The credits screen must lead to the title screen once any button is pressed.",
        testGame.getScreen().getClass(),
        TitleScreen.class);
  }

  @Test
  public void testIntrotoTitleScreen() {
    Gdx.gl20 = Gdx.gl;
    IntroScreen testIntro = new IntroScreen(testGame);
    testIntro.show();
    Long waitTime = System.currentTimeMillis();
    while (System.currentTimeMillis() - waitTime < 2000) {
      testIntro.render(1);
    }
    assertSame(
        "After a fixed period of time, the intro screen must automatically transition to the title"
            + " screen.",
        testGame.getScreen().getClass(),
        TitleScreen.class);
  }

  @Test
  public void testPlayButton() {
    Gdx.gl20 = Gdx.gl;
    TitleScreen testTitle = new TitleScreen(testGame);
    testTitle.show();
    testTitle.getPlayButton().toggle();
    assertSame(
        "Pressing the play button must go to the level selector screen.",
        testGame.getScreen().getClass(),
        LevelSelectorScreen.class);
  }

  @Test
  public void testTutorialButton() {
    Gdx.gl20 = Gdx.gl;
    TitleScreen testTitle = new TitleScreen(testGame);
    testTitle.show();
    testTitle.getTutorialButton().toggle();
    assertSame(
        "Pressing the tutorial button must lead to the tutorial screen.",
        testGame.getScreen().getClass(),
        TutorialScreen.class);
  }

  @Test
  public void testCreditsButton() {
    Gdx.gl20 = Gdx.gl;
    TitleScreen testTitle = new TitleScreen(testGame);
    testTitle.show();
    testTitle.getCreditsButton().toggle();
    assertSame(
        "Pressing the credits button must lead to the credits screen",
        testGame.getScreen().getClass(),
        CreditsScreen.class);
  }

  @Test
  public void testSettingsButton() {
    Gdx.gl20 = Gdx.gl;
    TitleScreen testTitle = new TitleScreen(testGame);
    testTitle.show();
    testTitle.getSettingsButton().toggle();
    assertSame(
        "Pressing the settings button must lead to the settings screen",
        testGame.getScreen().getClass(),
        SettingsScreen.class);
  }

  @Test
  public void testWinScreenToCredit() {
    GameWinScreen testWinScreen = new GameWinScreen(testGame, 20);
    testWinScreen.show();
    testWinScreen.getCreditsScreen().toggle();
    assertSame(
        "Pressing the credits button, must lead to the credits screen",
        testGame.getScreen().getClass(),
        CreditsScreen.class);
  }

  @Test
  public void testWinScreenToTitle() {
    GameWinScreen testWinScreen = new GameWinScreen(testGame, 20);
    testWinScreen.show();
    testWinScreen.getTitleButton().toggle();
    assertSame(
        "Pressing the title button must lead to the title screen",
        testGame.getScreen().getClass(),
        TitleScreen.class);
  }

  @Test
  public void testTutorialToGameOne() {
    TutorialScreen testTutorial = new TutorialScreen(testGame, "game1", false, false);
    testTutorial.keyDown(0);
    assertSame(
        "Pressing any button with the continueTo set to game1 should lead to the game screen",
        testGame.getScreen().getClass(),
        GameScreen.class);
  }

  @Test
  public void testTutorialToGameTwo() {
    TutorialScreen testTutorial = new TutorialScreen(testGame, "game2", false, false);
    testTutorial.keyDown(0);
    assertSame(
        "Pressing any button with the continueTo set to game2 should lead to the game screen",
        testGame.getScreen().getClass(),
        GameScreen.class);
  }

  @Test
  public void testTutorialToGameThree() {
    TutorialScreen testTutorial = new TutorialScreen(testGame, "game3", false, false);
    testTutorial.keyDown(0);
    assertSame(
        "Pressing any button with the continueTo set to game3 should lead to the game screen",
        testGame.getScreen().getClass(),
        GameScreen.class);
  }

  @Test
  public void testTutorialToTitle() {
    TutorialScreen testTutorial = new TutorialScreen(testGame, "title", false, false);
    testTutorial.keyDown(0);
    assertSame(
        "Pressing any button with the continueTo set to title should lead to the title screen",
        testGame.getScreen().getClass(),
        TitleScreen.class);
  }

  @Test
  public void testTutorialToDefault() {
    TutorialScreen testTutorial = new TutorialScreen(testGame, "void", false, false);
    testTutorial.keyDown(0);
    assertSame(
        "Pressing any button with the continueTo set to anything apart from game1/2/3 should lead"
            + " to the game screen",
        testGame.getScreen().getClass(),
        TitleScreen.class);
  }
}

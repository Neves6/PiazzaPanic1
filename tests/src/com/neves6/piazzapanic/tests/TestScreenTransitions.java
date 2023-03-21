package com.neves6.piazzapanic.tests;

import static org.junit.Assert.assertTrue;

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
    assertTrue(testGame.getScreen().getClass() == IntroScreen.class);
  }

  @Test
  public void testCreditsScreenToTitle() {
    Gdx.gl20 = Gdx.gl;
    CreditsScreen testCredits = new CreditsScreen(testGame);
    assertTrue(testCredits.keyDown(2) == true);
    assertTrue(testGame.getScreen().getClass() == TitleScreen.class);
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
    assertTrue(testGame.getScreen().getClass() == TitleScreen.class);
  }

  @Test
  public void testPlayButton() {
    Gdx.gl20 = Gdx.gl;
    TitleScreen testTitle = new TitleScreen(testGame);
    testTitle.show();
    testTitle.getPlayButton().toggle();
    assertTrue(testGame.getScreen().getClass() == LevelSelectorScreen.class);
  }

  @Test
  public void testTutorialButton() {
    Gdx.gl20 = Gdx.gl;
    TitleScreen testTitle = new TitleScreen(testGame);
    testTitle.show();
    testTitle.getTutorialButton().toggle();
    assertTrue(testGame.getScreen().getClass() == TutorialScreen.class);
  }

  @Test
  public void testCreditsButton() {
    Gdx.gl20 = Gdx.gl;
    TitleScreen testTitle = new TitleScreen(testGame);
    testTitle.show();
    testTitle.getCreditsButton().toggle();
    assertTrue(testGame.getScreen().getClass() == CreditsScreen.class);
  }

  @Test
  public void testSettingsButton() {
    Gdx.gl20 = Gdx.gl;
    TitleScreen testTitle = new TitleScreen(testGame);
    testTitle.show();
    testTitle.getSettingsButton().toggle();
    assertTrue(testGame.getScreen().getClass() == SettingsScreen.class);
  }

  @Test
  public void testWinScreenToCredit(){
    GameWinScreen testWinScreen = new GameWinScreen(testGame, 20);
    testWinScreen.show();
    testWinScreen.getCreditsScreen().toggle();
    assertTrue(testGame.getScreen().getClass() == CreditsScreen.class);
  }

  @Test
  public void testWinScreenToTitle(){
    GameWinScreen testWinScreen = new GameWinScreen(testGame, 20);
    testWinScreen.show();
    testWinScreen.getTitleScreen().toggle();
    assertTrue(testGame.getScreen().getClass() == TitleScreen.class);
  }

  @Test
  public void testTutorialToGameOne(){
    TutorialScreen testTutorial = new TutorialScreen(testGame, "game1");
    testTutorial.keyDown(0);
    assertTrue(testGame.getScreen().getClass() == GameScreen.class);
  }

  @Test
  public void testTutorialToGameTwo(){
    TutorialScreen testTutorial = new TutorialScreen(testGame, "game2");
    testTutorial.keyDown(0);
    assertTrue(testGame.getScreen().getClass() == GameScreen.class);
  }

  @Test
  public void testTutorialToGameThree(){
    TutorialScreen testTutorial = new TutorialScreen(testGame, "game3");
    testTutorial.keyDown(0);
    assertTrue(testGame.getScreen().getClass() == GameScreen.class);
  }

  @Test
  public void testTutorialToTitle(){
    TutorialScreen testTutorial = new TutorialScreen(testGame, "title");
    testTutorial.keyDown(0);
    assertTrue(testGame.getScreen().getClass() == TitleScreen.class);
  }

  @Test
  public void testTutorialToDefault(){
    TutorialScreen testTutorial = new TutorialScreen(testGame, "void");
    testTutorial.keyDown(0);
    assertTrue(testGame.getScreen().getClass() == TitleScreen.class);
  }
}

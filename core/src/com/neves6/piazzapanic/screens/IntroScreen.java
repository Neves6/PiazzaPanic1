/**
 * INTEGRATED SETTINGS
 *
 * <p>REQUIREMENT: recall user screen settings
 *
 * <p>FEATURE ADDITION.
 */
package com.neves6.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neves6.piazzapanic.gamemechanisms.Utility;
import java.util.ArrayList;

/** The screen with an animation on start up. */
public class IntroScreen extends ScreenAdapter {
  PiazzaPanicGame game;
  OrthographicCamera camera;
  Animation<TextureRegion> introAnimation;
  Texture introSheet;
  BitmapFont font;
  float stateTime;
  int frameCols;
  int frameRows;
  int winWidth;
  int winHeight;
  ArrayList<String> settings;
  TextureRegion currentFrame;

  /**
   * Constructor method.
   *
   * @param game Instance of PiazzaPanicGame used to control screen transitions.
   */
  public IntroScreen(PiazzaPanicGame game) {
    this.game = game;
    camera = new OrthographicCamera();
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
    // Added to set screen size using settings from text file
    settings = Utility.getSettings();
    if (settings.get(0).trim().equals("fullscreen")) {
      Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    } else if (settings.get(0).trim().equals("windowed")) {
      Gdx.graphics.setWindowedMode(1280, 720);
    }
  }

  /** What to show when this screen is loaded. */
  public void show() {
    // INTRO ANIMATION
    frameCols = 8;
    frameRows = 1;
    introSheet = new Texture(Gdx.files.internal("intro_sheet.png"));
    TextureRegion[][] tmp =
        TextureRegion.split(
            introSheet, introSheet.getWidth() / frameCols, introSheet.getHeight() / frameRows);
    TextureRegion[] walkFrames = new TextureRegion[frameCols * frameRows];
    int index = 0;
    for (int i = 0; i < frameRows; i++) {
      for (int j = 0; j < frameCols; j++) {
        walkFrames[index++] = tmp[i][j];
      }
    }
    introAnimation = new Animation<TextureRegion>(0.125f, walkFrames);
    stateTime = 0f;
  }

  /**
   * What to process in every frame.
   *
   * @param delta The time in seconds since the last render.
   */
  @Override
  public void render(float delta) {
    Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
    stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
    winWidth = Gdx.graphics.getWidth();
    winHeight = Gdx.graphics.getHeight();

    // Get current frame of animation for the current stateTime
    currentFrame = introAnimation.getKeyFrame(stateTime, true);

    camera.update();

    if (stateTime > 2f) {
      // dispose();
      game.setScreen(new TitleScreen(game));
    }

    game.getBatch().setProjectionMatrix(camera.combined);

    game.getBatch().begin();
    game.getBatch()
        .draw(
            currentFrame,
            winWidth / 2f - winWidth / 10f,
            winHeight / 2f - winWidth / 10f,
            winWidth / 5f,
            winWidth / 5f);
    font.draw(
        game.getBatch(),
        "GEORGE\nAssessment 2\nFinal Build",
        winWidth / 2f - winWidth / 10f,
        winHeight / 2f - winWidth / 9f,
        winWidth / 5f,
        1,
        false);
    game.getBatch().end();
  }

  /**
   * Changes size of input upon user adjustment.
   *
   * @param width Integer representing the horizontal size of the screen.
   * @param height Integer representing the vertical size of the screen.
   */
  @Override
  public void resize(int width, int height) {
    super.resize(width, height);
    camera.setToOrtho(false, width, height);
  }

  /** Method which runs when the screen transitions to another. */
  @Override
  public void hide() {
    super.dispose();
    game.dispose();
    introSheet.dispose();
    font.dispose();
  }
}

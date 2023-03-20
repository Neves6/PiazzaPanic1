package com.neves6.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neves6.piazzapanic.gamemechanisms.Utility;
import java.util.ArrayList;

public class IntroScreen extends ScreenAdapter {
  PiazzaPanicGame game;
  OrthographicCamera camera;
  SpriteBatch batch;
  Animation<TextureRegion> introAnimation;
  Texture introSheet;
  BitmapFont font;
  float stateTime;
  static final int FRAME_COLS = 8;
  static final int FRAME_ROWS = 1;
  int winWidth;
  int winHeight;
  ArrayList<String> settings;

  public IntroScreen(PiazzaPanicGame game) {
    this.game = game;
  }

  public void show() {
    if (game.testFlag == false){
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

      // INTRO ANIMATION
      introSheet = new Texture(Gdx.files.internal("intro_sheet.png"));
      TextureRegion[][] tmp =
          TextureRegion.split(
              introSheet, introSheet.getWidth() / FRAME_COLS, introSheet.getHeight() / FRAME_ROWS);
      TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
      int index = 0;
      for (int i = 0; i < FRAME_ROWS; i++) {
        for (int j = 0; j < FRAME_COLS; j++) {
          walkFrames[index++] = tmp[i][j];
        }
      }
      introAnimation = new Animation<TextureRegion>(0.125f, walkFrames);
    }

    stateTime = 0f;
  }

  @Override
  public void render(float delta) {
    if (game.testFlag == false){
      Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
      winWidth = Gdx.graphics.getWidth();
      winHeight = Gdx.graphics.getHeight();

      // Get current frame of animation for the current stateTime
      final TextureRegion currentFrame = introAnimation.getKeyFrame(stateTime, true);

      camera.update();
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
          "NEVES6\nAssessment 1\nIndev Build",
          winWidth / 2f - winWidth / 10f,
          winHeight / 2f - winWidth / 9f,
          winWidth / 5f,
          1,
          false);
      game.getBatch().end();
    }

    stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
    if (stateTime > 2f) {
      // dispose();
      game.setScreen(new TitleScreen(game));
    }
  }

  @Override
  public void resize(int width, int height) {
    if (game.testFlag == false){
      super.resize(width, height);
      camera.setToOrtho(false, width, height);
    }
  }

  @Override
  public void hide() {
    super.dispose();
    game.dispose();
    game.getBatch().dispose();
    introSheet.dispose();
    font.dispose();
  }
}

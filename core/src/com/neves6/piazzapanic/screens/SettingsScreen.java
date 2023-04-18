package com.neves6.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.neves6.piazzapanic.gamemechanisms.Utility;
import java.util.ArrayList;

/** Screen used to adjust and save settings for current and future games. */
public class SettingsScreen extends ScreenAdapter {
  PiazzaPanicGame game;
  OrthographicCamera camera;
  SpriteBatch batch;
  BitmapFont font;
  Texture bg;
  Stage stage;
  TextButton fullscreenButton;
  TextButton volumeFullButton;
  TextButton volumeHalfButton;
  TextButton volumeNoneButton;
  TextButton.TextButtonStyle buttonStyle;
  Skin skin;
  TextureAtlas atlas;
  int winWidth;
  int winHeight;
  ArrayList<String> settings;
  TextButton mainMenuButton;

  /**
   * Constructor method.
   *
   * @param game Instance of PiazzaPanicGame used to control screen transitions.
   */
  public SettingsScreen(PiazzaPanicGame game) {
    this.game = game;
    settings = Utility.getSettings();
    font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
    bg = new Texture(Gdx.files.internal("title_screen_large-min.png"));
  }

  /** What to show when this screen is loaded. */
  @Override
  public void show() {
    camera = new OrthographicCamera();
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    skin = new Skin();
    atlas = new TextureAtlas(Gdx.files.internal("buttons/title/unnamed.atlas"));
    skin.addRegions(atlas);
    buttonStyle = new TextButton.TextButtonStyle();
    buttonStyle.font = font;
    buttonStyle.up = skin.getDrawable("black_alpha_mid");
    buttonStyle.down = skin.getDrawable("black_alpha_mid");
    buttonStyle.checked = skin.getDrawable("black_alpha_mid");

    fullscreenButton = new TextButton("Toggle Screen: " + settings.get(0), buttonStyle);
    fullscreenButton.setPosition(
        Gdx.graphics.getWidth() / 2f - fullscreenButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f + fullscreenButton.getHeight() / 2);
    fullscreenButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            if (settings.get(0).trim().equals("fullscreen")) {
              // System.out.println("Toggling to windowed");
              settings.set(0, "windowed");
              Gdx.graphics.setWindowedMode(1280, 720);
            } else if (settings.get(0).trim().equals("windowed")) {
              // System.out.println("Toggling to fullscreen");
              settings.set(0, "fullscreen");
              Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
            // System.out.println("Win/FS Toggled?");
            Utility.setSettings(settings);
            game.setScreen(new TitleScreen(game));
          }
        });

    volumeFullButton = new TextButton("Volume: Full", buttonStyle);
    volumeFullButton.setPosition(
        Gdx.graphics.getWidth() / 2f - volumeFullButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f - volumeFullButton.getHeight() / 2);
    volumeFullButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            settings.set(1, "full");
            Utility.setSettings(settings);
            game.setScreen(new TitleScreen(game));
          }
        });

    volumeHalfButton = new TextButton("Volume: Half", buttonStyle);
    volumeHalfButton.setPosition(
        Gdx.graphics.getWidth() / 2f - volumeHalfButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f - volumeHalfButton.getHeight() * 3 / 2);
    volumeHalfButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            settings.set(1, "half");
            Utility.setSettings(settings);
            game.setScreen(new TitleScreen(game));
          }
        });

    volumeNoneButton = new TextButton("Volume: None", buttonStyle);
    volumeNoneButton.setPosition(
        Gdx.graphics.getWidth() / 2f - fullscreenButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f - volumeNoneButton.getHeight() * 5 / 2);
    volumeNoneButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            settings.set(1, "none");
            Utility.setSettings(settings);
            game.setScreen(new TitleScreen(game));
          }
        });

    mainMenuButton = new TextButton("Exit to Main Menu", buttonStyle);
    mainMenuButton.setPosition(
        Gdx.graphics.getWidth() / 2f - volumeNoneButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f - volumeNoneButton.getHeight() * 7 / 2);
    mainMenuButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            game.setScreen(new TitleScreen(game));
          }
        });

    stage = new Stage();
    stage.addActor(volumeNoneButton);
    stage.addActor(volumeHalfButton);
    stage.addActor(volumeFullButton);
    stage.addActor(fullscreenButton);
    stage.addActor(mainMenuButton);
    Gdx.input.setInputProcessor(stage);
  }

  /**
   * What to process in every frame.
   *
   * @param delta The time in seconds since the last render.
   */
  @Override
  public void render(float delta) {
    Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    winWidth = Gdx.graphics.getWidth();
    winHeight = Gdx.graphics.getHeight();

    camera.update();
    game.getBatch().setProjectionMatrix(camera.combined);

    game.getBatch().begin();
    font.draw(
        game.getBatch(),
        "SETTINGS",
        0,
        winHeight / 2f + font.getLineHeight() * 3,
        winWidth,
        1,
        false);
    game.getBatch().end();
    stage.draw();
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
    fullscreenButton.setPosition(
        Gdx.graphics.getWidth() / 2f - fullscreenButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f + fullscreenButton.getHeight() / 2);
    volumeFullButton.setPosition(
        Gdx.graphics.getWidth() / 2f - volumeFullButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f - volumeFullButton.getHeight() / 2);
    volumeHalfButton.setPosition(
        Gdx.graphics.getWidth() / 2f - volumeHalfButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f - volumeHalfButton.getHeight() * 3 / 2);
    volumeNoneButton.setPosition(
        Gdx.graphics.getWidth() / 2f - volumeNoneButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f - volumeNoneButton.getHeight() * 5 / 2);
    mainMenuButton.setPosition(
        Gdx.graphics.getWidth() / 2f - fullscreenButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f - volumeNoneButton.getHeight() * 7 / 2);
    stage.clear();
    stage.addActor(fullscreenButton);
    stage.addActor(volumeFullButton);
    stage.addActor(volumeHalfButton);
    stage.addActor(volumeNoneButton);
    stage.addActor(mainMenuButton);
    camera.setToOrtho(false, width, height);
  }

  /** Method which runs when the screen transitions to another. */
  @Override
  public void hide() {
    super.dispose();
    game.dispose();
    font.dispose();
    bg.dispose();
    stage.dispose();
    skin.dispose();
    atlas.dispose();
  }
}

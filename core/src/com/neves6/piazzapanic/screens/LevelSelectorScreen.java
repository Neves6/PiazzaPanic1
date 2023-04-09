package com.neves6.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import java.io.File;

/** Screen used to select which level wants to be played. */
public class LevelSelectorScreen extends ScreenAdapter {
  PiazzaPanicGame game;
  OrthographicCamera camera;
  BitmapFont font;
  Texture bg;
  Texture lock;
  Texture leftCustomerMode;
  Texture rightCustomerMode;
  Texture leftPowerupMode;
  Texture rightPowerupMode;
  int winWidth;
  int winHeight;
  float bgScaleFactor;
  Stage stage;
  TextButton easyButton;
  TextButton mediumButton;
  TextButton hardButton;
  TextButton resumeButton;
  TextButton.TextButtonStyle buttonStyle;
  Button customerGameModeButton;
  Button powerupGameModeButton;
  Skin skin;
  TextureAtlas atlas;
  boolean resumeFlag = false;

  /**
   * Constructor method
   *
   * @param game Instance of PiazzaPanicGame used to control screen transitions.
   */
  public LevelSelectorScreen(PiazzaPanicGame game) {
    this.game = game;
    font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
    bg = new Texture(Gdx.files.internal("title_screen_large-min.png"));
    lock = new Texture(Gdx.files.internal("levellocked.png"));
    leftCustomerMode = new Texture(Gdx.files.internal("left-scenario-slider.png"));
    rightCustomerMode = new Texture(Gdx.files.internal("right-scenario-slider.png"));
    leftPowerupMode = new Texture(Gdx.files.internal("left-powerup-slider.png"));
    rightPowerupMode = new Texture(Gdx.files.internal("right-powerup-slider.png"));
  }

  /** What to show when this screen is loaded. */
  @Override
  public void show() {
    camera = new OrthographicCamera();
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    skin = new Skin();
    atlas = new TextureAtlas(Gdx.files.internal("buttons/levelselector/levelselector.atlas"));
    skin.addRegions(atlas);
    buttonStyle = new TextButton.TextButtonStyle();
    buttonStyle.font = font;
    buttonStyle.up = skin.getDrawable("black_alpha_square");
    buttonStyle.down = skin.getDrawable("black_alpha_square");
    buttonStyle.checked = skin.getDrawable("black_alpha_square");
    easyButton = new TextButton("Easy", buttonStyle);
    easyButton.setPosition(
        Gdx.graphics.getWidth() / 2f - easyButton.getWidth() / 2 - easyButton.getWidth() * 1.5f,
        Gdx.graphics.getHeight() / 2f - easyButton.getHeight() / 2);
    mediumButton = new TextButton("Medium", buttonStyle);
    mediumButton.setPosition(
        Gdx.graphics.getWidth() / 2f - easyButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f - easyButton.getHeight() / 2);
    hardButton = new TextButton("Hard", buttonStyle);
    hardButton.setPosition(
        Gdx.graphics.getWidth() / 2f - easyButton.getWidth() / 2 + hardButton.getWidth() * 1.5f,
        Gdx.graphics.getHeight() / 2f - easyButton.getHeight() / 2);
    easyButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            game.setScreen(
                new TutorialScreen(
                    game,
                    "easyGame",
                    customerGameModeButton.isChecked(),
                    powerupGameModeButton.isChecked()));
          }
        });
    mediumButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            game.setScreen(
                new TutorialScreen(
                    game,
                    "mediumGame",
                    customerGameModeButton.isChecked(),
                    powerupGameModeButton.isChecked()));
          }
        });
    hardButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            game.setScreen(
                new TutorialScreen(
                    game,
                    "hardGame",
                    customerGameModeButton.isChecked(),
                    powerupGameModeButton.isChecked()));
          }
        });

    // New assessment two buttons.
    customerGameModeButton =
        new ImageButton(
            new TextureRegionDrawable(leftCustomerMode),
            new TextureRegionDrawable(rightCustomerMode),
            new TextureRegionDrawable(rightCustomerMode));
    customerGameModeButton.setPosition(
        Gdx.graphics.getWidth() / 3f - easyButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 4f - easyButton.getHeight() / 2);

    powerupGameModeButton =
        new ImageButton(
            new TextureRegionDrawable(leftPowerupMode),
            new TextureRegionDrawable(rightPowerupMode),
            new TextureRegionDrawable(rightPowerupMode));
    powerupGameModeButton.setPosition(
        Gdx.graphics.getWidth() / 3f - easyButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 5.5f - easyButton.getHeight() / 2);

    if (game.testMode) {
      return;
    }

    stage = new Stage();
    Gdx.input.setInputProcessor(stage);
    stage.addActor(easyButton);
    stage.addActor(mediumButton);
    stage.addActor(hardButton);
    stage.addActor(customerGameModeButton);
    stage.addActor(powerupGameModeButton);

    File json = new File("here.json");
    if (json.exists() && json.length() != 0) {
      resumeButton = new TextButton("Resume", buttonStyle);
      resumeButton.setPosition(
          Gdx.graphics.getWidth() / 2f - easyButton.getWidth() / 2 + hardButton.getWidth() * 2f,
          Gdx.graphics.getHeight() / 5.5f - easyButton.getHeight() / 2);
      resumeButton.addListener(
          new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
              game.setScreen(
                  new TutorialScreen(
                      game,
                      "resume",
                      customerGameModeButton.isChecked(),
                      powerupGameModeButton.isChecked()));
            }
          });
      stage.addActor(resumeButton);
      resumeFlag = true;
    }
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
    bgScaleFactor = (float) winHeight / (float) bg.getHeight();

    camera.update();
    game.getBatch().setProjectionMatrix(camera.combined);

    game.getBatch().begin();
    game.getBatch()
        .draw(
            bg,
            -((bg.getWidth() * bgScaleFactor) - winWidth) / 2,
            0,
            bg.getWidth() * bgScaleFactor,
            bg.getHeight() * bgScaleFactor);
    font.draw(
        game.getBatch(),
        "LEVEL SELECTION",
        winWidth / 2f - winWidth / 10f,
        winHeight / 2f + winHeight / 5f,
        winWidth / 5f,
        1,
        false);
    stage.draw();
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
    if (game.testMode) {
      return;
    }

    super.resize(width, height);
    easyButton.setPosition(
        width / 2f - easyButton.getWidth() / 2 - easyButton.getWidth() * 1.5f,
        height / 2f - easyButton.getHeight() / 2);
    mediumButton.setPosition(
        width / 2f - easyButton.getWidth() / 2, height / 2f - easyButton.getHeight() / 2);
    hardButton.setPosition(
        width / 2f - easyButton.getWidth() / 2 + hardButton.getWidth() * 1.5f,
        height / 2f - easyButton.getHeight() / 2);
    customerGameModeButton.setPosition(
        Gdx.graphics.getWidth() / 3f - easyButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 4f - easyButton.getHeight() / 2);
    powerupGameModeButton.setPosition(
        Gdx.graphics.getWidth() / 3f - easyButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 3f - easyButton.getHeight() / 2);
    stage.clear();
    stage.addActor(easyButton);
    stage.addActor(mediumButton);
    stage.addActor(hardButton);
    stage.addActor(customerGameModeButton);
    stage.addActor(powerupGameModeButton);
    if (resumeFlag) {
      stage.addActor(resumeButton);
    }
    stage.getViewport().update(width, height);
    camera.setToOrtho(false, width, height);
  }

  /** Method which runs when the screen transitions to another. */
  @Override
  public void hide() {
    super.dispose();
    game.dispose();
    font.dispose();
    bg.dispose();
    lock.dispose();
    stage.dispose();
    skin.dispose();
    atlas.dispose();
  }
}

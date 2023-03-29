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
  Stage flipButtonStage;
  TextButton level1Button;
  TextButton level2Button;
  TextButton level3Button;
  TextButton.TextButtonStyle buttonStyle;
  Button customerGameModeButton;
  Button powerupGameModeButton;
  Skin skin;
  TextureAtlas atlas;

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
    level1Button = new TextButton("Level 1", buttonStyle);
    level1Button.setPosition(
        Gdx.graphics.getWidth() / 2f - level1Button.getWidth() / 2 - level1Button.getWidth() * 1.5f,
        Gdx.graphics.getHeight() / 2f - level1Button.getHeight() / 2);
    level2Button = new TextButton("Level 2", buttonStyle);
    level2Button.setPosition(
        Gdx.graphics.getWidth() / 2f - level1Button.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f - level1Button.getHeight() / 2);
    level3Button = new TextButton("Level 3", buttonStyle);
    level3Button.setPosition(
        Gdx.graphics.getWidth() / 2f - level1Button.getWidth() / 2 + level3Button.getWidth() * 1.5f,
        Gdx.graphics.getHeight() / 2f - level1Button.getHeight() / 2);
    level1Button.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            // game.setScreen(new GameScreen(game, 1));
            game.setScreen(new TutorialScreen(game, "game1"));
          }
        });
    level1Button.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            // game.setScreen(new GameScreen(game, 2));
          }
        });
    level1Button.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            // game.setScreen(new GameScreen(game, 3));
          }
        });

    // New assessment two buttons.
    customerGameModeButton =
        new ImageButton(
            new TextureRegionDrawable(leftCustomerMode),
            new TextureRegionDrawable(rightCustomerMode),
            new TextureRegionDrawable(rightCustomerMode));
    customerGameModeButton.setPosition(40, 100);

    powerupGameModeButton =
        new ImageButton(
            new TextureRegionDrawable(leftPowerupMode),
            new TextureRegionDrawable(rightCustomerMode),
            new TextureRegionDrawable(rightPowerupMode));
    powerupGameModeButton.setPosition(40, 75);

    if (game.testMode) {
      return;
    }

    flipButtonStage = new Stage();
    flipButtonStage.addActor(customerGameModeButton);
    flipButtonStage.addActor(powerupGameModeButton);

    stage = new Stage();
    Gdx.input.setInputProcessor(flipButtonStage);
    stage.addActor(level1Button);
    stage.addActor(level2Button);
    stage.addActor(level3Button);
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
    game.getBatch()
        .draw(
            lock,
            level2Button.getX(),
            level2Button.getY(),
            level2Button.getWidth(),
            level2Button.getHeight());
    game.getBatch()
        .draw(
            lock,
            level3Button.getX(),
            level3Button.getY(),
            level3Button.getWidth(),
            level3Button.getHeight());
    flipButtonStage.draw();
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
    level1Button.setPosition(
        width / 2f - level1Button.getWidth() / 2 - level1Button.getWidth() * 1.5f,
        height / 2f - level1Button.getHeight() / 2);
    level2Button.setPosition(
        width / 2f - level1Button.getWidth() / 2, height / 2f - level1Button.getHeight() / 2);
    level3Button.setPosition(
        width / 2f - level1Button.getWidth() / 2 + level3Button.getWidth() * 1.5f,
        height / 2f - level1Button.getHeight() / 2);
    stage.clear();
    stage.addActor(level1Button);
    stage.addActor(level2Button);
    stage.addActor(level3Button);
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

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Screen displayed on launch with settings, credits and game selection
 * buttons.
 */
public class TitleScreen extends ScreenAdapter {
  PiazzaPanicGame game;
  OrthographicCamera camera;
  BitmapFont font;
  Texture bg;
  int winWidth;
  int winHeight;
  float bgScaleFactor;
  Stage stage;
  TextButton playButton;
  TextButton tutorialButton;
  TextButton creditsButton;
  TextButton settingsButton;
  TextButton exitButton;
  TextButton.TextButtonStyle buttonStyle;
  Skin skin;
  TextureAtlas atlas;

  /**
   * Constructor method.
   * @param game Instance of PiazzaPanicGame used to control screen transitions.
   */
  public TitleScreen(PiazzaPanicGame game) {
    this.game = game;
    font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
    bg = new Texture(Gdx.files.internal("title_screen_large-min.png"));
  }

  /**
   * What to show when this screen is loaded.
   */
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

    playButton = new TextButton("Play", buttonStyle);
    playButton.setPosition(
        Gdx.graphics.getWidth() / 2f - playButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f + playButton.getHeight() / 2);
    playButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            game.setScreen(new LevelSelectorScreen(game));
          }
        });

    tutorialButton = new TextButton("Tutorial", buttonStyle);
    tutorialButton.setPosition(
        Gdx.graphics.getWidth() / 2f - tutorialButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f - tutorialButton.getHeight() / 2);
    tutorialButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            game.setScreen(new TutorialScreen(game, "title"));
          }
        });

    creditsButton = new TextButton("Credits", buttonStyle);
    creditsButton.setPosition(
        Gdx.graphics.getWidth() / 2f - creditsButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f - creditsButton.getHeight() * 3 / 2);
    creditsButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            game.setScreen(new CreditsScreen(game));
          }
        });

    settingsButton = new TextButton("Settings", buttonStyle);
    settingsButton.setPosition(
        Gdx.graphics.getWidth() / 2f - settingsButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f - settingsButton.getHeight() * 5 / 2);
    settingsButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            game.setScreen(new SettingsScreen(game));
          }
        });

    exitButton = new TextButton("Exit", buttonStyle);
    exitButton.setPosition(
        Gdx.graphics.getWidth() / 2f - exitButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2f - exitButton.getHeight() * 7 / 2);
    exitButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            Gdx.app.exit();
          }
        });

    if (game.testMode) {
      return;
    }

    stage = new Stage();
    stage.addActor(exitButton);
    stage.addActor(settingsButton);
    stage.addActor(creditsButton);
    stage.addActor(tutorialButton);
    stage.addActor(playButton);
    Gdx.input.setInputProcessor(stage);
  }

  /**
   * What to process in every frame.
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
        "PIAZZA PANIC 1",
        winWidth / 2f - winWidth / 10f,
        winHeight / 2f + winHeight / 5f,
        winWidth / 5f,
        1,
        false);
    game.getBatch().end();
    stage.draw();
  }

  /**
   * Changes size of input upon user adjustment.
   * @param width Integer representing the horizontal size of
   *              the screen.
   * @param height Integer representing the vertical size of
   *               the screen.
   */
  @Override
  public void resize(int width, int height) {
    super.resize(width, height);
    playButton.setPosition(
        width / 2f - playButton.getWidth() / 2, height / 2f + playButton.getHeight() / 2);
    tutorialButton.setPosition(
        width / 2f - tutorialButton.getWidth() / 2, height / 2f - tutorialButton.getHeight() / 2);
    creditsButton.setPosition(
        width / 2f - creditsButton.getWidth() / 2, height / 2f - creditsButton.getHeight() * 3 / 2);
    settingsButton.setPosition(
        width / 2f - settingsButton.getWidth() / 2,
        height / 2f - settingsButton.getHeight() * 5 / 2);
    exitButton.setPosition(
        width / 2f - exitButton.getWidth() / 2, height / 2f - exitButton.getHeight() * 7 / 2);

    if (game.testMode) {
      return;
    }

    stage.clear();
    stage.addActor(playButton);
    stage.addActor(tutorialButton);
    stage.addActor(creditsButton);
    stage.addActor(settingsButton);
    stage.addActor(exitButton);
    stage.getViewport().update(width, height);
    camera.setToOrtho(false, width, height);
  }

  /**
   * Method which runs when the screen transitions to
   * another.
   */
  @Override
  public void hide() {
    if (game.testMode) {
      return;
    }

    super.dispose();
    game.dispose();
    font.dispose();
    bg.dispose();
    stage.dispose();
    skin.dispose();
    atlas.dispose();
  }

  /**
   *
   * @return
   */
  public Button getPlayButton() {
    return playButton;
  }

  public Button getTutorialButton() {
    return tutorialButton;
  }

  public Button getCreditsButton() {
    return creditsButton;
  }

  public Button getSettingsButton() {
    return settingsButton;
  }
}

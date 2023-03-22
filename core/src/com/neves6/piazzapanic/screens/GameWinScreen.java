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

/**
 * Screen which indicates that the user has won.
 */
public class GameWinScreen extends ScreenAdapter {
  PiazzaPanicGame game;
  OrthographicCamera camera;
  SpriteBatch batch;
  BitmapFont font;
  Texture bg;
  int winWidth;
  int winHeight;
  float bgScaleFactor;
  Stage stage;
  TextButton creditsButton;
  TextButton titleButton;
  TextButton.TextButtonStyle buttonStyle;
  Skin skin;
  TextureAtlas atlas;
  int completionTime;

  /**
   * Constructor method.
   * @param game Instance of PiazzaPanicGame used to control screen transitions.
   * @param completionTime Time taken to complete the game.
   */
  public GameWinScreen(PiazzaPanicGame game, int completionTime) {
    this.game = game;
    this.completionTime = completionTime;
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

    creditsButton = new TextButton("Credits", buttonStyle);
    creditsButton.setPosition(
        Gdx.graphics.getWidth() / 2f - creditsButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 3f + creditsButton.getHeight() / 2);
    creditsButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            game.setScreen(new CreditsScreen(game));
          }
        });

    titleButton = new TextButton("Title", buttonStyle);
    titleButton.setPosition(
        Gdx.graphics.getWidth() / 2f - titleButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 3f - titleButton.getHeight() / 2);
    titleButton.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent event, Actor actor) {
            game.setScreen(new TitleScreen(game));
          }
        });

    if (game.testMode) {
      return;
    }

    stage = new Stage();
    stage.addActor(creditsButton);
    stage.addActor(titleButton);
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
        "CONGRATULATIONS!\nYou completed the game in " + completionTime + " seconds!",
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
    creditsButton.setPosition(
        width / 2f - creditsButton.getWidth() / 2, height / 3f + creditsButton.getHeight() / 2);
    titleButton.setPosition(
        width / 2f - titleButton.getWidth() / 2, height / 3f - titleButton.getHeight() / 2);
    stage.clear();
    stage.addActor(creditsButton);
    stage.addActor(titleButton);
    stage.getViewport().update(width, height);
    camera.setToOrtho(false, width, height);
  }

  /**
   * Method which runs when the screen transitions to
   * another.
   */
  @Override
  public void hide() {
    super.dispose();
    game.dispose();
    batch.dispose();
    font.dispose();
    bg.dispose();
    stage.dispose();
    skin.dispose();
    atlas.dispose();
  }

  /**
   * Getter method for credits button.
   * @return A button with a change listener which can be used for testing.
   */
  public TextButton getCreditsScreen() {
    return creditsButton;
  }

  /**
   * Getter method for title button.
   * @return A button with a change listener which can be used for testing.
   */
  public TextButton getTitleScreen() {
    return titleButton;
  }
}

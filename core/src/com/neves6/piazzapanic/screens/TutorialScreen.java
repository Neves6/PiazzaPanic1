package com.neves6.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import org.json.simple.parser.ParseException;

/** Screen used to demonstrate gameplay to users. */
public class TutorialScreen extends ScreenAdapter implements InputProcessor {
  PiazzaPanicGame game;
  OrthographicCamera camera;
  BitmapFont font;
  Texture tutorial;
  Texture tutorial2;
  int winWidth;
  int winHeight;
  String continueTo;
  Boolean scenerio;
  Boolean disablePowerUp;
  int custNo;

  public TutorialScreen(
      PiazzaPanicGame game, String continueTo, Boolean endless, Boolean disablePowerup) {
    this.game = game;
    this.continueTo = continueTo;
    font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
    tutorial = new Texture(Gdx.files.internal("tutorial.png"));
    tutorial2 = new Texture(Gdx.files.internal("tutorialmap.png"));
    this.scenerio = endless;
    this.disablePowerUp = disablePowerup;
    if (endless) {
      custNo = -1;
    } else {
      custNo = 5;
    }
  }

  public TutorialScreen(
      PiazzaPanicGame game,
      String continueTo,
      Boolean endless,
      Boolean disablePowerup,
      Integer custNo) {
    this.game = game;
    this.continueTo = continueTo;
    font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
    tutorial = new Texture(Gdx.files.internal("tutorial.png"));
    tutorial2 = new Texture(Gdx.files.internal("tutorialmap.png"));
    this.scenerio = endless;
    this.disablePowerUp = disablePowerup;
    this.custNo = custNo;
  }

  /** What to show when this screen is loaded. */
  @Override
  public void show() {
    camera = new OrthographicCamera();
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.input.setInputProcessor(this);
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
    game.getBatch().draw(tutorial, 0, 0, winWidth, winHeight);
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
    font.dispose();
    tutorial.dispose();
  }

  /**
   * @param keycode one of the constants in {@link Input.Keys}
   * @return true
   */
  @Override
  public boolean keyDown(int keycode) {
    if (!(tutorial.equals(tutorial2))){
      tutorial = tutorial2;
      return true;
    }

    switch (continueTo) {
      case "title":
        game.setScreen(new TitleScreen(game));
        break;
      case "easyGame":
        game.setScreen(new GameScreen(game, 1, scenerio, disablePowerUp, custNo));
        break;
      case "mediumGame":
        game.setScreen(new GameScreen(game, 2, scenerio, disablePowerUp, custNo));
        break;
      case "hardGame":
        game.setScreen(new GameScreen(game, 3, scenerio, disablePowerUp, custNo));
        break;
      case "resume":
        try {
          game.setScreen(new GameScreen(game));
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
        break;
      default:
        game.setScreen(new TitleScreen(game));
        break;
    }
    return true;
  }

  /**
   * UNUSED METHOD.
   *
   * @param keycode one of the constants in {@link Input.Keys}
   * @return false
   */
  @Override
  public boolean keyUp(int keycode) {
    return false;
  }

  /**
   * UNUSED METHOD.
   *
   * @param character The character
   * @return false
   */
  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  /**
   * UNUSED METHOD.
   *
   * @param screenX The x coordinate, origin is in the upper left corner
   * @param screenY The y coordinate, origin is in the upper left corner
   * @param pointer the pointer for the event.
   * @param button the button
   * @return false
   */
  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  /**
   * UNUSED METHOD.
   *
   * @param screenX The x coordinate, origin is in the upper left corner
   * @param screenY The y coordinate, origin is in the upper left corner
   * @param pointer the pointer for the event.
   * @param button the button
   * @return false
   */
  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  /**
   * UNUSED METHOD.
   *
   * @param screenX The x coordinate, origin is in the upper left corner
   * @param screenY The y coordinate, origin is in the upper left corner
   * @param pointer the pointer for the event.
   * @return false
   */
  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  /**
   * UNUSED METHOD.
   *
   * @param screenX The x coordinate, origin is in the upper left corner
   * @param screenY The y coordinate, origin is in the upper left corner
   * @return false
   */
  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  /**
   * UNUSED METHOD.
   *
   * @param amountX the horizontal scroll amount, negative or positive depending on the direction
   *     the wheel was scrolled.
   * @param amountY the vertical scroll amount, negative or positive depending on the direction the
   *     wheel was scrolled.
   * @return false
   */
  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }
}

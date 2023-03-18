package com.neves6.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/** Display once the game has been completed by the user. */
public class CreditsScreen extends ScreenAdapter implements InputProcessor {
  PiazzaPanicGame game;
  OrthographicCamera camera;
  BitmapFont font;
  Texture credits;
  int winWidth;
  int winHeight;

  /**
   * Constructor method which sets up the fonts and textures to display the credits.
   *
   * @param game
   */
  public CreditsScreen(PiazzaPanicGame game) {
    this.game = game;
  }

  /** Inherited show method which sets up screen dimensions. */
  @Override
  public void show() {
    font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
    credits = new Texture(Gdx.files.internal("credits.png"));

    Gdx.input.setInputProcessor(this);

    camera = new OrthographicCamera();
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  /**
   * Draws the credits image onto the screen.
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
    game.getBatch().draw(credits, 0, 0, winWidth, winHeight);
    game.getBatch().end();
  }

  /**
   * A function which can resize the window if it changes.
   *
   * @param width Horizontal size of the screen.
   * @param height Vertical size of the screen.
   */
  @Override
  public void resize(int width, int height) {
    super.resize(width, height);
    camera.setToOrtho(false, width, height);
  }

  /** What to do when the screen is exited. */
  @Override
  public void hide() {
    super.dispose();
    game.dispose();
    font.dispose();
    credits.dispose();
  }

  /**
   * When the user presses a single key down.
   *
   * @param keycode a one to one mapping between the user keyboard and numbers.
   * @return true
   */
  @Override
  public boolean keyDown(int keycode) {
    game.setScreen(new TitleScreen(game));
    return true;
  }

  /**
   * UNUSED (NEED TO INHERIT CLASS)
   *
   * @param keycode one of the constants in {@link Input.Keys}
   * @return false
   */
  @Override
  public boolean keyUp(int keycode) {
    return false;
  }

  /**
   * @param character The character
   * @return false
   */
  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  /**
   * @param screenX The x coordinate, origin is in the upper left corner
   * @param screenY The y coordinate, origin is in the upper left corner
   * @param pointer the pointer for the event.
   * @param button the button
   * @return
   */
  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  /**
   * @param screenX
   * @param screenY
   * @param pointer the pointer for the event.
   * @param button the button
   * @return
   */
  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  /**
   * @param screenX
   * @param screenY
   * @param pointer the pointer for the event.
   * @return
   */
  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  /**
   * @param screenX
   * @param screenY
   * @return
   */
  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  /**
   * @param amountX the horizontal scroll amount, negative or positive depending on the direction
   *     the wheel was scrolled.
   * @param amountY the vertical scroll amount, negative or positive depending on the direction the
   *     wheel was scrolled.
   * @return
   */
  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }
}

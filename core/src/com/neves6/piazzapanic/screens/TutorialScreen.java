package com.neves6.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TutorialScreen extends ScreenAdapter implements InputProcessor {
  PiazzaPanicGame game;
  OrthographicCamera camera;
  SpriteBatch batch;
  BitmapFont font;
  Texture tutorial;
  int winWidth;
  int winHeight;
  String continueTo;

  public TutorialScreen(PiazzaPanicGame game, String continueTo) {
    this.game = game;
    this.continueTo = continueTo;
    font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
    tutorial = new Texture(Gdx.files.internal("tutorial.png"));
  }

  @Override
  public void show() {
    camera = new OrthographicCamera();
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.input.setInputProcessor(this);
  }

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

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);
    camera.setToOrtho(false, width, height);
  }

  @Override
  public void hide() {
    super.dispose();
    game.dispose();
    font.dispose();
    tutorial.dispose();
  }

  /**
   * @param keycode one of the constants in {@link Input.Keys}
   * @return
   */
  @Override
  public boolean keyDown(int keycode) {
    switch (continueTo) {
      case "title":
        game.setScreen(new TitleScreen(game));
        break;
      case "game1":
        game.setScreen(new GameScreen(game, 1));
        break;
      case "game2":
        game.setScreen(new GameScreen(game, 2));
        break;
      case "game3":
        game.setScreen(new GameScreen(game, 3));
        break;
      default:
        game.setScreen(new TitleScreen(game));
        break;
    }
    return true;
  }

  /**
   * @param keycode one of the constants in {@link Input.Keys}
   * @return
   */
  @Override
  public boolean keyUp(int keycode) {
    return false;
  }

  /**
   * @param character The character
   * @return
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

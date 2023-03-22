package com.neves6.piazzapanic.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Main class handling screen input. */
public class PiazzaPanicGame extends Game {
  SpriteBatch _batch;
  Boolean testMode;

  /**
   * A function which create a batch if it is null and returns it. If it is already created, return
   * the existing batch.
   *
   * @return A batch object that can be used to draw and place items.
   */
  public SpriteBatch getBatch() {
    if (_batch == null) {
      _batch = new SpriteBatch();
    }
    return _batch;
  }

  /** Default constructor method. */
  public PiazzaPanicGame() {
    super();
    testMode = false;
  }

  /**
   * Constructor method where you can set your test flag.
   *
   * @param tf Boolean value representing whether we are running in test mode or not.
   */
  public PiazzaPanicGame(Boolean tf) {
    testMode = tf;
  }

  /** What to show when this screen is loaded. */
  @Override
  public void create() {
    setScreen(new IntroScreen(this));
  }

  /**
   * Changes size of input upon user adjustment.
   *
   * @param width Integer representing the horizontal size of the screen.
   * @param height Integer representing the vertical size of the screen.
   */
  public void resize(int width, int height) {
    super.resize(width, height);
  }

  /** UNUSED METHOD * */
  public void render(float delta) {}

  /** UNUSED METHOD * */
  @Override
  public void dispose() {}
}

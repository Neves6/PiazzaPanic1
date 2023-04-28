/**
 * INTEGRATED FEATURE.
 *
 * <p>REQUIREMENTS: save game/new game drawing game screen
 *
 * <p>ADDED FEATURE
 */
package com.neves6.piazzapanic.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Main class handling screen input. */
public class PiazzaPanicGame extends Game {
  SpriteBatch batch;

  /**
   * A function which create a batch if it is null and returns it. If it is already created, return
   * the existing batch.
   *
   * @return A batch object that can be used to draw and place items.
   */
  public SpriteBatch getBatch() {
    if (batch == null) {
      batch = new SpriteBatch();
    }
    return batch;
  }

  /** Default constructor method. */
  public PiazzaPanicGame() {
    super();
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

  /** UNUSED METHOD. * */
  public void render(float delta) {}

  /** UNUSED METHOD. * */
  @Override
  public void dispose() {}
}

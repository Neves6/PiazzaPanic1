package com.neves6.piazzapanic.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class PiazzaPanicGame extends Game {
  SpriteBatch _batch;
  Boolean testMode;

  public SpriteBatch getBatch() {
    if (_batch == null) {
      _batch = new SpriteBatch();
    }
    return _batch;
  }

  public PiazzaPanicGame() {
    super();
    testMode = false;
  }

  public PiazzaPanicGame(Boolean tf){
    testMode = tf;
  }

  @Override
  public void create() {
    setScreen(new IntroScreen(this));
  }

  public void resize(int width, int height) {
    super.resize(width, height);
  }

  public void render(float delta) {}

  @Override
  public void dispose() {}
}

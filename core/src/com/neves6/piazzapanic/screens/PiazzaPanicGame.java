package com.neves6.piazzapanic.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PiazzaPanicGame extends Game {
  SpriteBatch _batch;
  Boolean testFlag;

  public SpriteBatch getBatch() {
    if (_batch == null) {
      _batch = new SpriteBatch();
    }
    return _batch;
  }

  public PiazzaPanicGame() {
    super();
    this.testFlag = false;
  }

  public PiazzaPanicGame(Boolean testFlag) {
    super();
    this.testFlag = testFlag;
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

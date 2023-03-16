package com.neves6.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CreditsScreen extends ScreenAdapter {
  PiazzaPanicGame game;
  OrthographicCamera camera;
  SpriteBatch batch;
  BitmapFont font;
  Texture credits;
  int winWidth;
  int winHeight;

  public CreditsScreen(PiazzaPanicGame game) {
    this.game = game;
    font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
    credits = new Texture(Gdx.files.internal("credits.png"));
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(
        new InputAdapter() {
          @Override
          public boolean keyDown(int keyCode) {
            game.setScreen(new TitleScreen(game));
            return true;
          }
        });

    camera = new OrthographicCamera();
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    batch = new SpriteBatch();
  }

  @Override
  public void render(float delta) {
    Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    winWidth = Gdx.graphics.getWidth();
    winHeight = Gdx.graphics.getHeight();

    camera.update();
    game.batch.setProjectionMatrix(camera.combined);

    game.batch.begin();
    game.batch.draw(credits, 0, 0, winWidth, winHeight);
    game.batch.end();
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
    batch.dispose();
    font.dispose();
    credits.dispose();
  }
}

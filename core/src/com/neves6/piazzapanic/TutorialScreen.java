package com.neves6.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class TutorialScreen extends ScreenAdapter {
    PiazzaPanicGame game;
    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    Texture tutorial;
    int winWidth;
    int winHeight;
    ArrayList<String> settings;
    String continueTo;

    public TutorialScreen(PiazzaPanicGame game, String continueTo) {
        this.game = game;
        this.continueTo = continueTo;
        settings = Utility.getSettings();
        font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
        tutorial = new Texture(Gdx.files.internal("tutorial.png"));
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (continueTo.equals("title")) {
                    game.setScreen(new TitleScreen(game));
                } else if (continueTo.equals("game1")) {
                    game.setScreen(new GameScreen(game, 1));
                } else if (continueTo.equals("game2")) {
                    game.setScreen(new GameScreen(game, 2));
                } else if (continueTo.equals("game3")) {
                    game.setScreen(new GameScreen(game, 3));
                } else {
                    game.setScreen(new TitleScreen(game));
                }
                return true;
            }
        });

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        winWidth = Gdx.graphics.getWidth();
        winHeight = Gdx.graphics.getHeight();

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(tutorial, 0, 0, winWidth, winHeight);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void hide(){
        super.dispose();
        game.dispose();
        batch.dispose();
        font.dispose();
        tutorial.dispose();
    }
}

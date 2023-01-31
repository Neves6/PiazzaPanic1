package com.neves6.piazzapanic;

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
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

public class SettingsScreen extends ScreenAdapter {
    PiazzaPanicGame game;
    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    Texture bg;
    Stage stage;
    TextButton fullscreenButton;
    TextButton volumeFullButton;
    TextButton volumeHalfButton;
    TextButton volumeNoneButton;
    TextButton.TextButtonStyle buttonStyle;
    Skin skin;
    TextureAtlas atlas;
    int winWidth;
    int winHeight;
    ArrayList<String> settings;

    public SettingsScreen(PiazzaPanicGame game) {
        this.game = game;
        settings = Utility.getSettings();
        font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
        bg = new Texture(Gdx.files.internal("title_screen_large-min.png"));
    }

    @Override
    public void show(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        atlas = new TextureAtlas(Gdx.files.internal("buttons/title/unnamed.atlas"));
        skin.addRegions(atlas);
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.up = skin.getDrawable("black_alpha_mid");
        buttonStyle.down = skin.getDrawable("black_alpha_mid");
        buttonStyle.checked = skin.getDrawable("black_alpha_mid");

        fullscreenButton = new TextButton("Toggle Fullscreen", buttonStyle);
        fullscreenButton.setPosition(Gdx.graphics.getWidth()/2f - fullscreenButton.getWidth()/2, Gdx.graphics.getHeight()/2f + fullscreenButton.getHeight()/2);
        fullscreenButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (settings.get(0).equals("fullscreen")) {
                    System.out.println("Toggling to windowed");
                    settings.set(0, "windowed");
                    Gdx.graphics.setWindowedMode(1280, 720);
                } else if (settings.get(0).equals("windowed")) {
                    System.out.println("Toggling to fullscreen");
                    settings.set(0, "fullscreen");
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }
                Utility.setSettings(settings);
                game.setScreen(new TitleScreen(game));
            }
        });
        stage.addActor(fullscreenButton);

        volumeFullButton = new TextButton("Volume: Full", buttonStyle);
        volumeFullButton.setPosition(Gdx.graphics.getWidth()/2f - volumeFullButton.getWidth()/2, Gdx.graphics.getHeight()/2f - volumeFullButton.getHeight()/2);
        volumeFullButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings.set(1, "full");
                Utility.setSettings(settings);
                game.setScreen(new TitleScreen(game));
            }
        });
        stage.addActor(volumeFullButton);

        volumeHalfButton = new TextButton("Volume: Half", buttonStyle);
        volumeHalfButton.setPosition(Gdx.graphics.getWidth()/2f - volumeHalfButton.getWidth()/2, Gdx.graphics.getHeight()/2f - volumeHalfButton.getHeight()*3/2);
        volumeHalfButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings.set(1, "half");
                Utility.setSettings(settings);
                game.setScreen(new TitleScreen(game));
            }
        });
        stage.addActor(volumeHalfButton);

        volumeNoneButton = new TextButton("Volume: None", buttonStyle);
        volumeNoneButton.setPosition(Gdx.graphics.getWidth()/2f - volumeNoneButton.getWidth()/2, Gdx.graphics.getHeight()/2f - volumeNoneButton.getHeight()*5/2);
        volumeNoneButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings.set(1, "none");
                Utility.setSettings(settings);
                game.setScreen(new TitleScreen(game));
            }
        });
        stage.addActor(volumeNoneButton);
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
        font.draw(game.batch, "SETTINGS", 0, winHeight/2f + font.getLineHeight()*3, winWidth, 1, false);
        game.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        fullscreenButton.setPosition(Gdx.graphics.getWidth()/2f - fullscreenButton.getWidth()/2, Gdx.graphics.getHeight()/2f + fullscreenButton.getHeight()/2);
        volumeFullButton.setPosition(Gdx.graphics.getWidth()/2f - volumeFullButton.getWidth()/2, Gdx.graphics.getHeight()/2f - volumeFullButton.getHeight()/2);
        volumeHalfButton.setPosition(Gdx.graphics.getWidth()/2f - volumeHalfButton.getWidth()/2, Gdx.graphics.getHeight()/2f - volumeHalfButton.getHeight()*3/2);
        volumeNoneButton.setPosition(Gdx.graphics.getWidth()/2f - volumeNoneButton.getWidth()/2, Gdx.graphics.getHeight()/2f - volumeNoneButton.getHeight()*5/2);
        stage.clear();
        stage.addActor(fullscreenButton);
        stage.addActor(volumeFullButton);
        stage.addActor(volumeHalfButton);
        stage.addActor(volumeNoneButton);
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void hide(){

    }
}

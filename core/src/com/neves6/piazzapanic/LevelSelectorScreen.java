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

public class LevelSelectorScreen extends ScreenAdapter {
    PiazzaPanicGame game;
    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    Texture bg;
    Texture lock;
    int winWidth;
    int winHeight;
    float bgScaleFactor;
    Stage stage;
    TextButton level1Button;
    TextButton level2Button;
    TextButton level3Button;
    TextButton.TextButtonStyle buttonStyle;
    Skin skin;
    TextureAtlas atlas;

    public LevelSelectorScreen(PiazzaPanicGame game) {
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold.fnt"));
        bg = new Texture(Gdx.files.internal("title_screen_large-min.png"));
        lock = new Texture(Gdx.files.internal("levellocked.png"));
    }

    @Override
    public void show(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        atlas = new TextureAtlas(Gdx.files.internal("buttons/levelselector/levelselector.atlas"));
        skin.addRegions(atlas);
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.up = skin.getDrawable("black_alpha_square");
        buttonStyle.down = skin.getDrawable("black_alpha_square");
        buttonStyle.checked = skin.getDrawable("black_alpha_square");
        level1Button = new TextButton("Level 1", buttonStyle);
        level1Button.setPosition(Gdx.graphics.getWidth()/2 - level1Button.getWidth()/2 - level1Button.getWidth()*1.5f, Gdx.graphics.getHeight()/2 - level1Button.getHeight()/2);
        level2Button = new TextButton("Level 2", buttonStyle);
        level2Button.setPosition(Gdx.graphics.getWidth()/2 - level1Button.getWidth()/2, Gdx.graphics.getHeight()/2 - level1Button.getHeight()/2);
        level3Button = new TextButton("Level 3", buttonStyle);
        level3Button.setPosition(Gdx.graphics.getWidth()/2 - level1Button.getWidth()/2 + level3Button.getWidth()*1.5f, Gdx.graphics.getHeight()/2 - level1Button.getHeight()/2);
        stage.addActor(level1Button);
        stage.addActor(level2Button);
        stage.addActor(level3Button);
        level1Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game, 1));
            }
        });
        level1Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // game.setScreen(new GameScreen(game, 2));
            }
        });
        level1Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // game.setScreen(new GameScreen(game, 3));
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        winWidth = Gdx.graphics.getWidth();
        winHeight = Gdx.graphics.getHeight();
        bgScaleFactor = (float) winHeight / (float) bg.getHeight();

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(
                bg,
                -((bg.getWidth() * bgScaleFactor) - winWidth) / 2,
                0,
                bg.getWidth() * bgScaleFactor,
                bg.getHeight() * bgScaleFactor);
        font.draw(game.batch, "LEVEL SELECTION", winWidth / 2f - winWidth/10f, winHeight / 2f + winHeight/5f, winWidth/5f, 1, false);
        stage.draw();
        game.batch.draw(lock, level2Button.getX(), level2Button.getY(), level2Button.getWidth(), level2Button.getHeight());
        game.batch.draw(lock, level3Button.getX(), level3Button.getY(), level3Button.getWidth(), level3Button.getHeight());
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        level1Button.setPosition(width/2 - level1Button.getWidth()/2 - level1Button.getWidth()*1.5f, height/2 - level1Button.getHeight()/2);
        level2Button.setPosition(width/2 - level1Button.getWidth()/2, height/2 - level1Button.getHeight()/2);
        level3Button.setPosition(width/2 - level1Button.getWidth()/2 + level3Button.getWidth()*1.5f, height/2 - level1Button.getHeight()/2);
        stage.clear();
        stage.addActor(level1Button);
        stage.addActor(level2Button);
        stage.addActor(level3Button);
        stage.getViewport().update(width, height);
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void hide() {
        super.dispose();
        game.dispose();
        batch.dispose();
        font.dispose();
        bg.dispose();
        lock.dispose();
        stage.dispose();
        skin.dispose();
        atlas.dispose();
    }
}


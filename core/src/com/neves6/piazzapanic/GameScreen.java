package com.neves6.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen extends ScreenAdapter {
    PiazzaPanicGame game;
    int level;
    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    Stage stage;
    int winWidth;
    int winHeight;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    ScenarioGameMaster gm;
    float unitScale;
    float wScale;
    float hScale;
    int INITIAL_WIDTH;
    int INITIAL_HEIGHT;
    int[] renderableLayers = { 0, 1, 2 };
    Texture selectedTexture;
    Texture recipes;
    public GameScreen(PiazzaPanicGame game, int level) {
        this.game = game;
        this.level = level;
        font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold_Black.fnt"));
        //bg = new Texture(Gdx.files.internal("title_screen_large.png"));
        this.INITIAL_WIDTH = Gdx.graphics.getWidth();
        this.INITIAL_HEIGHT = Gdx.graphics.getHeight();
        if (level == 1) {
            map = new TmxMapLoader().load("tilemaps/level1.tmx");
            gm = new ScenarioGameMaster(game, map, 2, 5);
            unitScale = Gdx.graphics.getHeight() / (12f*32f);
            //wScale = (Gdx.graphics.getHeight() / (12f*32f)) * 21;
            //hScale =  Gdx.graphics.getHeight() /      32f       ;
            wScale = unitScale * 32f;
            hScale = unitScale * 32f;
            renderer = new OrthogonalTiledMapRenderer(map, unitScale);
        }
        selectedTexture = new Texture(Gdx.files.internal("people/selected.png"));
        recipes = new Texture(Gdx.files.internal("recipes.png"));
    }

    @Override
    public void show(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        stage = new Stage();
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.W) {
                    gm.tryMove("up");
                }
                if (keyCode == Input.Keys.A) {
                    gm.tryMove("left");
                }
                if (keyCode == Input.Keys.S) {
                    gm.tryMove("down");
                }
                if (keyCode == Input.Keys.D) {
                    gm.tryMove("right");
                }
                if (keyCode == Input.Keys.NUM_1) {
                    gm.setSelectedChef(1);
                }
                if (keyCode == Input.Keys.NUM_2) {
                    gm.setSelectedChef(2);
                }
                if (keyCode == Input.Keys.E) {
                    gm.tryInteract();
                }
                return true;
            }
        });

        gm.tickUpdate(delta);

        Gdx.gl20.glViewport( 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        winWidth = Gdx.graphics.getWidth();
        winHeight = Gdx.graphics.getHeight();

        camera.update();
        //game.batch.setProjectionMatrix(camera.combined);

        renderer.setView(camera);
        renderer.render(renderableLayers);

        game.batch.begin();
        game.batch.draw(gm.getChef(1).getTxNow(), gm.getChef(1).getxCoord() * wScale, gm.getChef(1).getyCoord() * hScale, 32 * unitScale, 32 * unitScale);
        game.batch.draw(gm.getChef(2).getTxNow(), gm.getChef(2).getxCoord() * wScale, gm.getChef(2).getyCoord() * hScale, 32 * unitScale, 32 * unitScale);
        if (gm.getSelectedChef() == 1) {
            game.batch.draw(selectedTexture, gm.getChef(1).getxCoord() * wScale, gm.getChef(1).getyCoord() * hScale, 32 * unitScale, 32 * unitScale);
        } else if (gm.getSelectedChef() == 2) {
            game.batch.draw(selectedTexture, gm.getChef(2).getxCoord() * wScale, gm.getChef(2).getyCoord() * hScale, 32 * unitScale, 32 * unitScale);
        }
        game.batch.draw(recipes, 20, 20);
        font.draw(game.batch, gm.generateHoldingsText(), winWidth - (6*(winWidth/8f)), winHeight - 20, (3*(winWidth/8f)), -1, true);
        font.draw(game.batch, gm.generateCustomersTrayText(), winWidth - (3*(winWidth/8f)), winHeight - 20, (3*(winWidth/8f)), -1, true);
        font.draw(game.batch, gm.generateTimerText(), winWidth - (winWidth/3f), 40, (winWidth/3f), -1, false);
        game.batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        if (width == INITIAL_WIDTH && height == INITIAL_HEIGHT) {
            super.resize(width, height);
            camera.setToOrtho(false, width, height);
            unitScale = Gdx.graphics.getHeight() / (12f*32f);
            //wScale = (Gdx.graphics.getHeight() / (12f*32f)) * 21;
            //hScale =  Gdx.graphics.getHeight() /      32f       ;
            wScale = unitScale * 32f;
            hScale = unitScale * 32f;
            renderer = new OrthogonalTiledMapRenderer(map, unitScale);
        } else {
            Gdx.graphics.setWindowedMode(INITIAL_WIDTH, INITIAL_HEIGHT);
        }
    }

    @Override
    public void hide(){
        game.dispose();
        batch.dispose();
        font.dispose();
        stage.dispose();
        map.dispose();
        renderer.dispose();
        selectedTexture.dispose();
        recipes.dispose();
    }
}

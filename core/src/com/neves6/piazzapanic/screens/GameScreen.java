package com.neves6.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.neves6.piazzapanic.gamemaster.ScenarioGameMaster;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.staff.BaseStaff;
import com.neves6.piazzapanic.staff.DeliveryStaff;
import com.neves6.piazzapanic.staff.IngredientsStaff;
import java.util.ArrayList;
import java.util.Arrays;

/** A screen that displays the main game. */
public class GameScreen extends ScreenAdapter implements InputProcessor {
  DeliveryStaff deliveryStaff;
  PiazzaPanicGame game;
  OrthographicCamera camera;
  SpriteBatch batch;
  BitmapFont font;
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
  int[] renderableLayers = {0, 1, 2};
  Texture selectedTexture;
  Texture recipes;
  Texture lock;
  Money machineUnlockBalance;
  IngredientsStaff ingredientsHelper;

  /**
   * Constructor method.
   *
   * @param game Instance of PiazzaPanicGame used to control screen transitions.
   * @param level The difficulty that the user has selected.
   */
  public GameScreen(PiazzaPanicGame game, int level, boolean scenerio, boolean disablePowerup) {
    this.machineUnlockBalance = new Money();
    this.deliveryStaff =
        new DeliveryStaff(
            new ArrayList<>(Arrays.asList(3, 4, 5, 6, 7, 8)),
            (new ArrayList<>(Arrays.asList(4, 4, 4, 4, 4, 4))));
    this.game = game;
    font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold_Black.fnt"));
    font.getData().setScale(0.75F);
    // bg = new Texture(Gdx.files.internal("title_screen_large.png"));
    this.INITIAL_WIDTH = Gdx.graphics.getWidth();
    this.INITIAL_HEIGHT = Gdx.graphics.getHeight();
    this.ingredientsHelper =
        new IngredientsStaff(
            new ArrayList<>(Arrays.asList(7, 6, 5, 4, 3, 2, 1, 2, 2, 2)),
            (new ArrayList<>(Arrays.asList(9, 9, 9, 9, 9, 9, 9, 9, 8, 9))));
    if (level == 1) {
      map = new TmxMapLoader().load("tilemaps/level1.tmx");
      if (scenerio) {
        gm =
            new ScenarioGameMaster(
                game,
                map,
                3,
                5,
                machineUnlockBalance,
                ingredientsHelper,
                deliveryStaff,
                disablePowerup);
      } else {
        gm =
            new ScenarioGameMaster(
                game,
                map,
                3,
                -1,
                machineUnlockBalance,
                ingredientsHelper,
                deliveryStaff,
                disablePowerup);
      }
      unitScale = Gdx.graphics.getHeight() / (12f * 32f);
      wScale = unitScale * 32f;
      hScale = unitScale * 32f;
      if (game.testMode == false) {
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);
      }
    }
    selectedTexture = new Texture(Gdx.files.internal("people/selected.png"));
    recipes = new Texture(Gdx.files.internal("recipes.png"));
    lock = new Texture(Gdx.files.internal("levellocked.png"));
  }

  /** What to show when this screen is loaded. */
  @Override
  public void show() {
    camera = new OrthographicCamera();
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    Gdx.graphics.setResizable(false);

    Gdx.input.setInputProcessor(this);
  }

  /**
   * What to process in every frame.
   *
   * @param delta The time in seconds since the last render.
   */
  @Override
  public void render(float delta) {
    gm.tickUpdate(delta);

    gm.setRecipeToStaff();

    Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    winWidth = Gdx.graphics.getWidth();
    winHeight = Gdx.graphics.getHeight();

    camera.update();
    // game.batch.setProjectionMatrix(camera.combined);

    renderer.setView(camera);
    renderer.render(renderableLayers);

    game.getBatch().begin();
    game.getBatch()
        .draw(
            gm.getChef(1).getTxNow(),
            gm.getChef(1).getxCoord() * wScale,
            gm.getChef(1).getyCoord() * hScale,
            32 * unitScale,
            32 * unitScale);
    game.getBatch()
        .draw(
            gm.getChef(2).getTxNow(),
            gm.getChef(2).getxCoord() * wScale,
            gm.getChef(2).getyCoord() * hScale,
            32 * unitScale,
            32 * unitScale);
    game.getBatch()
        .draw(
            gm.getChef(3).getTxNow(),
            gm.getChef(3).getxCoord() * wScale,
            gm.getChef(3).getyCoord() * hScale,
            32 * unitScale,
            32 * unitScale);
    game.getBatch()
        .draw(
            selectedTexture,
            gm.getChef(gm.getSelectedChef()).getxCoord() * wScale,
            gm.getChef(gm.getSelectedChef()).getyCoord() * hScale,
            32 * unitScale,
            32 * unitScale);

    if (gm.getCustomersRemaining() >= 1) {
      game.getBatch()
          .draw(
              gm.getFirstCustomer().getTxUp(),
              8 * wScale,
              2 * hScale,
              32 * unitScale,
              32 * unitScale);
      for (int i = 1; i < gm.getCustomersRemaining(); i++) {
        game.getBatch()
            .draw(
                gm.getFirstCustomer().getTxLeft(),
                (8 + i) * wScale,
                2 * hScale,
                32 * unitScale,
                32 * unitScale);
      }
    }
    font.draw(
        game.getBatch(),
        gm.getMachineTimerForChef(0),
        gm.getChef(1).getxCoord() * wScale,
        gm.getChef(1).getyCoord() * hScale + 2 * (hScale / 3f),
        32 * unitScale,
        1,
        true);
    font.draw(
        game.getBatch(),
        gm.getMachineTimerForChef(1),
        gm.getChef(2).getxCoord() * wScale,
        gm.getChef(2).getyCoord() * hScale + 2 * (hScale / 3f),
        32 * unitScale,
        1,
        true);
    game.getBatch().draw(recipes, 20, 20);
    font.draw(
        game.getBatch(),
        gm.generateHoldingsText(),
        winWidth - (4.75f * (winWidth / 8f)),
        winHeight - 20,
        (3 * (winWidth / 8f)),
        -1,
        true);
    font.draw(
        game.getBatch(),
        gm.generateCustomersTrayText(),
        winWidth - (3 * (winWidth / 8f)),
        winHeight - 20,
        (3 * (winWidth / 8f)),
        -1,
        true);
    font.draw(
        game.getBatch(),
        gm.generateTimerText(),
        winWidth - (winWidth / 3f),
        40,
        (winWidth / 3f),
        -1,
        true);
    font.draw(
        game.getBatch(),
        gm.generateReputationPointText(),
        winWidth - (winWidth / 3f),
        60,
        (winWidth / 3f),
        -1,
        true);
    font.draw(
        game.getBatch(),
        machineUnlockBalance.displayBalance(),
        winWidth - (winWidth / 3f),
        80,
        (winWidth / 3f),
        -1,
        true);
    font.draw(
        game.getBatch(),
        gm.getPowerUpRunner().displayText(),
        winWidth - (winWidth / 3f),
        550,
        (winWidth / 3f),
        -1,
        true);

    // Any machines that are unlockable add here to draw a lock on top of it.
    if (!(machineUnlockBalance.isUnlocked("chopping"))) {
      game.getBatch().draw(lock, 12 * wScale, 7 * hScale, 32 * unitScale, 32 * unitScale);
    }
    if (!(machineUnlockBalance.isUnlocked("forming"))) {
      game.getBatch().draw(lock, 10 * wScale, 7 * hScale, 32 * unitScale, 32 * unitScale);
    }
    if (!(machineUnlockBalance.isUnlocked("grill"))) {
      game.getBatch().draw(lock, 7 * wScale, 7 * hScale, 32 * unitScale, 32 * unitScale);
    }
    if (!(machineUnlockBalance.isUnlocked("potato"))) {
      game.getBatch().draw(lock, 14 * wScale, 6 * hScale, 32 * unitScale, 32 * unitScale);
    }
    if (!(machineUnlockBalance.isUnlocked("pizza"))) {
      game.getBatch().draw(lock, 1 * wScale, 6 * hScale, 32 * unitScale, 32 * unitScale);
    }
    if (!(machineUnlockBalance.isUnlocked("ingredients-staff"))) {
      game.getBatch().draw(lock, 2 * wScale, 7 * hScale, 32 * unitScale, 32 * unitScale);
    }
    if (!(machineUnlockBalance.isUnlocked("server-staff"))) {
      game.getBatch().draw(lock, 1 * wScale, 3 * hScale, 32 * unitScale, 32 * unitScale);
    }

    game.getBatch().end();

    drawSequence(ingredientsHelper);
    drawSequence(deliveryStaff);
  }

  /**
   * Gets a pair of coordinates and draws a chef.
   *
   * @param ob A baseStaff object type which path is getting drawn.
   */
  public void drawSequence(BaseStaff ob) {
    if (ob.getCollect()) {
      ArrayList<Integer> pairCoord = ob.getCoordInSeq();
      game.getBatch().begin();
      game.getBatch()
          .draw(
              new Texture(Gdx.files.internal("people/chef1down.png")),
              pairCoord.get(0) * wScale,
              pairCoord.get(1) * hScale,
              32 * unitScale,
              32 * unitScale);
      game.getBatch().end();
    }
  }

  /**
   * Changes size of input upon user adjustment.
   *
   * @param width Integer representing the horizontal size of the screen.
   * @param height Integer representing the vertical size of the screen.
   */
  @Override
  public void resize(int width, int height) {
    if (game.testMode) {
      return;
    }

    if (width == INITIAL_WIDTH && height == INITIAL_HEIGHT) {
      super.resize(width, height);
      camera.setToOrtho(false, width, height);
      unitScale = Gdx.graphics.getHeight() / (12f * 32f);
      wScale = unitScale * 32f;
      hScale = unitScale * 32f;
      renderer = new OrthogonalTiledMapRenderer(map, unitScale);
    } else {
      Gdx.graphics.setWindowedMode(INITIAL_WIDTH, INITIAL_HEIGHT);
    }
  }

  /** Method which runs when the screen transitions to another. */
  @Override
  public void hide() {
    super.dispose();
    game.dispose();
    font.dispose();
    map.dispose();
    renderer.dispose();
    selectedTexture.dispose();
    recipes.dispose();
  }

  /**
   * Getter method for scenario game master.
   *
   * @return Instance of scenario game master which is currently being used to control the game.
   */
  public ScenarioGameMaster getGameMaster() {
    return gm;
  }

  /**
   * Method which runs when user presses a key down.
   *
   * @param keycode one of the constants in Input.Keys
   * @return true
   */
  @Override
  public boolean keyDown(int keycode) {
    if (keycode == Input.Keys.W) {
      gm.tryMove("up");
    }
    if (keycode == Input.Keys.A) {
      gm.tryMove("left");
    }
    if (keycode == Input.Keys.S) {
      gm.tryMove("down");
    }
    if (keycode == Input.Keys.D) {
      gm.tryMove("right");
    }
    if (keycode == Input.Keys.NUM_1) {
      gm.setSelectedChef(1);
    }
    if (keycode == Input.Keys.NUM_2) {
      gm.setSelectedChef(2);
    }
    if (keycode == Input.Keys.NUM_3) {
      gm.setSelectedChef(3);
    }
    if (keycode == Input.Keys.E) {
      gm.tryInteract();
    }
    return true;
  }

  /**
   * UNUSED METHOD
   *
   * @param keycode one of the constants in {@link Input.Keys}
   * @return false
   */
  @Override
  public boolean keyUp(int keycode) {
    return false;
  }

  /**
   * UNUSED METHOD
   *
   * @param character The character
   * @return false
   */
  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  /**
   * UNUSED METHOD
   *
   * @param screenX The x coordinate, origin is in the upper left corner
   * @param screenY The y coordinate, origin is in the upper left corner
   * @param pointer the pointer for the event.
   * @param button the button
   * @return false
   */
  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  /**
   * UNUSED METHOD
   *
   * @param screenX
   * @param screenY
   * @param pointer the pointer for the event.
   * @param button the button
   * @return false
   */
  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  /**
   * UNUSED METHOD
   *
   * @param screenX
   * @param screenY
   * @param pointer the pointer for the event.
   * @return false
   */
  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  /**
   * UNUSED METHOD
   *
   * @param screenX
   * @param screenY
   * @return false
   */
  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  /**
   * UNUSED METHOD
   *
   * @param amountX the horizontal scroll amount, negative or positive depending on the direction
   *     the wheel was scrolled.
   * @param amountY the vertical scroll amount, negative or positive depending on the direction the
   *     wheel was scrolled.
   * @return false
   */
  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }
}

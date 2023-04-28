/**
 * ADDED SHARED SETUP, INTEGRATED OTHER FEATURES
 *
 * <p>REQUIREMENTS: LOAD FROM NEW GAME OR SAVED GAME
 *
 * <p>FEATURE ADDITION, INCREASE CODE EFFICIENCY.
 */
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
import com.neves6.piazzapanic.gamemaster.TextMaster;
import com.neves6.piazzapanic.gamemechanisms.GameReader;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.staff.BaseStaff;
import com.neves6.piazzapanic.staff.DeliveryStaff;
import com.neves6.piazzapanic.staff.IngredientsStaff;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.simple.parser.ParseException;

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
  float wscale;
  float hscale;
  int initialWidth;
  int initialHeight;
  int[] renderableLayers = {0, 1, 2};
  Texture selectedTexture;
  Texture recipes;
  Texture lock;
  Money machineUnlockBalance;
  IngredientsStaff ingredientsHelper;
  ArrayList<Boolean> wasd = new ArrayList<>(Arrays.asList(false, false, false, false));
  TextMaster tm = new TextMaster();

  /**
   * Constructor method.
   *
   * @param game Instance of PiazzaPanicGame used to control screen transitions.
   * @param level The difficulty that the user has selected.
   * @param scenario The boolean variable to indicate whether the game is time limited.
   * @param disablePowerup Should power ups be disabled or not.
   * @param custNo Number of customers that are needed to initialise the game.
   */
  public GameScreen(
      PiazzaPanicGame game, int level, boolean scenario, boolean disablePowerup, int custNo) {
    this.game = game;
    sharedSetup();
    map = new TmxMapLoader().load("tilemaps/level1.tmx");
    gm =
        new ScenarioGameMaster(
            game,
            map,
            3,
            custNo,
            machineUnlockBalance,
            ingredientsHelper,
            deliveryStaff,
            disablePowerup,
            level);
  }

  /**
   * Constructor method for loading a previously saved game.
   *
   * @param game An instance of PiazzaPanicGame.
   * @throws ParseException If the file containing the save data, cannot be parsed.
   */
  public GameScreen(PiazzaPanicGame game) throws ParseException {
    this.game = game;
    sharedSetup();
    GameReader gr = null;
    try {
      gr = new GameReader("here.json");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    map = new TmxMapLoader().load("tilemaps/level1.tmx");
    gm = gr.createGameMaster(game, map, machineUnlockBalance, ingredientsHelper, deliveryStaff);
  }

  public void sharedSetup() {
    unitScale = Gdx.graphics.getHeight() / (12f * 32f);
    wscale = unitScale * 32f;
    hscale = unitScale * 32f;
    font = new BitmapFont(Gdx.files.internal("fonts/IBM_Plex_Mono_SemiBold_Black.fnt"));
    font.getData().setScale(unitScale * 0.4F);
    this.initialWidth = Gdx.graphics.getWidth();
    this.initialHeight = Gdx.graphics.getHeight();
    this.machineUnlockBalance = new Money();
    this.deliveryStaff =
        new DeliveryStaff(
            new ArrayList<>(Arrays.asList(3, 4, 5, 6, 7, 8)),
            (new ArrayList<>(Arrays.asList(4, 4, 4, 4, 4, 4))));
    this.ingredientsHelper =
        new IngredientsStaff(
            new ArrayList<>(Arrays.asList(7, 6, 5, 4, 3, 2, 1, 2, 2, 2)),
            (new ArrayList<>(Arrays.asList(9, 9, 9, 9, 9, 9, 9, 9, 8, 9))));
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
    if (wasd.get(0)) {
      gm.tryMove("up");
    }
    if (wasd.get(1)) {
      gm.tryMove("left");
    }
    if (wasd.get(2)) {
      gm.tryMove("down");
    }
    if (wasd.get(3)) {
      gm.tryMove("right");
    }
    gm.tickUpdate(delta);

    gm.setRecipeToStaff();

    Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    winWidth = Gdx.graphics.getWidth();
    winHeight = Gdx.graphics.getHeight();

    camera.update();
    game.getBatch().setProjectionMatrix(camera.combined);

    renderer.setView(camera);
    renderer.render(renderableLayers);

    game.getBatch().begin();
    game.getBatch()
        .draw(
            gm.getChef(1).getTxNow(),
            gm.getChef(1).getxCoord() * wscale,
            gm.getChef(1).getyCoord() * hscale,
            32 * unitScale,
            32 * unitScale);
    game.getBatch()
        .draw(
            gm.getChef(2).getTxNow(),
            gm.getChef(2).getxCoord() * wscale,
            gm.getChef(2).getyCoord() * hscale,
            32 * unitScale,
            32 * unitScale);
    game.getBatch()
        .draw(
            gm.getChef(3).getTxNow(),
            gm.getChef(3).getxCoord() * wscale,
            gm.getChef(3).getyCoord() * hscale,
            32 * unitScale,
            32 * unitScale);
    game.getBatch()
        .draw(
            selectedTexture,
            gm.getChef(gm.getSelectedChef()).getxCoord() * wscale,
            gm.getChef(gm.getSelectedChef()).getyCoord() * hscale,
            32 * unitScale,
            32 * unitScale);

    if (gm.getCustomersRemaining() >= 1) {
      game.getBatch()
          .draw(
              gm.getFirstCustomer().getTxUp(),
              8 * wscale,
              2 * hscale,
              32 * unitScale,
              32 * unitScale);
      for (int i = 1; i < gm.getCustomersRemaining(); i++) {
        game.getBatch()
            .draw(
                gm.getFirstCustomer().getTxLeft(),
                (8 + i) * wscale,
                2 * hscale,
                32 * unitScale,
                32 * unitScale);
      }
    }
    font.draw(
        game.getBatch(),
        tm.getMachineTimerForChef(0, gm.getChefs()),
        gm.getChef(1).getxCoord() * wscale,
        gm.getChef(1).getyCoord() * hscale + 2 * (hscale / 3f),
        32 * unitScale,
        1,
        true);
    font.draw(
        game.getBatch(),
        tm.getMachineTimerForChef(1, gm.getChefs()),
        gm.getChef(2).getxCoord() * wscale,
        gm.getChef(2).getyCoord() * hscale + 2 * (hscale / 3f),
        32 * unitScale,
        1,
        true);
    font.draw(
        game.getBatch(),
        tm.getMachineTimerForChef(2, gm.getChefs()),
        gm.getChef(3).getxCoord() * wscale,
        gm.getChef(3).getyCoord() * hscale + 2 * (hscale / 3f),
        32 * unitScale,
        1,
        true);
    game.getBatch()
        .draw(recipes, wscale * 0.1f, hscale * 0.1f, 32 * unitScale * 5f, 32 * unitScale * 2.5f);
    font.draw(
        game.getBatch(),
        tm.generateHoldingsText(gm.getChefs()),
        wscale * 8.25F,
        hscale * 11.75F,
        wscale * 6.5F,
        -1,
        true);
    font.draw(
        game.getBatch(),
        tm.generateCustomersTrayText(gm.getCustomers(), gm.getTray1(), gm.getTray2()),
        wscale * 15F,
        hscale * 11.75F,
        wscale * 6F,
        -1,
        true);
    font.draw(
        game.getBatch(),
        tm.generateTimerText(gm.getTotalTimerDisplay()),
        wscale * 15.25F,
        hscale * 3.75F,
        wscale * 6F,
        -1,
        true);
    font.draw(
        game.getBatch(),
        tm.generateReputationPointText(
            gm.getTotalTimer(), gm.getLastRepPointLost(), gm.getReputationPoints()),
        wscale * 15.25F,
        hscale * 4.25F,
        wscale * 6F,
        -1,
        true);
    font.draw(
        game.getBatch(),
        machineUnlockBalance.displayBalance(),
        wscale * 15.25F,
        hscale * 4.75F,
        wscale * 6F,
        -1,
        true);
    font.draw(
        game.getBatch(),
        gm.getPowerUpRunner().displayText(),
        wscale * 14.25F,
        hscale * 8.75F,
        wscale * 8F,
        -1,
        true);
    font.draw(
        game.getBatch(),
        tm.generateCustomerLeftText(gm.getTotalTimer(), gm.getLastRepPointLost()),
        wscale * 15.25F,
        hscale * 6.75F,
        wscale * 5.5F,
        -1,
        true);

    // Any machines that are unlockable add here to draw a lock on top of it.
    if (!(machineUnlockBalance.isUnlocked("chopping"))) {
      game.getBatch().draw(lock, 12 * wscale, 7 * hscale, 32 * unitScale, 32 * unitScale);
      font.draw(
          game.getBatch(),
          Float.toString(machineUnlockBalance.getUnlockPrice("chopping")),
          12 * wscale,
          7 * hscale);
    }
    if (!(machineUnlockBalance.isUnlocked("forming"))) {
      game.getBatch().draw(lock, 10 * wscale, 7 * hscale, 32 * unitScale, 32 * unitScale);
      font.draw(
          game.getBatch(),
          Float.toString(machineUnlockBalance.getUnlockPrice("forming")),
          10 * wscale,
          7 * hscale);
    }
    if (!(machineUnlockBalance.isUnlocked("grill"))) {
      game.getBatch().draw(lock, 7 * wscale, 7 * hscale, 32 * unitScale, 32 * unitScale);
      font.draw(
          game.getBatch(),
          Float.toString(machineUnlockBalance.getUnlockPrice("grill")),
          7 * wscale,
          7 * hscale);
    }
    if (!(machineUnlockBalance.isUnlocked("potato"))) {
      game.getBatch().draw(lock, 14 * wscale, 6 * hscale, 32 * unitScale, 32 * unitScale);
      font.draw(
          game.getBatch(),
          Float.toString(machineUnlockBalance.getUnlockPrice("potato")),
          14 * wscale,
          6 * hscale);
    }
    if (!(machineUnlockBalance.isUnlocked("pizza"))) {
      game.getBatch().draw(lock, 1 * wscale, 6 * hscale, 32 * unitScale, 32 * unitScale);
      font.draw(
          game.getBatch(),
          Float.toString(machineUnlockBalance.getUnlockPrice("pizza")),
          1 * wscale,
          6 * hscale);
    }
    if (!(machineUnlockBalance.isUnlocked("ingredients-staff"))) {
      game.getBatch().draw(lock, 2 * wscale, 7 * hscale, 32 * unitScale, 32 * unitScale);
      font.draw(
          game.getBatch(),
          Float.toString(machineUnlockBalance.getUnlockPrice("ingredients-staff")),
          2 * wscale,
          7 * hscale);
    }
    if (!(machineUnlockBalance.isUnlocked("server-staff"))) {
      game.getBatch().draw(lock, 1 * wscale, 3 * hscale, 32 * unitScale, 32 * unitScale);
      font.draw(
          game.getBatch(),
          Float.toString(machineUnlockBalance.getUnlockPrice("server-staff")),
          1 * wscale,
          3 * hscale);
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
              pairCoord.get(0) * wscale,
              pairCoord.get(1) * hscale,
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
    if (width == initialWidth && height == initialHeight) {
      super.resize(width, height);
      camera.setToOrtho(false, width, height);
      unitScale = Gdx.graphics.getHeight() / (12f * 32f);
      wscale = unitScale * 32f;
      hscale = unitScale * 32f;
      renderer = new OrthogonalTiledMapRenderer(map, unitScale);
    } else {
      Gdx.graphics.setWindowedMode(initialWidth, initialHeight);
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
    // Detects which key is pressed by the user
    // If WASD (movement keys) pressed, set appropriate variable to true for use in render method
    // If action key pressed, attempt to perform action
    if (keycode == Input.Keys.W) {
      wasd.set(0, true);
    }
    if (keycode == Input.Keys.A) {
      wasd.set(1, true);
    }
    if (keycode == Input.Keys.S) {
      wasd.set(2, true);
    }
    if (keycode == Input.Keys.D) {
      wasd.set(3, true);
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
    if (keycode == Input.Keys.ESCAPE) {
      try {
        gm.getSave().closeClass();
        game.setScreen(new IntroScreen(game));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return true;
  }

  /**
   * Method which runs when user lifts a key up.
   *
   * @param keycode one of the constants in {@link Input.Keys}.
   * @return false
   */
  @Override
  public boolean keyUp(int keycode) {
    // Detects which key is released by the user
    // If WASD (movement keys) released, set appropriate variable to false, stopping movement
    if (keycode == Input.Keys.W) {
      wasd.set(0, false);
    }
    if (keycode == Input.Keys.A) {
      wasd.set(1, false);
    }
    if (keycode == Input.Keys.S) {
      wasd.set(2, false);
    }
    if (keycode == Input.Keys.D) {
      wasd.set(3, false);
    }
    return false;
  }

  /**
   * UNUSED METHOD.
   *
   * @param character The character.
   * @return false
   */
  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  /**
   * UNUSED METHOD.
   *
   * @param screenX The x coordinate, origin is in the upper left corner.
   * @param screenY The y coordinate, origin is in the upper left corner.
   * @param pointer the pointer for the event.
   * @param button the button
   * @return false
   */
  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  /**
   * UNUSED METHOD.
   *
   * @param screenX The x coordinate, origin is in the upper left corner.
   * @param screenY The y coordinate, origin is in the upper left corner.
   * @param pointer the pointer for the event.
   * @param button the button.
   * @return false
   */
  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  /**
   * UNUSED METHOD.
   *
   * @param screenX The x coordinate, origin is in the upper left corner.
   * @param screenY The y coordinate, origin is in the upper left corner.
   * @param pointer the pointer for the event.
   * @return false
   */
  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  /**
   * UNUSED METHOD.
   *
   * @param screenX The x coordinate, origin is in the upper left corner.
   * @param screenY The y coordinate, origin is in the upper left corner.
   * @return false
   */
  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  /**
   * UNUSED METHOD.
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

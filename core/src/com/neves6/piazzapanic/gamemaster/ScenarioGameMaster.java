package com.neves6.piazzapanic.gamemaster;

import static com.neves6.piazzapanic.gamemechanisms.OrderMaster.checkOrderExpired;
import static com.neves6.piazzapanic.gamemechanisms.OrderMaster.createCustomers;
import static java.util.Arrays.asList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.neves6.piazzapanic.gamemechanisms.*;
import com.neves6.piazzapanic.people.Chef;
import com.neves6.piazzapanic.people.Customer;
import com.neves6.piazzapanic.powerups.PowerUpRunner;
import com.neves6.piazzapanic.screens.GameWinScreen;
import com.neves6.piazzapanic.screens.PiazzaPanicGame;
import com.neves6.piazzapanic.staff.DeliveryStaff;
import com.neves6.piazzapanic.staff.IngredientsStaff;
import java.util.*;

/** A class designed to handle all in game processing. */
public class ScenarioGameMaster {
  DeliveryStaff deliveryStaff;
  IngredientsStaff staffOne;
  PiazzaPanicGame game;
  TiledMapMaster map;
  TiledMapTileLayer collisionLayer;
  ArrayList<Chef> chefs = new ArrayList<>();
  Queue<Customer> customers = new LinkedList<>();
  Map<String, Machine> machines = new HashMap();
  Tray tray1 = new Tray();
  Tray tray2 = new Tray();
  int selectedChef;
  float totalTimer;
  Sound machineInteract;
  Sound serving;
  Sound fridge;
  Sound trash;
  float soundVolume;
  ArrayList<String> settings;
  Money machineUnlockBalance;
  ArrayList<String> recipes =
      new ArrayList<>(asList("salad", "hamburger", "jacket potato", "pizza"));
  int customersServed;
  int maxCustomers;
  int customersGenerated;
  float lastCustomer;
  Boolean isPowerUp;
  PowerUpRunner powerups;
  ReputationPoints reputationPoints = new ReputationPoints(3);
  float lastRepPointLost = 0;
  int difficulty;
  GameSaver save;
  float totalTimerDisplay;

  /**
   * ScenarioGameMaster constructor.
   *
   * @param game PiazzaPanicGame instance.
   * @param map TiledMap instance.
   * @param chefno Number of chefs.
   * @param custno Number of customers.
   * @param machineUnlockBalance A class that controls the in game currency.
   * @param ingredientsHelper Staff member which can get ingredients.
   * @param deliveryStaff Staff member who serves customers.
   * @param disablePowerup Turn power ups off.
   * @param difficulty Game difficulty setting.
   */
  public ScenarioGameMaster(
      PiazzaPanicGame game,
      TiledMap map,
      int chefno,
      int custno,
      Money machineUnlockBalance,
      IngredientsStaff ingredientsHelper,
      DeliveryStaff deliveryStaff,
      Boolean disablePowerup,
      int difficulty) {
    this.machineUnlockBalance = machineUnlockBalance;
    this.staffOne = ingredientsHelper;
    this.deliveryStaff = deliveryStaff;
    this.game = game;
    settings = Utility.getSettings();
    this.map = new TiledMapMaster(map);
    collisionLayer = (TiledMapTileLayer) map.getLayers().get(3);
    this.isPowerUp = !disablePowerup;
    this.difficulty = difficulty;

    this.save = new GameSaver("here.json");

    this.save.setDifficulty(difficulty);
    this.save.setPowerUp(disablePowerup);
    this.save.setCustomersRemaining(custno);

    for (int i = 0; i < chefno; i++) {
      chefs.add(new Chef("Chef", 6 + i, 5, 1, 1, 1, false, new Stack<String>(), i + 1));
    }
    this.maxCustomers = custno;

    totalTimer = 0f;
    totalTimerDisplay = 0f;

    // Assessment 1 (index 0-16)
    machines.put("fridge-meat", new Machine("fridge-meat", "", "meat", 0, false));
    machines.put("fridge-tomato", new Machine("fridge-tomato", "", "tomato", 0, false));
    machines.put("fridge-lettuce", new Machine("fridge-lettuce", "", "lettuce", 0, false));
    machines.put("fridge-onion", new Machine("fridge-onion", "", "onion", 0, false));
    machines.put("fridge-bun", new Machine("fridge-bun", "", "bun", 0, false));

    machineUnlockBalance.addGroup("grill", 100f);
    machines.put("grill-patty-1", new Machine("grill-patty-1", "patty", "burger", 3, true));
    machines.put(
        "grill-patty-2", new Machine("grill-patty-2", "patty", "burger", 3, true, "grill"));
    machines.put("grill-bun-1", new Machine("grill-bun-1", "bun", "toasted bun", 3, true));
    machines.put("grill-bun-2", new Machine("grill-bun-2", "bun", "toasted bun", 3, true, "grill"));

    machineUnlockBalance.addGroup("forming", 50f);
    machines.put("forming-1", new Machine("forming-1", "meat", "patty", 3, true));
    machines.put("forming-2", new Machine("forming-2", "meat", "patty", 3, true, "forming"));

    machineUnlockBalance.addGroup("chopping", 50f);
    machines.put(
        "chopping-tomato-1", new Machine("chopping-tomato-1", "tomato", "chopped tomato", 3, true));
    machines.put(
        "chopping-tomato-2",
        new Machine("chopping-tomato-2", "tomato", "chopped tomato", 3, true, "chopping"));
    machines.put(
        "chopping-lettuce-1",
        new Machine("chopping-lettuce-1", "lettuce", "chopped lettuce", 3, true));
    machines.put(
        "chopping-lettuce-2",
        new Machine("chopping-lettuce-2", "lettuce", "chopped lettuce", 3, true, "chopping"));
    machines.put(
        "chopping-onion-1", new Machine("chopping-onion-1", "onion", "chopped onion", 3, true));
    machines.put(
        "chopping-onion-2",
        new Machine("chopping-onion-2", "onion", "chopped onion", 3, true, "chopping"));

    // Assessment 2 (index 17-24)
    machines.put("fridge-dough", new Machine("fridge-dough", "", "dough", 0, false));
    machines.put("fridge-cheese", new Machine("fridge-cheese", "", "cheese", 0, false));
    machines.put("fridge-potato", new Machine("fridge-potato", "", "potato", 0, false));
    machines.put("fridge-beans", new Machine("fridge-beans", "", "beans", 0, false));

    machineUnlockBalance.addGroup("potato", 150f);
    machines.put("oven-potato-1", new Machine("oven-potato-1", "potato", "jacket", 3, true));
    machines.put(
        "oven-potato-2", new Machine("oven-potato-2", "potato", "jacket", 3, true, "potato"));

    machineUnlockBalance.addGroup("pizza", 150f);
    machines.put("oven-pizza-1", new Machine("oven-pizza-1", "raw pizza", "pizza", 3, true));
    machines.put(
        "oven-pizza-2", new Machine("oven-pizza-2", "raw pizza", "pizza", 3, true, "pizza"));

    // disposal and tray/serving handled separately
    serving = Gdx.audio.newSound(Gdx.files.internal("sounds/serving.mp3"));
    fridge = Gdx.audio.newSound(Gdx.files.internal("sounds/fridge.mp3"));
    machineInteract = Gdx.audio.newSound(Gdx.files.internal("sounds/charging-machine-90403.mp3"));
    trash = Gdx.audio.newSound(Gdx.files.internal("sounds/trash.mp3"));

    switch (settings.get(1).trim()) {
      case "full":
        soundVolume = 1f;
        break;
      case "half":
        soundVolume = 0.5f;
        break;
      case "none":
        soundVolume = 0f;
        break;
      default:
        break;
    }

    machineUnlockBalance.addGroup("ingredients-staff", 150f);
    machineUnlockBalance.addGroup("server-staff", 0f);

    this.powerups = new PowerUpRunner(chefs, machines, machineUnlockBalance, save);

    this.machineUnlockBalance.saveMoneyDetails(this.save);
  }

  /**
   * Setter method for selectedChef. Indexing is changed to represent java indexing starting from 0
   * but numbering for chefs starting from 1.
   *
   * @param selectedChef Index of the chef that movement needs to be applied on.
   */
  public void setSelectedChef(int selectedChef) {
    this.selectedChef = selectedChef - 1;
  }

  /**
   * Getter method for selectedChef. Indexing is changed to represent java indexing starting from 0
   * but numbering for chefs starting from 1.
   *
   * @return Index of the chef that movement will be applied on.
   */
  public int getSelectedChef() {
    return selectedChef + 1;
  }

  /**
   * Access method for any chef in the chefs array. Indexing is changed to represent java indexing
   * starting from 0 but numbering for chefs starting from 1.
   *
   * @param i ith position in the chefs array that needs to be accessed.
   * @return chef object based upon ith position passed in.
   */
  public Chef getChef(int i) {
    return chefs.get(i - 1);
  }

  /**
   * Used to set the current recipe to the ingredients staff member if this staff member has been
   * unlocked.
   */
  public void setRecipeToStaff() {
    if (machineUnlockBalance.isUnlocked("ingredients-staff")) {
      if (!customers.isEmpty()) {
        staffOne.setCurrentRecipe(customers.peek().getOrder());
      }
    }
  }

  /**
   * Attempts to move the currently selected chef in a specified direction. If the move is valid,
   * the facing variable for the currently selected chef will be set.
   *
   * @param direction Direction to move.
   */
  public void tryMove(String direction) {
    Chef chef = chefs.get(selectedChef);
    if (totalTimer < (chef.getLastMove() + 1F / 5F)) {
      return;
    }
    chef.setLastMove(totalTimer);
    switch (direction) {
      case "up":
        if (wouldNotCollide(chef.getxCoord(), chef.getyCoord() + 1, selectedChef)) {
          chef.alteryCoord(+1);
        }
        chef.setFacing("up");
        break;
      case "down":
        if (wouldNotCollide(chef.getxCoord(), chef.getyCoord() - 1, selectedChef)) {
          chef.alteryCoord(-1);
        }
        chef.setFacing("down");
        break;
      case "left":
        if (wouldNotCollide(chef.getxCoord() - 1, chef.getyCoord(), selectedChef)) {
          chef.alterxCoord(-1);
        }
        chef.setFacing("left");
        break;
      case "right":
        if (wouldNotCollide(chef.getxCoord() + 1, chef.getyCoord(), selectedChef)) {
          chef.alterxCoord(+1);
        }
        chef.setFacing("right");
        break;
      default:
        break;
    }
  }

  /**
   * Checks collision with the collision layer, other chefs, and being stickied.
   *
   * @param x x coordinate to check.
   * @param y y coordinate to check.
   * @param chefno chef number to check.
   * @return true if the chef would not collide, false otherwise.
   */
  public boolean wouldNotCollide(int x, int y, int chefno) {
    if (chefs.get(chefno).getIsStickied()) {
      return false;
    }
    for (int i = 0; i < chefs.size(); i++) {
      if (i != chefno && chefs.get(i).getxCoord() == x && chefs.get(i).getyCoord() == y) {
        return false;
      }
    }
    int tempCellTileID = collisionLayer.getCell(x, y).getTile().getId();
    return tempCellTileID != 37 && tempCellTileID != 39;
  }

  /**
   * Calculates size of customers array.
   *
   * @return The amount of customers which are yet to be served.
   */
  public int getCustomersRemaining() {
    return customers.size();
  }

  /**
   * Calculates the first customer at the start of the customers array.
   *
   * @return The customer which is at the start of the queue.
   */
  public Customer getFirstCustomer() {
    if (customers.size() > 0) {
      return customers.peek();
    } else {
      return null;
    }
  }

  /**
   * Updates all in game timers, performs any checks or functions required every frame. To be called
   * every frame render.
   *
   * @param delta time since last frame.
   */
  public void tickUpdate(float delta) {
    // TODO: play test and adjust difficulty scaling according to feedback

    lastRepPointLost =
        checkOrderExpired(
            customers,
            totalTimerDisplay,
            difficulty,
            reputationPoints,
            customersServed,
            lastRepPointLost);
    if (maxCustomers == -1 || (maxCustomers > 0 && customersGenerated < maxCustomers)) {
      lastCustomer =
          createCustomers(
              customers,
              lastCustomer,
              customersServed,
              customersGenerated,
              maxCustomers,
              difficulty,
              totalTimer);
    }

    if ((customersGenerated == maxCustomers && customers.size() == 0)
        || reputationPoints.getPoints() <= 0) {
      game.setScreen(
          new GameWinScreen(
              game, (int) totalTimerDisplay, false, (maxCustomers == -1), isPowerUp, difficulty));
    }

    float increment = powerups.updateValues(delta);

    for (String machine : machines.keySet()) {
      Machine tempMachine = machines.get(machine);
      if (tempMachine.getActive()) {
        tempMachine.incrementRuntime(delta);
        tempMachine.attemptGetOutput();
      }
    }

    // Delta is needed to move chefs.
    totalTimerDisplay += increment;
    totalTimer += delta;

    this.save.setChefDetails(chefs, selectedChef);
    this.save.setReputationPoints(reputationPoints.getPoints());
    this.save.setTime(totalTimerDisplay);
    this.save.setRecipe(getFirstCustomer());
    this.save.setTrays(tray1.getList(), tray2.getList());
  }

  /**
   * Attempts to cause an interaction between the currently selected chef and the machine in front
   * of them.
   */
  public void tryInteract() {
    Chef chef = chefs.get(selectedChef);

    int targetx;
    int targety;
    switch (chef.getFacing()) {
      case "up":
        targetx = chef.getxCoord();
        targety = chef.getyCoord() + 1;
        break;
      case "down":
        targetx = chef.getxCoord();
        targety = chef.getyCoord() - 1;
        break;
      case "left":
        targetx = chef.getxCoord() - 1;
        targety = chef.getyCoord();
        break;
      default: // Right
        targetx = chef.getxCoord() + 1;
        targety = chef.getyCoord();
        break;
    }

    // Unlock layer - interactions with any stations that need to be
    // purchased using credits.
    MapObjects unlockObjects = map.getObjectLayers("Unlock Layer");

    for (MapObject ob : unlockObjects) {
      if (map.detectInteractionFromTiledObject(map.loadRectangle(ob), targetx, targety)) {
        machineUnlockBalance.unlockMachine(ob.getName());
      }
    }

    // Fridge layer - contains all fridges in order to pick up ingredients.
    MapObjects fridgeObjects = map.getObjectLayers("Fridge Layer");
    for (MapObject ob : fridgeObjects) {
      if (map.detectInteractionFromTiledObject(map.loadRectangle(ob), targetx, targety)) {
        machines.get(ob.getName()).process(chef, machineUnlockBalance);
        fridge.play(soundVolume);
      }
    }

    // Cooking Objects - contain all machines
    MapObjects cookingObjects = map.getObjectLayers("Cooking Layer");

    String invTop = "";
    if (chef.getInventory().size() != 0) {
      // Work stations
      invTop = chef.getInventory().peek();
    }

    for (MapObject ob : cookingObjects) {
      // Only use a machine if you are trying to interact with it and have the correct
      // input.
      if (map.detectInteractionFromTiledObject(map.loadRectangle(ob), targetx, targety)
          && machines.get(ob.getName()) == chef.getMachineInteractingWith()) {
        machines.get(ob.getName()).attemptCompleteAction();
        machineInteract.play(soundVolume);
      } else if (map.detectInteractionFromTiledObject(map.loadRectangle(ob), targetx, targety)
          && chef.getMachineInteractingWith() == null
          && machines.get(ob.getName()).getInput().equals(invTop)) {
        machines.get(ob.getName()).process(chef, machineUnlockBalance);
        machineInteract.play(soundVolume);
      }
    }

    MapObjects miscObjects = map.getObjectLayers("Misc Layer");

    if (map.detectInteractionFromTiledObject(
        map.loadRectangle(miscObjects.get("bin")), targetx, targety)) {
      if (!chef.getInventory().isEmpty()) {
        chef.removeTopFromInventory();
        trash.play(soundVolume);
      }
    } else if (map.detectInteractionFromTiledObject(
        map.loadRectangle(miscObjects.get("serving")), targetx, targety)) {
      serveFood();
    } else if (map.detectInteractionFromTiledObject(
        map.loadRectangle(miscObjects.get("tray-1")), targetx, targety)) {
      tray1.addToTray(chefs.get(selectedChef), deliveryStaff, customers, machineUnlockBalance);
      if (machineUnlockBalance.isUnlocked("server-staff")
              && !(customers.peek().getOrder().equals("pizza"))) {
        serveFood();
      }
    } else if (map.detectInteractionFromTiledObject(
        map.loadRectangle(miscObjects.get("tray-2")), targetx, targety)) {
      tray2.addToTray(chefs.get(selectedChef), deliveryStaff, customers, machineUnlockBalance);
      if (machineUnlockBalance.isUnlocked("server-staff")
              && !(customers.peek().getOrder().equals("pizza"))) {
        serveFood();
      }
    } else if (map.detectInteractionFromTiledObject(
        map.loadRectangle(miscObjects.get("fast-track-collect")), targetx, targety)) {
      String item = staffOne.collectItem();
      // Don't add a null pointer onto the chefs stack.
      if (item != null) {
        Machine tempMachine = new Machine("staff", item, item, 0, true, "ingredients-staff");
        tempMachine.processStaffInteraction(chef, machineUnlockBalance);
      }
    }
  }

  /**
   * Getter method for the money class.
   *
   * @return Instance of the money class that is being used within scenario game master.
   */
  public Money getUnlockClass() {
    return machineUnlockBalance;
  }

  /** Method to handle giving food to the customer. */
  public void serveFood() {
    if (customers.isEmpty()) {
      return;
    }
    Chef chef = chefs.get(selectedChef);
    Stack<String> inv;
    // If the order isn't pizza and the server is unlocked,
    // get the order from the staff members inventory.
    // Otherwise, get the chefs inventory and operate
    // from there.
    if (machineUnlockBalance.isUnlocked("server-staff")
        && !(customers.peek().getOrder().equals("pizza"))) {
      inv = deliveryStaff.getItems();
    } else {
      inv = chef.getInventory();
    }

    if (inv.size() == 0) {
      return;
    }

    if (customers.peek().getOrder().equals(inv.peek())) {
      inv.pop();
      customers.poll();
      customersServed += 1;
      this.save.decrementCustomers();
      serving.play(soundVolume);

      if (customersServed == maxCustomers) {
        game.setScreen(
            new GameWinScreen(
                game, (int) totalTimerDisplay, true, (maxCustomers == -1), isPowerUp, 0));
      }

      // +$100 on completion of a recipe.
      machineUnlockBalance.incrementBalance();
      // Once an order is complete, allow ingredient staff to
      // collect another order.
      staffOne.setGenerate(true);
      this.machineUnlockBalance.saveMoneyDetails(this.save);

      // Activate random power up if a recipe is complete.
      if (isPowerUp) {
        this.powerups.activateRandomPowerUp();
      }
    }
  }

  /**
   * Getter method for the game's PowerUpRunner instance.
   *
   * @return Instance of PowerUpRunner used in the game.
   */
  public PowerUpRunner getPowerUpRunner() {
    return powerups;
  }

  /**
   * Getter method for the game's GameSaver instance.
   *
   * @return Instance of GameSaver used in the game.
   */
  public GameSaver getSave() {
    return save;
  }

  /**
   * Setter method for reputation points.
   *
   * @param overwriteValue Number of reputation points.
   */
  public void setReputationPoints(int overwriteValue) {
    reputationPoints.overwritePoints(overwriteValue);
  }

  /**
   * Setter method for game runtime.
   *
   * @param timeElapsed Amount of time the game has been running.
   */
  public void setTimeElapsed(float timeElapsed) {
    this.totalTimer = timeElapsed;
    this.totalTimerDisplay = timeElapsed;
  }

  /**
   * Getter method for game runtime.
   *
   * @return Amount of time the game has been running.
   */
  public float getTimer() {
    return totalTimerDisplay;
  }

  /**
   * Adds given item to a specified tray. Bypasses order completion checks, only use with
   * GameReader.
   *
   * @param number Number of the tray being added to.
   * @param item Name of item being added to the tray.
   */
  public void addtoTray(int number, String item) {
    if (number == 1) {
      tray1.putOnTray(item);
    } else if (number == 2) {
      tray2.putOnTray(item);
    }
  }

  public ArrayList<Chef> getChefs() {
    return chefs;
  }

  public float getTotalTimerDisplay() {
    return totalTimerDisplay;
  }

  public float getTotalTimer() {
    return totalTimer;
  }

  public float getLastRepPointLost() {
    return lastRepPointLost;
  }

  public int getReputationPoints() {
    return reputationPoints.getPoints();
  }

  public Queue<Customer> getCustomers() {
    return customers;
  }

  public ArrayList<String> getTray1() {
    return tray1.getList();
  }

  public ArrayList<String> getTray2() {
    return tray2.getList();
  }

  public IngredientsStaff getIngredientsStaff() { return staffOne; }
}

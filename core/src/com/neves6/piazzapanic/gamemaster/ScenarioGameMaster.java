package com.neves6.piazzapanic.gamemaster;

import static java.util.Arrays.asList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.neves6.piazzapanic.gamemechanisms.GameSaver;
import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.gamemechanisms.Utility;
import com.neves6.piazzapanic.people.Chef;
import com.neves6.piazzapanic.people.Customer;
import com.neves6.piazzapanic.powerups.PowerUpRunner;
import com.neves6.piazzapanic.screens.GameWinScreen;
import com.neves6.piazzapanic.screens.PiazzaPanicGame;
import com.neves6.piazzapanic.staff.DeliveryStaff;
import com.neves6.piazzapanic.staff.IngredientsStaff;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/** A class designed to handle all in game processing. */
public class ScenarioGameMaster extends GameMaster {
  int tileWidth;
  DeliveryStaff deliveryStaff;
  IngredientsStaff staffOne;
  PiazzaPanicGame game;
  TiledMap map;
  TiledMapTileLayer collisionLayer;
  ArrayList<Chef> chefs = new ArrayList<>();
  Queue<Customer> customers = new LinkedList<>();
  Map<String, Machine> machines = new HashMap();
  ArrayList<String> tray1 = new ArrayList<>();
  ArrayList<String> tray2 = new ArrayList<>();
  int selectedChef;
  float totalTimer;
  Sound grill;
  Sound chopping;
  Sound serving;
  Sound fridge;
  Sound forming;
  Sound trash;
  float soundVolume;
  ArrayList<String> settings;
  Money machineUnlockBalance;
  ArrayList<String> recipes =
      new ArrayList<>(asList("salad", "hamburger", "jacket potato", "pizza"));
  int customersServed;
  int maxCustomers;
  int customersGenerated;
  int timeAllowed;
  float lastCustomer;
  float waitTime;
  Boolean isPowerUp;
  PowerUpRunner powerups;
  int reputationPoints = 3;
  float lastRepPointLost = 0;
  int difficulty;
  GameSaver save;
  ArrayList<String> salad =
      new ArrayList<>(Arrays.asList("chopped tomato", "chopped onion", "chopped lettuce"));
  ArrayList<String> jacketPotato = new ArrayList<>(Arrays.asList("jacket", "beans"));
  ArrayList<String> hamburger = new ArrayList<>(Arrays.asList("burger", "toasted bun"));
  ArrayList<String> rawPizza = new ArrayList<>(Arrays.asList("chopped tomato", "dough", "cheese"));
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
    this.map = map;
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

    grill = Gdx.audio.newSound(Gdx.files.internal("sounds/grill.mp3"));
    chopping = Gdx.audio.newSound(Gdx.files.internal("sounds/chopping.mp3"));
    serving = Gdx.audio.newSound(Gdx.files.internal("sounds/serving.mp3"));
    fridge = Gdx.audio.newSound(Gdx.files.internal("sounds/fridge.mp3"));
    forming = Gdx.audio.newSound(Gdx.files.internal("sounds/forming.mp3"));
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
    machineUnlockBalance.addGroup("server-staff", 50f);

    // It is a square hence, width = height, just get one.
    tileWidth = (int) map.getProperties().get("tilewidth");

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
    if (!game.getTestMode() && totalTimer < (chef.getLastMove() + 1F / 5F)) {
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

    checkOrderExpired();
    if (maxCustomers == -1 || (maxCustomers > 0 && customersGenerated < maxCustomers)) {
      createCustomers();
    }

    if ((customersGenerated == maxCustomers && customers.size() == 0) || reputationPoints <= 0) {
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
    this.save.setReputationPoints(reputationPoints);
    this.save.setTime(totalTimerDisplay);
    this.save.setRecipe(getFirstCustomer());
    this.save.setTrays(tray1, tray2);
  }

  /**
   * Checks if time allowed to complete any customer orders has elapsed Removes customers whose
   * order expired and removes a reputation point
   */
  private void checkOrderExpired() {
    timeAllowed = Math.max(150 - 15 * (customersServed / 5), 90) - (5 * difficulty);
    for (int i = 0; i < customers.size(); i++) {
      if (customers.peek().getTimeArrived() + timeAllowed < totalTimerDisplay) {
        customers.poll();
        reputationPoints -= 1;
        lastRepPointLost = totalTimerDisplay;
      }
    }
  }

  /**
   * Creates 1-3 customers, initially skewed towards 1 but favours 3 as the total number of
   * customers served increases Will occasionally 0.5s stalls to vary customer arrival times
   */
  public void createCustomers() {
    waitTime = (float) Math.max(2.5 - 0.5 * (customersServed / 5F), 0.5);
    if (lastCustomer + waitTime <= totalTimer) {
      /*
      Random chance to stall next customer's arrival to vary customer arrival times
      Stalls scaled by difficulty to make harder difficulties slightly faster paced
      Chance to stall and time stalled by difficulty:
          Easy    30%   1s
          Medium  20%   0.75s
          Hard    10%   0.5s
      */

      float randomFloat = ThreadLocalRandom.current().nextFloat();
      if (customers.size() != 0 && randomFloat > (0.9 - 0.1 * (3 - difficulty))) {
        lastCustomer += 0.5 + 0.25 * (3 - difficulty);
      } else {
        int partySize = generatePartySize();
        for (int i = 0; i < partySize; i++) {
          // Max number of customers in the queue starts at 5, increases by 1 every 5 served
          // Queue caps at 10 customers
          if (customers.size() < Math.min(5 + (customersServed / 5), 10)
              && (maxCustomers == -1 || (maxCustomers > 0 && customersGenerated < maxCustomers))) {
            int randomInt = ThreadLocalRandom.current().nextInt(0, 4);
            customers.add(
                new Customer(
                    "Customer" + (customers.size() + 1),
                    -1,
                    -1,
                    recipes.get(randomInt),
                    totalTimer));
            customersGenerated += 1;
          } else {
            break;
          }
        }
        lastCustomer = totalTimer;
      }
    }
  }

  /**
   * Randomly generates a value 1 to 3 dependent on the number of customers served to be used as
   * group sizes Initially biased towards 1 but gradually shifts in favour of 3
   *
   * @return integer value 1 to 3
   */
  private int generatePartySize() {
    /*
    Creates a random party size of 1-3 customers
    The more customers that have been served, the more likely a larger group becomes
    Probabilities of group size for each interval of customers served:
        0-4:    1 = 80%,    2 = 20%,    3 = 0%
        5-9:    1 = 40%,    2 = 40%,    3 = 20%
        10-14:  1 = 0%,     2 = 60%,    3 = 40%
        15-19:  1 = 0%,     2 = 40%,    3 = 60%
        20-24:  1 = 0%,     2 = 20%,    3 = 80%
        25+:    1 = 0%,     2 = 0%,     3 = 100%
    */
    float randomFloat = ThreadLocalRandom.current().nextFloat();
    int partySize;
    if (randomFloat <= (0.8 - 0.4 * (customersServed / 5F))) {
      partySize = 1;
    } else if (randomFloat > (0.8 - 0.4 * (customersServed / 5F))
        && randomFloat <= (1 - 0.2 * (customersServed / 5F))) {
      partySize = 2;
    } else {
      partySize = 3;
    }
    return partySize;
  }

  /**
   * Helper method used to get the objects from a layer using its key.
   *
   * @param key Name of the layer.
   * @return All objects from the layer indicated by the key.
   */
  public MapObjects getObjectLayers(String key) {
    MapLayer unlockLayer = map.getLayers().get(key);
    return unlockLayer.getObjects();
  }

  /**
   * Converts the tiled map coordinates into the game coordinates then compares it to the target x
   * and target y, to check whether the rectangle is being interacted with.
   *
   * @param object A tiled map representation of an point on the map.
   * @param xcoord A game x coordinate.
   * @param ycoord A game y coordinate.
   * @return Boolean value representing whether the coordinates passed in are the coordinates of the
   *     rectangle passed in.
   */
  public Boolean detectInteractionFromTiledObject(Rectangle object, int xcoord, int ycoord) {
    return xcoord == Math.round(object.getX() / tileWidth)
        && ycoord == Math.round(object.getY() / tileWidth);
  }

  /**
   * Helper method to convert a map object to a rectangle map object.
   *
   * @param object A single part of a layer of the map.
   * @return Returns a rectangle representing the object.
   */
  public Rectangle loadRectangle(MapObject object) {
    RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
    // Now you can get the position of the rectangle like this:
    return rectangleMapObject.getRectangle();
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
      case "right":
        targetx = chef.getxCoord() + 1;
        targety = chef.getyCoord();
        break;
      default:
        targetx = chef.getxCoord();
        targety = chef.getyCoord();
        break;
    }

    // Unlock layer - interactions with any stations that need to be
    // purchased using credits.
    MapObjects unlockObjects = getObjectLayers("Unlock Layer");

    for (MapObject ob : unlockObjects) {
      if (detectInteractionFromTiledObject(loadRectangle(ob), targetx, targety)) {
        machineUnlockBalance.unlockMachine(ob.getName());
      }
    }

    // Fridge layer - contains all fridges in order to pick up ingredients.
    MapObjects fridgeObjects = getObjectLayers("Fridge Layer");
    for (MapObject ob : fridgeObjects) {
      if (detectInteractionFromTiledObject(loadRectangle(ob), targetx, targety)) {
        machines.get(ob.getName()).process(chef, machineUnlockBalance);
        fridge.play(soundVolume);
      }
    }

    // Cooking Objects - contain all machines
    MapObjects cookingObjects = getObjectLayers("Cooking Layer");

    String invTop = "";
    if (chef.getInventory().size() != 0) {
      // Work stations
      invTop = chef.getInventory().peek();
    }

    for (MapObject ob : cookingObjects) {
      // Only use a machine if you are trying to interact with it and have the correct
      // input.
      if (detectInteractionFromTiledObject(loadRectangle(ob), targetx, targety)
          && machines.get(ob.getName()) == chef.getMachineInteractingWith()) {
        machines.get(ob.getName()).attemptCompleteAction();
      } else if (detectInteractionFromTiledObject(loadRectangle(ob), targetx, targety)
          && chef.getMachineInteractingWith() == null
          && machines.get(ob.getName()).getInput().equals(invTop)) {
        machines.get(ob.getName()).process(chef, machineUnlockBalance);
      }
    }

    MapObjects miscObjects = getObjectLayers("Misc Layer");

    if (detectInteractionFromTiledObject(loadRectangle(miscObjects.get("bin")), targetx, targety)) {
      if (!chef.getInventory().isEmpty()) {
        chef.removeTopFromInventory();
        trash.play(soundVolume);
      }
    } else if (detectInteractionFromTiledObject(
        loadRectangle(miscObjects.get("serving")), targetx, targety)) {
      serveFood();
    } else if (detectInteractionFromTiledObject(
        loadRectangle(miscObjects.get("tray-1")), targetx, targety)) {
      addToTray(1);
    } else if (detectInteractionFromTiledObject(
        loadRectangle(miscObjects.get("tray-2")), targetx, targety)) {
      addToTray(2);
    } else if (detectInteractionFromTiledObject(
        loadRectangle(miscObjects.get("fast-track-collect")), targetx, targety)) {
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

  /**
   * Adds the top item from the currently selected chef's inventory to the tray.
   *
   * @param station Indicates which tray station is being used.
   */
  private void addToTray(int station) {
    Chef chef = chefs.get(selectedChef);
    Stack<String> inv = chef.getInventory();
    ArrayList<String> tray;
    if (station == 1) {
      tray = tray1;
    } else if (station == 2) {
      tray = tray2;
    } else {
      return;
    }

    if (inv.isEmpty()
        || recipes.contains(inv.peek())
        || inv.peek().equals("raw pizza")
        || inv.peek().contains("ruined")) {
      return;
    }

    tray.add(inv.pop());
    // Pizza cannot be handled by staff because you need to put it in the oven then
    // take it to the customer.
    if (tray.containsAll(rawPizza)) {
      if (tray.size() != rawPizza.size()) {
        inv.add("ruined raw pizza");
      } else {
        inv.add("raw pizza");
      }
      tray.clear();
      serving.play(soundVolume);
    } else if (tray.containsAll(salad)) {
      if (tray.size() != salad.size()) {
        inv.add("ruined salad");
      } else {
        inv.add("salad");
      }
      tray.clear();
      serving.play(soundVolume);
    } else if (tray.containsAll(hamburger)) {
      if (tray.size() != hamburger.size()) {
        inv.add("ruined hamburger");
      } else {
        inv.add("hamburger");
      }
      tray.clear();
      serving.play(soundVolume);
    } else if (tray.containsAll(jacketPotato)) {
      if (tray.size() != jacketPotato.size()) {
        inv.add("ruined jacket potato");
      } else {
        inv.add("jacket potato");
      }
      tray.clear();
      serving.play(soundVolume);
    }

    if (machineUnlockBalance.isUnlocked("server-staff")
        && customers.size() > 0
        && !inv.isEmpty()
        && customers.peek().getOrder().equals(inv.peek())) {
      deliveryStaff.collectItem(customers.peek().getOrder());
      inv.pop();
      serveFood();
    }

    if (station == 1) {
      tray1 = tray;
    } else if (station == 2) {
      tray2 = tray;
    }
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
            new GameWinScreen(game, (int) totalTimerDisplay, true, (maxCustomers == -1), isPowerUp, 0));
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
   * @param reputationPoints Number of reputation points.
   */
  public void setReputationPoints(int reputationPoints) {
    this.reputationPoints = reputationPoints;
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
      tray1.add(item);
    } else if (number == 2) {
      tray2.add(item);
    }
  }

  public ArrayList<Chef> getChefs() {
    return chefs;
  }

  public float getTotalTimerDisplay() {
    return totalTimerDisplay;
  }

  public float getLastRepPointLost() {
    return lastRepPointLost;
  }

  public int getReputationPoints() {
    return reputationPoints;
  }

  public Queue<Customer> getCustomers() {
    return customers;
  }

  public ArrayList<String> getTray1() {
    return tray1;
  }

  public ArrayList<String> getTray2() {
    return tray2;
  }
}

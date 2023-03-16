package com.neves6.piazzapanic;

import static java.util.Arrays.asList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.neves6.piazzapanic.staff.DeliveryStaff;
import com.neves6.piazzapanic.staff.IngredientsStaff;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class ScenarioGameMaster extends GameMaster {
  DeliveryStaff deliveryStaff;
  IngredientsStaff staffOne;
  PiazzaPanicGame game;
  TiledMap map;
  TiledMapTileLayer collisionLayer;
  ArrayList<Chef> chefs = new ArrayList<>();
  Stack<Customer> customers = new Stack<>();
  ArrayList<Machine> machines = new ArrayList<>();
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

  /**
   * ScenarioGameMaster constructor.
   *
   * @param game PiazzaPanicGame instance.
   * @param map TiledMap instance.
   * @param chefno Number of chefs.
   * @param custno Number of customers.
   * @param machineUnlockBalance A class that controls the in game currency.
   * @param ingredientsHelper Staff member which can get ingredients.
   */
  public ScenarioGameMaster(
      PiazzaPanicGame game,
      TiledMap map,
      int chefno,
      int custno,
      Money machineUnlockBalance,
      IngredientsStaff ingredientsHelper,
      DeliveryStaff deliveryStaff) {
    this.machineUnlockBalance = machineUnlockBalance;
    this.staffOne = ingredientsHelper;
    this.deliveryStaff = deliveryStaff;
    this.game = game;
    settings = Utility.getSettings();
    this.map = map;
    collisionLayer = (TiledMapTileLayer) map.getLayers().get(3);
    for (int i = 0; i < chefno; i++) {
      chefs.add(new Chef("Chef", 6 + i, 5, 1, 1, 1, false, new Stack<String>(), i + 1));
    }
    for (int i = 0; i < custno; i++) {
      int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
      customers.add(new Customer("Customer" + (i + 1), -1, -1, recipes.get(randomNum)));
    }
    totalTimer = 0f;

    // Assessment 1 (index 0-16)
    machines.add(new Machine("fridge-meat", "", "meat", 0, false));
    machines.add(new Machine("fridge-tomato", "", "tomato", 0, false));
    machines.add(new Machine("fridge-lettuce", "", "lettuce", 0, false));
    machines.add(new Machine("fridge-onion", "", "onion", 0, false));
    machines.add(new Machine("fridge-bun", "", "bun", 0, false));

    machineUnlockBalance.addGroup("grill", 100);
    machines.add(new Machine("grill-patty-1", "patty", "burger", 3, true));
    machines.add(new Machine("grill-patty-2", "patty", "burger", 3, true, "grill"));
    machines.add(new Machine("grill-bun-1", "bun", "toasted bun", 3, true));
    machines.add(new Machine("grill-bun-2", "bun", "toasted bun", 3, true, "grill"));

    machineUnlockBalance.addGroup("forming", 50);
    machines.add(new Machine("forming-1", "meat", "patty", 3, true));
    machines.add(new Machine("forming-2", "meat", "patty", 3, true, "forming"));

    machineUnlockBalance.addGroup("chopping", 50);
    machines.add(new Machine("chopping-tomato-1", "tomato", "chopped tomato", 3, true));
    machines.add(new Machine("chopping-tomato-2", "tomato", "chopped tomato", 3, true, "chopping"));
    machines.add(new Machine("chopping-lettuce-1", "lettuce", "chopped lettuce", 3, true));
    machines.add(
        new Machine("chopping-lettuce-2", "lettuce", "chopped lettuce", 3, true, "chopping"));
    machines.add(new Machine("chopping-onion-1", "onion", "chopped onion", 3, true));
    machines.add(new Machine("chopping-onion-2", "onion", "chopped onion", 3, true, "chopping"));

    // Assessment 2 (index 17-24)
    machines.add(new Machine("fridge-dough", "", "dough", 0, false));
    machines.add(new Machine("fridge-cheese", "", "cheese", 0, false));
    machines.add(new Machine("fridge-potato", "", "potato", 0, false));
    machines.add(new Machine("fridge-beans", "", "beans", 0, false));

    machineUnlockBalance.addGroup("potato", 150);
    machines.add(new Machine("oven-potato-1", "potato", "jacket", 3, true));
    machines.add(new Machine("oven-potato-2", "potato", "jacket", 3, true, "potato"));

    machineUnlockBalance.addGroup("pizza", 150);
    machines.add(new Machine("oven-pizza-1", "raw pizza", "pizza", 3, true));
    machines.add(new Machine("oven-pizza-2", "raw pizza", "pizza", 3, true, "pizza"));

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

    machineUnlockBalance.addGroup("ingredients-staff", 150);
    machineUnlockBalance.addGroup("server-staff", 50);
  }

  public void setSelectedChef(int selectedChef) {
    this.selectedChef = selectedChef - 1;
  }

  public int getSelectedChef() {
    return selectedChef + 1;
  }

  public Chef getChef(int i) {
    return chefs.get(i - 1);
  }

  public void setRecipeToStaff() {
    if (machineUnlockBalance.isUnlocked("ingredients-staff")) {
      staffOne.setCurrentRecipe(customers.get(0).getOrder());
    }
  }

  /**
   * Attempts to move the currently selected chef in a specified direction.
   *
   * @param direction Direction to move.
   */
  public void tryMove(String direction) {
    Chef chef = chefs.get(selectedChef);
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
  private boolean wouldNotCollide(int x, int y, int chefno) {
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
   * Generates the display text for the chefs' inventories.
   *
   * @return String containing the display text.
   */
  public String generateHoldingsText() {
    String comp = "";
    for (int i = 0; i < chefs.size(); i++) {
      comp += "Chef " + (i + 1) + " is holding:\n";
      comp += chefs.get(i).getInventory().toString() + "\n";
    }
    return comp;
  }

  /**
   * Generates the display text for the customers' tray and order.
   *
   * @return String containing the display text.
   */
  public String generateCustomersTrayText() {
    String comp = "";
    comp += "Customers remaining: ";
    comp += customers.size();
    if (customers.size() > 0) {
      comp += "\nOrder: ";
      comp += customers.get(0).getOrder();
    }
    comp += "\nTray 1 contents: ";
    comp += tray1.toString();
    comp += "\nTray 2 contents: ";
    comp += tray2.toString();
    return comp;
  }

  /**
   * Generates the display text for the timer.
   *
   * @return String containing the display text.
   */
  public String generateTimerText() {
    String comp = "";
    comp += "Time elapsed: ";
    comp += (int) totalTimer;
    comp += " s";
    return comp;
  }

  /**
   * Generates the display text for the chef's timer.
   *
   * @param chefno chef number to check.
   * @return String containing the display text.
   */
  public String getMachineTimerForChef(int chefno) {
    Chef chef = chefs.get(chefno);
    if (chef.getMachineInteractingWith() != null) {
      Machine machine = chef.getMachineInteractingWith();
      return ((int) (machine.getProcessingTime() - machine.getRuntime() + 1)) + "";
    } else {
      return "";
    }
  }

  public int getCustomersRemaining() {
    return customers.size();
  }

  public Customer getFirstCustomer() {
    return customers.get(0);
  }

  /**
   * Updates timers on all machines and the total timer. To be called every frame render.
   *
   * @param delta time since last frame.
   */
  public void tickUpdate(float delta) {
    // TODO: Use increment variable to handle powerup -
    // just use get delta everytime.
    float increment = delta;
    for (Machine machine : machines) {
      if (machine.getActive()) {
        machine.incrementRuntime(delta);
        machine.attemptGetOutput();
      }
    }
    totalTimer += increment;
  }

  /**
   * Attempts to cause an interaction between the currently selected chef and the machine in front
   * of them.
   */
  public void tryInteract() {
    Chef chef = chefs.get(selectedChef);
    if (chef.getIsStickied()) {
      return;
    }
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

    // Unlock machines even if you don't have anything in your stack.
    if (targetx == 10 && targety == 7) {
      machineUnlockBalance.unlockMachine("forming");
    } else if (targetx == 12 && targety == 7) {
      machineUnlockBalance.unlockMachine("chopping");
    } else if (targetx == 7 && targety == 7) {
      machineUnlockBalance.unlockMachine("grill");
    } else if (targetx == 14 && targety == 6) {
      machineUnlockBalance.unlockMachine("potato");
    } else if (targetx == 1 && targety == 6) {
      machineUnlockBalance.unlockMachine("pizza");
    } else if (targetx == 2 && targety == 7) {
      machineUnlockBalance.unlockMachine("ingredients-staff");
    } else if (targetx == 1 && targety == 3) {
      machineUnlockBalance.unlockMachine("server-staff");
    }

    // Staff collects items.
    if (targetx == 2 && targety == 7 && chefs.get(selectedChef).getInventory().size() == 0) {
      String item = staffOne.collectItem();
      // Don't add a null pointer onto the chefs stack.
      if (item != null) {
        Machine tempMachine = new Machine("staff", item, item, 0, true, "ingredients-staff");
        tempMachine.processStaffInteraction(chef, machineUnlockBalance);
      }
    }

    if (chef.getInventory().empty()) {
      if (targetx == 1 && targety == 10) {
        machines.get(0).process(chef, machineUnlockBalance);
        fridge.play(soundVolume);
      } else if (targetx == 2 && targety == 10) {
        machines.get(1).process(chef, machineUnlockBalance);
        fridge.play(soundVolume);
      } else if (targetx == 3 && targety == 10) {
        machines.get(2).process(chef, machineUnlockBalance);
        fridge.play(soundVolume);
      } else if (targetx == 4 && targety == 10) {
        machines.get(3).process(chef, machineUnlockBalance);
        fridge.play(soundVolume);
      } else if (targetx == 1 && targety == 8) {
        machines.get(4).process(chef, machineUnlockBalance);
        fridge.play(soundVolume);
      } else if (targetx == 4 && targety == 8) {
        machines.get(17).process(chef, machineUnlockBalance);
        fridge.play(soundVolume);
      } else if (targetx == 5 && targety == 10) {
        machines.get(18).process(chef, machineUnlockBalance);
        fridge.play(soundVolume);
      } else if (targetx == 6 && targety == 10) {
        machines.get(19).process(chef, machineUnlockBalance);
        fridge.play(soundVolume);
      } else if (targetx == 7 && targety == 10) {
        machines.get(20).process(chef, machineUnlockBalance);
        fridge.play(soundVolume);
      } else {
        return;
      }
    }

    // Work stations
    String invTop = chef.getInventory().peek();
    if (targetx == 6 && targety == 7) {
      if (invTop == "patty") {
        machines.get(5).process(chef, machineUnlockBalance);
        grill.play(soundVolume);
      } else if (invTop == "bun") {
        machines.get(7).process(chef, machineUnlockBalance);
        grill.play(soundVolume);
      }
    } else if (targetx == 7 && targety == 7) {
      if (invTop == "patty") {
        machines.get(6).process(chef, machineUnlockBalance);
        grill.play(soundVolume);
      } else if (invTop == "bun") {
        machines.get(8).process(chef, machineUnlockBalance);
        grill.play(soundVolume);
      }
      // Forming stations.
    } else if (targetx == 9 && targety == 7) {
      machines.get(9).process(chef, machineUnlockBalance);
      forming.play(soundVolume);
    } else if (targetx == 10 && targety == 7) {
      machines.get(10).process(chef, machineUnlockBalance);
      forming.play(soundVolume);
    } else if (targetx == 11 && targety == 7) {
      if (invTop == "tomato") {
        machines.get(11).process(chef, machineUnlockBalance);
        chopping.play(soundVolume);
      } else if (invTop == "lettuce") {
        machines.get(13).process(chef, machineUnlockBalance);
        chopping.play(soundVolume);
      } else if (invTop == "onion") {
        machines.get(15).process(chef, machineUnlockBalance);
        chopping.play(soundVolume);
      }
    } else if (targetx == 12 && targety == 7) {
      if (invTop == "tomato") {
        machines.get(12).process(chef, machineUnlockBalance);
      } else if (invTop == "lettuce") {
        machines.get(14).process(chef, machineUnlockBalance);
      } else if (invTop == "onion") {
        machines.get(16).process(chef, machineUnlockBalance);
      }
    } else if (targetx == 14 && targety == 4) {
      chef.removeTopFromInventory();
      trash.play(soundVolume);
    } else if (targetx == 8 && targety == 3) {
      serveFood();
    } else if (targetx == 2 && targety == 3) {
      addToTray(1);
    } else if (targetx == 3 && targety == 3) {
      addToTray(2);
    } else if (targetx == 14 && targety == 5) {
      if (invTop == "potato") {
        machines.get(21).process(chef, machineUnlockBalance);
        grill.play(soundVolume);
      }
    } else if (targetx == 14 && targety == 6) {
      if (invTop == "potato") {
        machines.get(22).process(chef, machineUnlockBalance);
        grill.play(soundVolume);
      }
    } else if (targetx == 1 && targety == 5) {
      if (invTop == "raw pizza") {
        machines.get(23).process(chef, machineUnlockBalance);
        grill.play(soundVolume);
      }
    } else if (targetx == 1 && targety == 6) {
      if (invTop == "raw pizza") {
        machines.get(24).process(chef, machineUnlockBalance);
        grill.play(soundVolume);
      }
    }
  }

  /** Adds the top item from the currently selected chef's inventory to the tray. */
  private void addToTray(int station) {
    Chef chef = chefs.get(selectedChef);
    Stack<String> inv = chef.getInventory();
    ArrayList<String> tray = new ArrayList<String>();
    if (station == 1) {
      tray = tray1;
    } else if (station == 2) {
      tray = tray2;
    } else {
      return;
    }

    if (inv.size() > 0) {
      // Hamburger ingredients
      if (tray.isEmpty() && inv.peek() == "toasted bun") {
        inv.pop();
        tray.add("toasted bun");
      } else if (tray.contains("toasted bun")
          && inv.peek() == "burger"
          && !tray.contains("burger")) {
        inv.pop();
        tray.add("burger");

        // Salad ingredients
      } else if (tray.isEmpty() && inv.peek() == "chopped lettuce") {
        inv.pop();
        tray.add("chopped lettuce");
      } else if (tray.contains("chopped lettuce")
          && inv.peek() == "chopped tomato"
          && !tray.contains("chopped tomato")) {
        inv.pop();
        tray.add("chopped tomato");
      } else if (tray.contains("chopped tomato")
          && inv.peek() == "chopped onion"
          && !tray.contains("chopped onion")) {
        inv.pop();
        tray.add("chopped onion");

        // Jacket potato ingredients
      } else if (tray.isEmpty() && inv.peek() == "jacket") {
        inv.pop();
        tray.add("jacket");
      } else if (tray.contains("jacket") && inv.peek() == "beans" && !tray.contains("beans")) {
        inv.pop();
        tray.add("beans");

        // Raw pizza ingredients
      } else if (tray.isEmpty() && inv.peek() == "dough") {
        inv.pop();
        tray.add("dough");
      } else if (tray.contains("dough")
          && inv.peek() == "chopped tomato"
          && !tray.contains("chopped tomato")) {
        inv.pop();
        tray.add("chopped tomato");
      } else if (tray.contains("chopped tomato")
          && inv.peek() == "cheese"
          && !tray.contains("cheese")) {
        inv.pop();
        tray.add("cheese");
      }
    }
    // Hamburger assembly
    if (tray.contains("burger") && tray.contains("toasted bun")) {
      tray.clear();
      if (machineUnlockBalance.isUnlocked("server-staff")) {
        deliveryStaff.collectItem("hamburger");
        serveFood();
      } else {
        inv.add("hamburger");
      }
      serving.play(soundVolume);
    }
    // Salad assembly
    if (tray.contains("chopped lettuce")
        && tray.contains("chopped tomato")
        && tray.contains("chopped onion")) {
      tray.clear();
      if (machineUnlockBalance.isUnlocked("server-staff")) {
        deliveryStaff.collectItem("salad");
        serveFood();
      } else {
        inv.add("salad");
      }
      serving.play(soundVolume);
    }
    // Jacket potato assembly
    if (tray.contains("jacket") && tray.contains("beans")) {
      tray.clear();
      if (machineUnlockBalance.isUnlocked("server-staff")) {
        deliveryStaff.collectItem("jacket potato");
        serveFood();
      } else {
        inv.add("jacket potato");
      }
      serving.play(soundVolume);
    }
    // Raw pizza assembly
    if (tray.contains("dough") && tray.contains("chopped tomato") && tray.contains("cheese")) {
      tray.clear();
      inv.add("raw pizza");
      serving.play(soundVolume);
    }
    if (station == 1) {
      tray1 = tray;

    } else if (station == 2) {
      tray2 = tray;
    }
  }

  private void serveFood() {
    Chef chef = chefs.get(selectedChef);
    Stack<String> inv = new Stack<>();
    if (machineUnlockBalance.isUnlocked("server-staff")
        && !(customers.get(0).getOrder() == "pizza")) {
      inv = deliveryStaff.getItems();
    } else {
      inv = chef.getInventory();
    }

    if (customers.get(0).getOrder() == inv.peek()) {
      inv.pop();
      customers.remove(0);
      serving.play(soundVolume);
      machineUnlockBalance.incrementBalance();
      staffOne.setGenerate(true);
    }

    if (customers.size() == 0) {
      game.setScreen(new GameWinScreen(game, (int) totalTimer));
    }
  }
}

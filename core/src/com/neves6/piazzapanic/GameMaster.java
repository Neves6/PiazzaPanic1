package com.neves6.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.Stack;

/**
 * GameMaster class.
 */
public class GameMaster {
    public GameMaster() {
    }
}

/**
 * ScenarioGameMaster subclass.
 */
class ScenarioGameMaster extends GameMaster {
    PiazzaPanicGame game;
    TiledMap map;
    TiledMapTileLayer collisionLayer;
    ArrayList<Chef> chefs = new ArrayList<>();
    Stack<Customer> customers = new Stack<>();
    ArrayList<Machine> machines = new ArrayList<>();
    ArrayList<String> tray = new ArrayList<>();
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

    /**
     * ScenarioGameMaster constructor.
     * @param game PiazzaPanicGame instance.
     * @param map TiledMap instance.
     * @param chefno Number of chefs.
     * @param custno Number of customers.
     */
    public ScenarioGameMaster(PiazzaPanicGame game, TiledMap map, int chefno, int custno) {
        this.game = game;
        settings = Utility.getSettings();
        this.map = map;
        collisionLayer = (TiledMapTileLayer) map.getLayers().get(3);
        for (int i = 0; i < chefno; i++) {
            chefs.add(new Chef("Chef", 6+i, 5, 1, 1, 1, false, new Stack<String>(), i+1));
        }
        for (int i = 0; i < custno; i++) {
            if (i % 2 == 0) {
                customers.add(new Customer("Customer"+i+1, -1, -1, "salad"));
            } else {
                customers.add(new Customer("Customer"+i+1, -1, -1, "burger"));
            }
        }
        totalTimer = 0f;
        machines.add(new Machine("fridgemeat", "", "meat", 0, false));
        machines.add(new Machine("fridgetomato", "", "tomato", 0, false));
        machines.add(new Machine("fridgelettuce", "", "lettuce", 0, false));
        machines.add(new Machine("fridgeonion", "", "onion", 0, false));
        machines.add(new Machine("fridgebun", "", "bun", 0, false));
        machines.add(new Machine("grill1patty", "patty", "burger", 3, true));
        machines.add(new Machine("grill2patty", "patty", "burger", 3, true));
        machines.add(new Machine("grill1bun", "bun", "toastedbun", 3, true));
        machines.add(new Machine("grill2bun", "bun", "toastedbun", 3, true));
        machines.add(new Machine("forming1", "meat", "patty", 3, true));
        machines.add(new Machine("forming2", "meat", "patty", 3, true));
        machines.add(new Machine("chopping1tomato", "tomato", "choppedtomato", 3, true));
        machines.add(new Machine("chopping2tomato", "tomato", "choppedtomato", 3, true));
        machines.add(new Machine("chopping1lettuce", "lettuce", "choppedlettuce", 3, true));
        machines.add(new Machine("chopping2lettuce", "lettuce", "choppedlettuce", 3, true));
        machines.add(new Machine("chopping1onion", "onion", "choppedonion", 3, true));
        machines.add(new Machine("chopping2onion", "onion", "choppedonion", 3, true));
        // disposal and tray/serving handled separately

        grill = Gdx.audio.newSound(Gdx.files.internal("sounds/grill.mp3"));
        chopping = Gdx.audio.newSound(Gdx.files.internal("sounds/chopping.mp3"));
        serving = Gdx.audio.newSound(Gdx.files.internal("sounds/serving.mp3"));
        fridge = Gdx.audio.newSound(Gdx.files.internal("sounds/fridge.mp3"));
        forming = Gdx.audio.newSound(Gdx.files.internal("sounds/forming.mp3"));
        trash = Gdx.audio.newSound(Gdx.files.internal("sounds/trash.mp3"));

        switch (settings.get(1).strip()){
            case "full":
                soundVolume = 1f;
                break;
            case "half":
                soundVolume = 0.5f;
                break;
            case "none":
                soundVolume = 0f;
                break;
        }
    }

    public void setSelectedChef(int selectedChef) {
        this.selectedChef = selectedChef - 1;
    }
    public int getSelectedChef() {
        return selectedChef + 1;
    }

    public Chef getChef(int i){
        return chefs.get(i-1);
    }

    /**
     * Attempts to move the currently selected chef in a specified direction.
     * @param direction Direction to move.
     */
    public void tryMove(String direction){
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
        }
    }

    /**
     * Checks collision with the collision layer, other chefs, and being stickied.
     * @param x x coordinate to check.
     * @param y y coordinate to check.
     * @param chefno chef number to check.
     * @return true if the chef would not collide, false otherwise.
     */
    private boolean wouldNotCollide(int x, int y, int chefno) {
        if (chefs.get(chefno).getIsStickied()) {
            return false;
        }
        if ((chefno == 0 && chefs.get(1).getxCoord() == x && chefs.get(1).getyCoord() == y) || (chefno == 1 && chefs.get(0).getxCoord() == x && chefs.get(0).getyCoord() == y)) {
            return false;
        }
        int tempCellTileID = collisionLayer.getCell(x, y).getTile().getId();
        return tempCellTileID != 37 && tempCellTileID != 39;
    }

    /**
     * Generates the display text for the chefs' inventories.
     * @return String containing the display text.
     */
    public String generateHoldingsText() {
        String comp = "";
        comp += "Chef 1 is holding:\n";
        comp += chefs.get(0).getInventory().toString();
        comp += "\nChef 2 is holding:\n";
        comp += chefs.get(1).getInventory().toString();
        return comp;
    }

    /**
     * Generates the display text for the customers' tray and order.
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
        comp += "\nTray contents: ";
        comp += tray.toString();
        return comp;
    }

    /**
     * Generates the display text for the timer.
     * @return String containing the display text.
     */
    public String generateTimerText(){
        String comp = "";
        comp += "Time elapsed: ";
        comp += (int) totalTimer;
        comp += " s";
        return comp;
    }

    /**
     * Generates the display text for the chef's timer.
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

    public int getCustomersRemining(){
        return customers.size();
    }

    public Customer getFirstCustomer() {
        return customers.get(0);
    }

    /**
     * Updates timers on all machines and the total timer.
     * To be called every frame render.
     * @param delta time since last frame.
     */
    public void tickUpdate(float delta) {
        for (Machine machine : machines) {
            if (machine.getActive()) {
                machine.incrementRuntime(delta);
                machine.attemptGetOutput();
            }
        }
        totalTimer += delta;
    }

    /**
     * Attempts to cause an interaction between the currently selected chef and the machine in front of them.
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
        //System.out.println("Target: " + targetx + ", " + targety + "\nFacing: " + chef.getFacing());
        if (chef.getInventory().empty()) {
            if (targetx == 1 && targety == 10) {
                machines.get(0).process(chef);
                fridge.play(soundVolume);
            } else if (targetx == 2 && targety == 10) {
                machines.get(1).process(chef);
                fridge.play(soundVolume);
            } else if (targetx == 3 && targety == 10) {
                machines.get(2).process(chef);
                fridge.play(soundVolume);
            } else if (targetx == 4 && targety == 10) {
                machines.get(3).process(chef);
                fridge.play(soundVolume);
            } else if (targetx == 1 && targety == 8) {
                machines.get(4).process(chef);
                fridge.play(soundVolume);
            } else { return; }
        }
        String invTop = chef.getInventory().peek();
        if (targetx == 6 && targety == 7) {
            if (invTop == "patty") {
                machines.get(5).process(chef);
                grill.play(soundVolume);
            } else if (invTop == "bun") {
                machines.get(7).process(chef);
                grill.play(soundVolume);
            }
        } else if (targetx == 7 && targety == 7) {
            if (invTop == "patty") {
                machines.get(6).process(chef);
                grill.play(soundVolume);
            } else if (invTop == "bun") {
                machines.get(8).process(chef);
                grill.play(soundVolume);
            }
        } else if (targetx == 9 && targety == 7) {
            machines.get(9).process(chef);
            forming.play(soundVolume);
        } else if (targetx == 10 && targety == 7) {
            machines.get(10).process(chef);
            forming.play(soundVolume);
        } else if (targetx == 11 && targety == 7) {
            if (invTop == "tomato") {
                machines.get(11).process(chef);
                chopping.play(soundVolume);
            } else if (invTop == "lettuce") {
                machines.get(13).process(chef);
                chopping.play(soundVolume);
            } else if (invTop == "onion") {
                machines.get(15).process(chef);
                chopping.play(soundVolume);
            }
        } else if (targetx == 12 && targety == 7) {
            if (invTop == "tomato") {
                machines.get(12).process(chef);
            } else if (invTop == "lettuce") {
                machines.get(14).process(chef);
            } else if (invTop == "onion") {
                machines.get(16).process(chef);
            }
        } else if (targetx == 1 && targety == 5) {
            chef.removeTopFromInventory();
            trash.play(soundVolume);
        } else if (targetx == 8 && targety == 3) {
            addToTray();
        }
    }

    /**
     * Adds the top item from the currently selected chef's inventory to the tray.
     */
    private void addToTray() {
        Chef chef = chefs.get(selectedChef);
        Stack<String> inv = chef.getInventory();
        if (customers.get(0).getOrder() == "burger"){
            if (inv.peek() == "burger"){
                inv.pop();
                tray.add("burger");
            } else if (inv.peek() == "toastedbun"){
                inv.pop();
                tray.add("toastedbun");
            }
            if (tray.contains("burger") && tray.contains("toastedbun")){
                customers.remove(0);
                tray.clear();
                serving.play(soundVolume);
            }
        } else if (customers.get(0).getOrder() == "salad"){
            if (inv.peek() == "choppedtomato"){
                inv.pop();
                tray.add("choppedtomato");
            } else if (inv.peek() == "choppedlettuce"){
                inv.pop();
                tray.add("choppedlettuce");
            } else if (inv.peek() == "choppedonion"){
                inv.pop();
                tray.add("choppedonion");
            }
            if (tray.contains("choppedtomato") && tray.contains("choppedlettuce") && tray.contains("choppedonion")){
                customers.remove(0);
                tray.clear();
                serving.play(soundVolume);
            }
        }
        if (customers.size() == 0){
            game.setScreen(new GameWinScreen(game, (int) totalTimer));
        }
    }
}
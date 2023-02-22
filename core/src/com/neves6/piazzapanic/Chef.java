package com.neves6.piazzapanic;

import com.badlogic.gdx.graphics.Texture;

import java.util.Stack;

/**
 * Chef subclass.
 */
public class Chef extends Person {
    private boolean isStickied;
    private Stack<String> inventory;
    private String facing;
    private final Texture txUp;
    private final Texture txDown;
    private final Texture txLeft;
    private final Texture txRight;
    private Texture txNow;
    private boolean isInteracting;
    private Machine machineInteractingWith;

    /**
     * Chef constructor.
     * @param name
     * @param xCoord
     * @param yCoord
     * @param chopSpeed
     * @param frySpeed
     * @param bakeSpeed
     * @param isStickied
     * @param inventory
     * @param textureSet
     */
    public Chef(String name, int xCoord, int yCoord, int chopSpeed, int frySpeed, int bakeSpeed, boolean isStickied, Stack<String> inventory, int textureSet){
        super(name, xCoord, yCoord);
        this.isStickied = isStickied;
        if (inventory.size() == 0){ this.inventory = new Stack<String>();}
        else {this.inventory = inventory;}
        this.facing = "down";
        this.txUp =    new Texture("people/chef" + textureSet + "up.png");
        this.txDown =  new Texture("people/chef" + textureSet + "down.png");
        this.txLeft =  new Texture("people/chef" + textureSet + "left.png");
        this.txRight = new Texture("people/chef" + textureSet + "right.png");
        this.txNow = txDown;
    }

    public boolean getIsStickied(){
        return isStickied;
    }
    public Texture getTxNow(){
        return txNow;
    }
    public void setIsStickied(boolean flag){
        this.isStickied = flag;
    }
    public void setMachineInteractingWith(Machine machine){
        this.machineInteractingWith = machine;
    }
    public Machine getMachineInteractingWith(){
        return machineInteractingWith;
    }

    //Inventory management
    public Stack<String> getInventory(){
        return inventory;
    }
    public void addToInventory(String item){
        this.inventory.push(item);
    }
    public void removeTopFromInventory(){
        this.inventory.pop();
    }

    //Facing
    public String getFacing(){
        return facing;
    }

    /**
     * Sets the facing direction of the chef, then changes the current texture to match.
     * @param facing sprite facing direction.
     */
    public void setFacing(String facing){
        this.facing = facing;
        switch (facing){
            case "up":
                this.txNow = txUp;
                break;
            case "down":
                this.txNow = txDown;
                break;
            case "left":
                this.txNow = txLeft;
                break;
            case "right":
                this.txNow = txRight;
                break;
        }
    }
}

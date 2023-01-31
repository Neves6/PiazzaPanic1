package com.neves6.piazzapanic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Stack;

public class Person {
    private final String name;
    private int xCoord;
    private int yCoord;
    private Sprite sprite;

    public Person(String name, int xCoord, int yCoord, Sprite sprite){
        this.name = name;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.sprite = sprite;
    }

    public int getxCoord() {
        return xCoord;
    }
    public int getyCoord() {
        return yCoord;
    }
    public void setxCoord(int xCoord){
        this.xCoord = xCoord;
    }

    public void setyCoord(int yCoord){
        this.xCoord = yCoord;
    }
    public void alterxCoord(int xDelta){
        this.xCoord += xDelta;
    }
    public void alteryCoord(int yDelta){
        this.yCoord += yDelta;
    }

}

class Customer extends Person{

    private String order;

    public Customer(String name, int xCoord, int yCoord, Sprite sprite, String order){
        super(name, xCoord, yCoord, sprite);
        this.order = order;
    }

    public String getOrder(){
        return order;
    }

}

class Chef extends Person {
    private boolean isStickied;
    private Stack<String> inventory;
    private String facing;
    private final Texture txUp;
    private final Texture txDown;
    private final Texture txLeft;
    private final Texture txRight;
    private Texture txNow;
    private boolean isInteracting;

    public Chef(String name, int xCoord, int yCoord, Sprite sprite, int chopSpeed, int frySpeed, int bakeSpeed, boolean isStickied, Stack<String> inventory, int textureSet){
        super(name, xCoord, yCoord, sprite);
        this.isStickied = isStickied;
        this.inventory = inventory;
        this.inventory = new Stack<String>();
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

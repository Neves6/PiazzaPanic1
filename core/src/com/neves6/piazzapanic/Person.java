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
    public String getName() {
        return name;
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

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
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

    public void setOrder(String order){
        this.order = order;
    }
}

class Chef extends Person {
    private int chopSpeed;
    private int frySpeed;
    private int bakeSpeed;
    private boolean isStickied;
    private Stack<String> inventory;
    private String facing;
    private final Texture txUp;
    private final Texture txDown;
    private final Texture txLeft;
    private final Texture txRight;
    private Texture txNow;
    private boolean isInteracting;

    public Chef(String name, int xCoord, int yCoord, Sprite sprite, int chopSpeed, int frySpeed, int bakeSpeed, boolean isStickied, Stack inventory, int textureSet){
        super(name, xCoord, yCoord, sprite);
        this.chopSpeed = chopSpeed;
        this.frySpeed = frySpeed;
        this.bakeSpeed = bakeSpeed;
        this.isStickied = isStickied;
        this.inventory = inventory;
        this.inventory = new Stack();
        this.facing = "down";
        this.txUp =    new Texture("people/chef" + textureSet + "up.png");
        this.txDown =  new Texture("people/chef" + textureSet + "down.png");
        this.txLeft =  new Texture("people/chef" + textureSet + "left.png");
        this.txRight = new Texture("people/chef" + textureSet + "right.png");
        this.txNow = txDown;
    }
    //Getters
    public int getChopSpeed(){
        return chopSpeed;
    }
    public int getFrySpeed(){
        return frySpeed;
    }
    public int getBakeSpeed(){
        return bakeSpeed;
    }
    public boolean getIsStickied(){
        return isStickied;
    }
    public Texture getTxNow(){
        return txNow;
    }
    //Setters
    public void setChopSpeed(int chopSpeed){
        this.chopSpeed = chopSpeed;
    }
    public void setFrySpeed(int frySpeed){
        this.frySpeed = frySpeed;
    }
    public void setBakeSpeed(int bakeSpeed){
        this.bakeSpeed = bakeSpeed;
    }
    public void setIsStickied(boolean flag){
        this.isStickied = flag;
    }
    public void setisInteracting(boolean flag){
        this.isInteracting = flag;
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
    public void clearInventory(){
        this.inventory.clear();
    }
    public boolean getisInteracting(){
        return isInteracting;
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

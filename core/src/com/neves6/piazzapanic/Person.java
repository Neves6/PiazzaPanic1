package com.neves6.piazzapanic;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.Stack;

public class Person {
    private String name;
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
    private boolean isChopping; private int chopSpeed;
    private boolean isFrying; private int frySpeed; 
    private boolean isBaking; private int bakeSpeed;
    private boolean isServing;
    private Stack inventory;

    public Chef(String name, int xCoord, int yCoord, Sprite sprite, boolean isChopping, boolean isFrying, boolean isBaking, boolean isServing, int chopSpeed, int frySpeed, int bakeSpeed, Stack inventory){
        super(name, xCoord, yCoord, sprite);
        this.chopSpeed = chopSpeed;
        this.frySpeed = frySpeed;
        this.bakeSpeed = bakeSpeed;
        this.isChopping = false;
        this.isFrying = false;
        this.isBaking = false;
        this.isServing = false;
        this.inventory = new Stack();
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
    public boolean isChopping(){
        return isChopping;
    }
    public boolean isBaking(){
        return isBaking;
    }
    public boolean isFrying(){
        return isFrying;
    }
    public boolean isServing(){
        return isServing;
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
    public void setIsChopping(boolean flag){
        this.isChopping = flag;
    }
    public void setIsBaking(boolean flag){
        this.isBaking = flag;
    }
    public void setIsFrying(boolean flag){
        this.isFrying = flag;
    }
    public void setIsServing(boolean flag){
        this.isServing = flag;
    }
    //Inventory management
    public Stack getInventory(){
        return inventory;
    }
    public void addToInventory(String item){
        this.inventory.push(item);
    }
    public void removeTopFromInventory(String item){
        this.inventory.pop();
    }
}

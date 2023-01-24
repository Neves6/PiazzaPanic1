package com.neves6.piazzapanic;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Furniture{
    private String name;
    private int xCoord;
    private int yCoord;

    private Sprite sprite;

    public Furniture(String name, int xCoord, int yCoord, Sprite sprite){
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

class ActiveFurniture extends Furniture{
    private Animation animation;
    private boolean isActive;

    public ActiveFurniture(String name, int xCoord, int yCoord, Sprite sprite, boolean isActive){
        super(name, xCoord, yCoord, sprite);
        this.animation = animation;
        this.isActive = false;
    }
    public Animation getAnimation(){
        return animation;
    }
    public void setAnimation(Animation animation){
        this.animation = animation;
    }
    public void setActive(boolean flag){
        this.isActive = flag;
    }
}

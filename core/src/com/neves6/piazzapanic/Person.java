package com.neves6.piazzapanic;

import com.badlogic.gdx.graphics.Texture;

import java.util.Stack;

/**
 * Base person class.
 */
public class Person {
    private final String name;
    private int xCoord;
    private int yCoord;

    /**
     * Person constructor.
     * @param name Name of person.
     * @param xCoord logical x coordinate of person.
     * @param yCoord logical y coordinate of person.
     */
    public Person(String name, int xCoord, int yCoord){
        this.name = name;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public String getName(){ return name; }
    public void setxCoord(int xCoord){
        if (xCoord > -1) {
            this.xCoord = xCoord;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setyCoord(int yCoord){
        if (yCoord > -1) {
            this.yCoord = yCoord;
        } else {
            throw new IllegalArgumentException();
        }

    }
    public void alterxCoord(int xDelta){
        this.xCoord += xDelta;
    }
    public void alteryCoord(int yDelta){
        this.yCoord += yDelta;
    }

}



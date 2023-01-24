package com.neves6.piazzapanic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Kitchen extends Actor {
    private Texture foodWindow;
    private Texture oven;
    private Texture choppingBoard;
    private Texture fryer;
    private Texture countertop;
    private Texture lettuceBox;
    private Texture tomatoBox;
    private Texture onionBox;
    private Texture meatBox;
    private Texture bunBox;
    private Texture tileFloor;

    private float foodWindowX;
    private float foodWindowY;
    private float ovenX;
    private float ovenY;
    private float choppingBoardX;
    private float choppingBoardY;
    private float fryerX;
    private float fryerY;
    private float countertopX;
    private float countertopY;
    private float lettuceBoxX;
    private float lettuceBoxY;
    private float tomatoBoxX;
    private float tomatoBoxY;
    private float onionBoxX;
    private float onionBoxY;
    private float meatBoxX;
    private float meatBoxY;
    private float bunBoxX;
    private float bunBoxY;
    private float tileFloorX;
    private float tileFloorY;
    private Stage stage;

    public Kitchen() {
        foodWindow = new Texture("kitchen_fridge.png");
        oven = new Texture("kitchen_stove.png");
        choppingBoard = new Texture("kitchen_sink.png");
        fryer = new Texture("kitchen_stove.png");
        countertop = new Texture("tabletop.png");
        tileFloor = new Texture("tileset.png");
        onionBox = new Texture("tabletop_wallframes.png");
        tomatoBox = new Texture("tabletop_wallframes.png");
        lettuceBox = new Texture("tabletop_wallframes.png");
        meatBox = new Texture("tabletop_wallknife.png");
        bunBox = new Texture("tabletop_wallknife.png");
        foodWindowX = 0;
        foodWindowY = 0;
        ovenX = 200;
        ovenY = 0;
        choppingBoardX = 400;
        choppingBoardY = 0;
        fryerX = 600;
        fryerY = 0;
        countertopX = 800;
        countertopY = 0;
        tileFloorX = 0;
        tileFloorY = 0;
        onionBoxX = 50;
        onionBoxY = 50;
        tomatoBoxX = 100;
        tomatoBoxY = 50;
        lettuceBoxX = 150;
        lettuceBoxY = 50;
        meatBoxX = 200;
        meatBoxY = 50;
        bunBoxX = 250;
        bunBoxY = 50;
        stage = new Stage();
        stage.addActor(this);
    }

}

package com.neves6.piazzapanic;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;

public class GameScreen extends ScreenAdapter {
    PiazzaPanicGame game;

    public GameScreen(PiazzaPanicGame game) {
        this.game = game;
    }
}

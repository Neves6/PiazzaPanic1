package com.neves6.piazzapanic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PiazzaPanicGame extends Game {
	SpriteBatch batch;

	public PiazzaPanicGame() {
		super();
	}

	@Override
	public void create () {
		batch = new SpriteBatch();

		setScreen(new IntroScreen(this));
	}

	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public void render(float delta) {
	}

	@Override
	public void dispose () {
		//super.dispose();
		//batch.dispose();
	}
}

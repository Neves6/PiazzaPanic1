package com.neves6.piazzapanic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PiazzaPanicGame extends Game {
	SpriteBatch batch;
	BitmapFont font;

	public PiazzaPanicGame() {
		super();
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();

		setScreen(new IntroScreen(this));
	}

	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public void render(float delta) {
		super.render();
	}

	@Override
	public void dispose () {
		//batch.dispose();
		font.dispose();
	}
}

package com.neves6.piazzapanic.tests;

import com.badlogic.gdx.Gdx;
import com.neves6.piazzapanic.CreditsScreen;
import com.neves6.piazzapanic.IntroScreen;
import com.neves6.piazzapanic.PiazzaPanicGame;
import com.neves6.piazzapanic.TitleScreen;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class TestScreenTransitions {
    @Test
    public void testIntroScreenOnLaunch(){
        Gdx.gl20 = Gdx.gl;
        PiazzaPanicGame testGame = new PiazzaPanicGame();
        testGame.create();
        assertTrue(testGame.getScreen().getClass() == IntroScreen.class);
    }

}
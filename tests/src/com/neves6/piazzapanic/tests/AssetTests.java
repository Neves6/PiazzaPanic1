package com.neves6.piazzapanic.tests;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class AssetTests {

    @Test
    public void testControlAssetExists() {
        assertTrue("Controls.png must exist for the game to compile", Gdx.files
                .internal("controls.png").exists());
    }

    @Test
    public void testShipAssetExists() {
        assertTrue("Credits.png must exist for the game to compile", Gdx.files
                .internal("credits.png").exists());
    }
}
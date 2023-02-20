package com.neves6.piazzapanic.tests;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class TestAsset {

    @Test
    public void testControlAssetExists() {
        assertTrue("'controls.png' must exist for the game to compile", Gdx.files
                .internal("controls.png").exists());
    }


    @Test
    public void testfontsIBMPlexMonoSemiBoldExists() {
        assertTrue("'fonts/IBM_Plex_Mono_SemiBold.fnt' must exist for the game to compile", Gdx.files
                .internal("fonts/IBM_Plex_Mono_SemiBold.fnt").exists());
    }

    @Test
    public void testcreditspngExists() {
        assertTrue("'credits.png' must exist for the game to compile", Gdx.files
                .internal("credits.png").exists());
    }

    @Test
    public void testsoundsgrillExists() {
        assertTrue("'sounds/grill.mp3' must exist for the game to compile", Gdx.files
                .internal("sounds/grill.mp3").exists());
    }

    @Test
    public void testsoundschoppingExists() {
        assertTrue("'sounds/chopping.mp3' must exist for the game to compile", Gdx.files
                .internal("sounds/chopping.mp3").exists());
    }

    @Test
    public void testsoundsservingExists() {
        assertTrue("'sounds/serving.mp3' must exist for the game to compile", Gdx.files
                .internal("sounds/serving.mp3").exists());
    }

    @Test
    public void testsoundsfridgeExists() {
        assertTrue("'sounds/fridge.mp3' must exist for the game to compile", Gdx.files
                .internal("sounds/fridge.mp3").exists());
    }

    @Test
    public void testsoundsformingExists() {
        assertTrue("'sounds/forming.mp3' must exist for the game to compile", Gdx.files
                .internal("sounds/forming.mp3").exists());
    }

    @Test
    public void testsoundstrashExists() {
        assertTrue("'sounds/trash.mp3' must exist for the game to compile", Gdx.files
                .internal("sounds/trash.mp3").exists());
    }

    @Test
    public void testfontsIBMPlexMonoSemiBoldBlackExists() {
        assertTrue("'fonts/IBM_Plex_Mono_SemiBold_Black.fnt' must exist for the game to compile", Gdx.files
                .internal("fonts/IBM_Plex_Mono_SemiBold_Black.fnt").exists());
    }

    @Test
    public void testtitlescreenlargepngExists() {
        assertTrue("'title_screen_large.png' must exist for the game to compile", Gdx.files
                .internal("title_screen_large.png").exists());
    }

    @Test
    public void testpeopleselectedExists() {
        assertTrue("'people/selected.png' must exist for the game to compile", Gdx.files
                .internal("people/selected.png").exists());
    }

    @Test
    public void testrecipespngExists() {
        assertTrue("'recipes.png' must exist for the game to compile", Gdx.files
                .internal("recipes.png").exists());
    }

    @Test
    public void testtitlescreenlargeminpngExists() {
        assertTrue("'title_screen_large-min.png' must exist for the game to compile", Gdx.files
                .internal("title_screen_large-min.png").exists());
    }

    @Test
    public void testbuttonstitleunnamedExists() {
        assertTrue("'buttons/title/unnamed.atlas' must exist for the game to compile", Gdx.files
                .internal("buttons/title/unnamed.atlas").exists());
    }

    @Test
    public void testintrosheetpngExists() {
        assertTrue("'intro_sheet.png' must exist for the game to compile", Gdx.files
                .internal("intro_sheet.png").exists());
    }

    @Test
    public void testlevellockedpngExists() {
        assertTrue("'levellocked.png' must exist for the game to compile", Gdx.files
                .internal("levellocked.png").exists());
    }

    @Test
    public void testbuttonslevelselectorlevelselectorExists() {
        assertTrue("'buttons/levelselector/levelselector.atlas' must exist for the game to compile", Gdx.files
                .internal("buttons/levelselector/levelselector.atlas").exists());
    }

    @Test
    public void testtutorialpngExists() {
        assertTrue("'tutorial.png' must exist for the game to compile", Gdx.files
                .internal("tutorial.png").exists());
    }
}

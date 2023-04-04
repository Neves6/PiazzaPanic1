package com.neves6.piazzapanic.gamemechanisms;
import com.neves6.piazzapanic.screens.IntroScreen;
import com.neves6.piazzapanic.screens.PiazzaPanicGame;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

/** Class used to save game data from a current game runtime to a json
 * file.
 */
public class GameSaver {
    JSONObject gameDetails;
    FileWriter file;

    public GameSaver(String fileLoc) throws IOException {
        gameDetails = new JSONObject();
        file = new FileWriter(fileLoc);
    }

    public Boolean setDifficulty(int value){
        if (value <= 0 || value >= 4){
            return false;
        } else{
            gameDetails.put("Difficulty", value);
            return true;
        }
    }

    public Boolean setPowerUp(Boolean value){
        gameDetails.put("Power-ups", value);
        return true;
    }

    public Boolean setCustomersRemaining(int value){
        if (value == 0){
            return false;
        } else{
            gameDetails.put("Customers remaining", value);
            return true;
        }
    }

    public void closeClass(PiazzaPanicGame game) throws IOException {
        file.write(gameDetails.toJSONString());
        file.flush();
        file.close();
        game.setScreen(new IntroScreen(game));
    }
}

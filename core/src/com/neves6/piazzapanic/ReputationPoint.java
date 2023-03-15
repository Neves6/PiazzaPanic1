package com.neves6.piazzapanic;

public class ReputationPoint {
    private ReputationPoint (){ //DO NOT INSTANTIATE
    }
    public int incrementPoints(int points){
        points+=1;
        return points;
    }
    public int decrementPoints(int points, PiazzaPanicGame game, int totalTimer, int custNum, boolean isEndless, boolean isPowerUp, int difficulty){
        points-=1;
        if (points==0){
            
            //check if highscore
            //save etc
            game.setScreen(new GameWinScreen(game, (int) totalTimer, false, isEndless, isPowerUp, difficulty));
        }
        return points;
    }
}

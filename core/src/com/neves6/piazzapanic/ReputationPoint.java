package com.neves6.piazzapanic;

public class ReputationPoint {
    boolean isEndless;
    int highScoreEndless;
    int highScoreScenario;
    int points;
    public ReputationPoint (boolean isEndless, int highScoreEndless, int highScoreScenario, int points){
        this.isEndless=isEndless;
        this.highScoreEndless=getHighScoreEndless();
        this.highScoreScenario=getHighScoreScenario();
        this.points=3;
    }
    public int getHighScoreEndless(){
        return 0; //for now
    }
    public int getHighScoreScenario(){
        return 0; //for now
    }
    public void incrementPoints(){
        points+=1;
        return;
    }
    public void decrementPoints(){
        points-=1;
        return;
    }
    public void isHighScore(){ //call at end of game
        if (isEndless==true){
            int prevHighEndless=getHighScoreEndless();
            if (points>prevHighEndless){
                setHighScoreEndless(points); //add congrats screen or smthn
            }
            else{
                setHighScoreEndless(prevHighEndless);
            }
        }
        else{
            int prevHighScenario=getHighScoreScenario();
            if (points>prevHighScenario){
                setHighScoreScenario(points); //congrats
            }
            else{
                setHighScoreScenario(prevHighScenario);
            }
        }

    }
    public void setHighScoreEndless(int points){
        //store high score for endless mode
        //isEndless MUST be true
        return;
    }
    public void setHighScoreScenario(int points){
        //store high score for scenario mode
        //isEndless MUST be false
        return;
    }
    public int getPoints(){
        return points;
    }
}

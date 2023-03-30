package com.neves6.piazzapanic.gamemechanisms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/** Helper/convenience class. Currently only handles retrieving and saving settings. */
public final class Utility {

  public static String settingsFilepath = "./settings.txt";

  /** Utility constructor. SHOULD NOT BE INITIALIZED! */
  private Utility() {} // not intended to be instantiated

  /**
   * Retrieves settings from file.
   *
   * @return ArrayList of settings values.
   */
  public static ArrayList<String> getSettings() {
    settingsFileExistenceHandler();
    ArrayList<String> settings = new ArrayList<String>();
    try (FileReader f = new FileReader(settingsFilepath)) {
      StringBuffer sb = new StringBuffer();
      while (f.ready()) {
        char c = (char) f.read();
        if (c == '\n') {
          settings.add(sb.toString());
          sb = new StringBuffer();
        } else {
          sb.append(c);
        }
      }
      if (sb.length() > 0) {
        settings.add(sb.toString());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return settings;
  }

  /**
   * Saves settings to file.
   *
   * @param settings ArrayList of settings values to be saved.
   */
  public static void setSettings(ArrayList<String> settings) {
    settingsFileExistenceHandler();
    try {
      BufferedWriter outputWriter = null;
      outputWriter = new BufferedWriter(new FileWriter(settingsFilepath));
      for (String setting : settings) {
        outputWriter.write(setting + "");
        // outputWriter.newLine();
        outputWriter.write("\n");
      }
      outputWriter.flush();
      outputWriter.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** Checks if file exists, creates it if it doesn't and saves default values. */
  private static void settingsFileExistenceHandler() {
    File f = new File(settingsFilepath);
    if (f.isFile()) {
      return;
    } else {
      try {
        f.createNewFile();
        ArrayList<String> defaults = new ArrayList<String>();
        defaults.add("windowed");
        defaults.add("full");
        for (int i = 0; i < 36; i++){
          defaults.add("0");
        }
        setSettings(defaults);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  boolean isEndless;
  boolean isPowerUp;
  int difficulty;
  int points;
  public static ArrayList<Integer> getHighScore(boolean isEndless, boolean isPowerUp, int difficulty){
    ArrayList<String> settingsDotTeeExTee = new ArrayList<String>(getSettings());
    if (isEndless){
      if (isPowerUp){
        if (difficulty==0){
          //easy powerup endless
          ArrayList<Integer> prevScores = new ArrayList<Integer>();
          for (int i=2; i<5; i++){
            prevScores.add(Integer.parseInt(settingsDotTeeExTee.get(i)));
          }
          return prevScores;
        }else if (difficulty==1){
          //medium powerup endless
          ArrayList<Integer> prevScores = new ArrayList<Integer>();
          for (int i=5; i<8; i++){
            prevScores.add(Integer.parseInt(settingsDotTeeExTee.get(i)));
          }
          return prevScores;
        }else if (difficulty==2){
          //hard powerup endless
          ArrayList<Integer> prevScores = new ArrayList<Integer>();
          for (int i=8; i<11; i++){
            prevScores.add(Integer.parseInt(settingsDotTeeExTee.get(i)));
          }
          return prevScores;
        }else{
          //error difficulty out of range
          throw new IllegalArgumentException("Difficulty variable out of range");
        }
      }else{
        if (difficulty==0){
          //easy not powerup endless
          ArrayList<Integer> prevScores = new ArrayList<Integer>();
          for (int i=11; i<14; i++){
            prevScores.add(Integer.parseInt(settingsDotTeeExTee.get(i)));
          }
          return prevScores;
        }else if (difficulty==1){
          //medium not powerup endless
          ArrayList<Integer> prevScores = new ArrayList<Integer>();
          for (int i=14; i<17; i++){
            prevScores.add(Integer.parseInt(settingsDotTeeExTee.get(i)));
          }
          return prevScores;
        }else if (difficulty==2){
          //hard not powerup endless
          ArrayList<Integer> prevScores = new ArrayList<Integer>();
          for (int i=17; i<20; i++){
            prevScores.add(Integer.parseInt(settingsDotTeeExTee.get(i)));
          }
          return prevScores;
        }else{
          //error difficulty out of range
          throw new IllegalArgumentException("Difficulty variable out of range");
        }
      }
    }else{
      if (isPowerUp){
        if (difficulty==0){
          //easy powerup scenario
          ArrayList<Integer> prevScores = new ArrayList<Integer>();
          for (int i=20; i<23; i++){
            prevScores.add(Integer.parseInt(settingsDotTeeExTee.get(i)));
          }
          return prevScores;
        }else if (difficulty==1){
          //medium powerup scenario
          ArrayList<Integer> prevScores = new ArrayList<Integer>();
          for (int i=23; i<26; i++){
            prevScores.add(Integer.parseInt(settingsDotTeeExTee.get(i)));
          }
          return prevScores;
        }else if (difficulty==2){
          //hard powerup scenario
          ArrayList<Integer> prevScores = new ArrayList<Integer>();
          for (int i=26; i<29; i++){
            prevScores.add(Integer.parseInt(settingsDotTeeExTee.get(i)));
          }
          return prevScores;
        }else{
          //error difficulty out of range
          throw new IllegalArgumentException("Difficulty variable out of range");
        }
      }else{
        if (difficulty==0){
          //easy not powerup scenario
          ArrayList<Integer> prevScores = new ArrayList<Integer>();
          for (int i=29; i<32; i++){
            prevScores.add(Integer.parseInt(settingsDotTeeExTee.get(i)));
          }
          return prevScores;
        }else if (difficulty==1){
          //medium not powerup scenario
          ArrayList<Integer> prevScores = new ArrayList<Integer>();
          for (int i=32; i<35; i++){
            prevScores.add(Integer.parseInt(settingsDotTeeExTee.get(i)));
          }
          return prevScores;
        }else if (difficulty==2){
          //hard not powerup scenario
          ArrayList<Integer> prevScores = new ArrayList<Integer>();
          for (int i=35; i<38; i++){
            prevScores.add(Integer.parseInt(settingsDotTeeExTee.get(i)));
          }
          return prevScores;
        }else{
          //error difficulty out of range
          throw new IllegalArgumentException("Difficulty variable out of range");
        }
      }
    }
  }
  public static void setHighScore(boolean isEndless, boolean isPowerUp, int difficulty, int place, int iscore){
    ArrayList<String> settingsDotTeeExTee = new ArrayList<String>(getSettings());
    String score=Integer.toString(iscore);
    if (isEndless){
      if (isPowerUp){
        if (difficulty==0){
          if (place==0){
            //easy powerup endless 0
            settingsDotTeeExTee.set(2, score);
          }else if (place==1){
            //easy powerup endless 1
            settingsDotTeeExTee.set(3, score);
          }else if(place==2){
            //easy powerup endless 2
            settingsDotTeeExTee.set(4, score);
          }else{
            throw new IllegalArgumentException("Place variable out of range");
          }
        }else if (difficulty==1){
          if (place==0){
            //medium powerup endless 0
            settingsDotTeeExTee.set(5, score);
          }else if (place==1){
            //medium powerup endless 1
            settingsDotTeeExTee.set(6, score);
          }else if(place==2){
            //medium powerup endless 2
            settingsDotTeeExTee.set(7, score);
          }else{
            throw new IllegalArgumentException("Place variable out of range");
          }
        }else if (difficulty==2){
          if (place==0){
            //hard powerup endless 0,
            settingsDotTeeExTee.set(8, score);
          }else if (place==1){
            //hard powerup endless 1
            settingsDotTeeExTee.set(9, score);
          }else if(place==2){
            //hard powerup endless 2
            settingsDotTeeExTee.set(10, score);
          }else{
            throw new IllegalArgumentException("Place variable out of range");
          }
        }else{
          throw new IllegalArgumentException("Difficulty variable out of range");
        }
      }else{
        if (difficulty==0){
          if (place==0){
            //easy not powerup endless 0
            settingsDotTeeExTee.set(11, score);
          }else if (place==1){
            //easy not powerup endless 1
            settingsDotTeeExTee.set(12, score);
          }else if(place==2){
            //easy not powerup endless 2
            settingsDotTeeExTee.set(13, score);
          }else{
            throw new IllegalArgumentException("Place variable out of range");
          }
        }else if (difficulty==1){
          if (place==0){
            //medium not powerup endless 0
            settingsDotTeeExTee.set(14, score);
          }else if (place==1){
            //medium not powerup endless 1
            settingsDotTeeExTee.set(15, score);
          }else if(place==2){
            //medium not powerup endless 2
            settingsDotTeeExTee.set(16, score);
          }else{
            throw new IllegalArgumentException("Place variable out of range");
          }
        }else if (difficulty==2){
          if (place==0){
            //hard not powerup endless 0
            settingsDotTeeExTee.set(17, score);
          }else if (place==1){
            //hard not powerup endless 1
            settingsDotTeeExTee.set(18, score);
          }else if(place==2){
            //hard not powerup endless 2
            settingsDotTeeExTee.set(19, score);
          }else{
            throw new IllegalArgumentException("Place variable out of range");
          }
        }else{
          throw new IllegalArgumentException("Difficulty variable out of range");
        }
      }
    }else{
      //dscore=Math.pow(dscore, -1);
      //int iscore = (int) dscore;
      //String score=Integer.toString(iscore);
      if (isPowerUp){
        if (difficulty==0){
          if (place==0){
            //easy powerup scenario 0
            settingsDotTeeExTee.set(20, score);
          }else if (place==1){
            //easy powerup scenario 1
            settingsDotTeeExTee.set(21, score);
          }else if(place==2){
            //easy powerup scenario 2
            settingsDotTeeExTee.set(22, score);
          }else{
            throw new IllegalArgumentException("Place variable out of range");
          }
        }else if (difficulty==1){
          if (place==0){
            //medium powerup scenario 0
            settingsDotTeeExTee.set(23, score);
          }else if (place==1){
            //medium powerup scenario 1
            settingsDotTeeExTee.set(24, score);
          }else if(place==2){
            //medium powerup scenario 2
            settingsDotTeeExTee.set(25, score);
          }else{
            throw new IllegalArgumentException("Place variable out of range");
          }
        }else if (difficulty==2){
          if (place==0){
            //hard powerup scenario 0
            settingsDotTeeExTee.set(26, score);
          }else if (place==1){
            //hard powerup scenario 1
            settingsDotTeeExTee.set(27, score);
          }else if(place==2){
            //hard powerup scenario 2
            settingsDotTeeExTee.set(28, score);
          }else{
            throw new IllegalArgumentException("Place variable out of range");
          }
        }else{
          throw new IllegalArgumentException("Difficulty variable out of range");
        }
      }else{
        if (difficulty==0){
          if (place==0){
            //easy not powerup scenario 0
            settingsDotTeeExTee.set(29, score);
          }else if (place==1){
            //easy not powerup scenario 1
            settingsDotTeeExTee.set(30, score);
          }else if(place==2){
            //easy not powerup scenario 2
            settingsDotTeeExTee.set(31, score);
          }else{
            throw new IllegalArgumentException("Place variable out of range");
          }
        }else if (difficulty==1){
          if (place==0){
            //medium not powerup scenario 0
            settingsDotTeeExTee.set(32, score);
          }else if (place==1){
            //medium not powerup scenario 1
            settingsDotTeeExTee.set(33, score);
          }else if(place==2){
            //medium not powerup scenario 2
            settingsDotTeeExTee.set(34, score);
          }else{
            throw new IllegalArgumentException("Place variable out of range");
          }
        }else if (difficulty==2){
          if (place==0){
            //hard not powerup scenario 0
            settingsDotTeeExTee.set(35, score);
          }else if (place==1){
            //hard not powerup scenario 1
            settingsDotTeeExTee.set(36, score);
          }else if(place==2){
            //hard not powerup scenario 2
            settingsDotTeeExTee.set(37, score);
          }else{
            throw new IllegalArgumentException("Place variable out of range");
          }
        }else{
          throw new IllegalArgumentException("Difficulty variable out of range");
        }
      }
    }
    setSettings(settingsDotTeeExTee);
    return;
  }
  public static double calculateScore(int points, int totalTimer, boolean isEndless){
    double score;
    if(isEndless){
      score=points;
    }
    else{
      score=Math.pow((double) totalTimer, (double) -1);
    }
    return score;
  }

  public static boolean isHighScore(boolean isEndless, boolean isPowerUp, int difficulty, int points, int totalTimer){
    ArrayList<Integer> prevScores=getHighScore(isEndless, isPowerUp, difficulty);
    //double score=calculateScore(points, totalTimer, isEndless);
    if (isEndless){
      int score = points;
      if (score>prevScores.get(0)){
        if (score>prevScores.get(1)){
          if (score>prevScores.get(2)){
            //high score 2. replace highest score.
            setHighScore(isEndless, isPowerUp, difficulty, 2, score);
            return true;
          }else if (score==prevScores.get(2)){
            //high score 1. replace middle score.

            setHighScore(isEndless, isPowerUp, difficulty, 1, score);
            return true;
          }else{
            //high score 1. replace middle score.
            setHighScore(isEndless, isPowerUp, difficulty, 1, score);
            return true;
          }
        }else if (score==prevScores.get(1)){
          //high score 0. replace lowest score.
          setHighScore(isEndless, isPowerUp, difficulty, 0, score);
          return true;
        }else{
          //high score 0. replace lowest score.
          setHighScore(isEndless, isPowerUp, difficulty, 0, score);
          return true;
        }
      }else{
        //no high score.
        return false;
      }
    }else{
      double score=Math.pow(totalTimer, -1);
      ArrayList<Double> prevScoresD = new ArrayList<Double>();
      for (int i=0;i<3;i++){
        if (Double.valueOf(prevScores.get(i))==0){
          prevScoresD.add(0.0);
        }else{
          prevScoresD.add((Math.pow(Double.valueOf(prevScores.get(i)), -1)));
        }
      }
      if (score>prevScoresD.get(0)){
        if (score>prevScoresD.get(1)){
          if (score>prevScoresD.get(2)){
            //high score 2. replace highest score.
            score=Math.pow(score, -1);
            int iscore=(int) score;
            setHighScore(isEndless, isPowerUp, difficulty, 2, iscore);
            return true;
          }else if (score==prevScores.get(2)){
            //high score 1. replace middle score.

            score=Math.pow(score, -1);
            int iscore=(int) score;
            setHighScore(isEndless, isPowerUp, difficulty, 1, iscore);
            return true;
          }else{
            //high score 1. replace middle score.
            score=Math.pow(score, -1);
            int iscore=(int) score;
            setHighScore(isEndless, isPowerUp, difficulty, 1, iscore);
            return true;
          }
        }else if (score==prevScores.get(1)){
          //high score 0. replace lowest score.
          score=Math.pow(score, -1);
          int iscore=(int) score;
          setHighScore(isEndless, isPowerUp, difficulty, 0, iscore);
          return true;
        }else{
          //high score 0. replace lowest score.
          score=Math.pow(score, -1);
          int iscore=(int) score;
          setHighScore(isEndless, isPowerUp, difficulty, 0, iscore);
          return true;
        }
      }else{
        //no high score.
        return false;
      }
    }
  }
}



/** GAMETYPE IF-ELSE SECTION
 if (isEndless){
    if (isPowerUp){
        if (difficulty==0){
            //easy powerup endless
        }else if (difficulty==1){
            //medium powerup endless
        }else if (difficulty==2){
            //hard powerup endless
        }else{
            //error difficulty out of range
            throw new IllegalArgumentException("Difficulty variable out of range");
        }
    }else{
        if (difficulty==0){
            //easy not powerup endless
        }else if (difficulty==1){
            //medium not powerup endless
        }else if (difficulty==2){
            //hard not powerup endless
        }else{
            //error difficulty out of range
            throw new IllegalArgumentException("Difficulty variable out of range");
        }
    }
 }else{
    if (isPowerUp){
        if (difficulty==0){
            //easy powerup scenario
        }else if (difficulty==1){
            //medium powerup scenario
        }else if (difficulty==2){
            //hard powerup scenario
        }else{
            //error difficulty out of range
            throw new IllegalArgumentException("Difficulty variable out of range");
        }
    }else{
        if (difficulty==0){
            //easy not powerup scenario
        }else if (difficulty==1){
            //medium not powerup scenario
        }else if (difficulty==2){
            //hard not powerup scenario
        }else{
            //error difficulty out of range
            throw new IllegalArgumentException("Difficulty variable out of range");
        }
    }
 }
 */

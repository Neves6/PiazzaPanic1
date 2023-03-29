package com.neves6.piazzapanic.powerups;


import static java.lang.Math.random;

public class PowerUpRunner {
  DoubleSpeed doublespeed = new DoubleSpeed(30000L);
  ShorterMachineTime shorterMachineTime = new ShorterMachineTime(30000L);

  public PowerUpRunner() {}

  public void activateRandomPowerUp() {
    double random = random();
    if (random < 1 && random > 0) {
      shorterMachineTime.aquirePowerUp();
    }
  }

  public DoubleSpeed getDoubleSpeed() {
    return doublespeed;
  }

  public ShorterMachineTime getShorterMachineTime() {
    return shorterMachineTime;
  }
}

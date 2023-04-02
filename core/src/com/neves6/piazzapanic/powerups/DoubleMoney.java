package com.neves6.piazzapanic.powerups;

import com.neves6.piazzapanic.gamemechanisms.Money;

public class DoubleMoney extends BasePowerUp {
  /**
   * Constructor.
   *
   * @param effectTime How long the power up lasts.
   * @param s
   */
  public DoubleMoney(Long effectTime, String s) {
    super(effectTime, s);
  }

  public void applyPowerUp(Money money) {
    if (this.acquired && !endTime()) {
      money.incrementBalance();
    }
    return;
  }

  public void endPowerUp() {
    if (this.acquired && endTime()) {
      this.acquired = false;
      this.startTime = 0L;
    }
  }
}

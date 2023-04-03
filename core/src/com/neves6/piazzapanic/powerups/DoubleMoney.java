package com.neves6.piazzapanic.powerups;

import com.neves6.piazzapanic.gamemechanisms.Money;

/** A power-up which increased the amount of money earned from completing an order. */
public class DoubleMoney extends BasePowerUp {
  /**
   * Constructor.
   *
   * @param effectTime How long the power up lasts.
   * @param name Display name given to the powerup.
   */
  public DoubleMoney(Long effectTime, String name) {
    super(effectTime, name);
  }

  /**
   * If the power-up is applied, increment balance. If not, do nothing.
   *
   * @param money Object which contains the currency which can be used to unlock machines.
   */
  public void applyPowerUp(Money money) {
    if (this.acquired) {
      money.incrementBalance();
    }
  }
}

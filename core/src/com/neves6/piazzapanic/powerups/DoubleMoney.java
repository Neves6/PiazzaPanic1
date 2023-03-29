package com.neves6.piazzapanic.powerups;

import com.neves6.piazzapanic.gamemechanisms.Money;

public class DoubleMoney extends BasePowerUp{
    /**
     * Constructor.
     *
     * @param effectTime How long the power up lasts.
     */
    public DoubleMoney(Long effectTime) {
        super(effectTime);
    }

    public void applyPowerUp(Money money){
        if (this.aquired && !endTime()){
            money.incrementBalance();
        }
        return;
    }
}

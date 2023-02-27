package com.neves6.piazzapanic.powerups;

public class BasePowerUp {
    Boolean aquired;
    Long currentTimeMillis;
    Long effectTime;

    public BasePowerUp(Long effectTime) {
        this.aquired = false;
        this.currentTimeMillis = 0L;
        this.effectTime = effectTime;
    }

    public void setTimeOfActivation() {
        if (this.currentTimeMillis == 0L) {
            this.currentTimeMillis = System.currentTimeMillis();
        }
    }

    public Boolean endTime() {
        if (System.currentTimeMillis() - this.currentTimeMillis > effectTime) {
            this.aquired = false;
            return true;
        } else {
            return false;
        }
    }

    public Boolean getAquiredStatus() {
        return this.aquired;
    }
    }

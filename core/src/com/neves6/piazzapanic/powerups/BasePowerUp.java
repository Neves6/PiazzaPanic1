package com.neves6.piazzapanic.powerups;

public class BasePowerUp {
    Boolean aquired;
    Long startTime;
    Long effectTime;

    public BasePowerUp(Long effectTime) {
        this.aquired = false;
        this.startTime = 0L;
        this.effectTime = effectTime;
    }

    public void setStartTime() {
        if (this.startTime == 0L && this.aquired) {
            this.startTime = System.currentTimeMillis();
        }
    }

    public Boolean endTime() {
        if (System.currentTimeMillis() - this.startTime > effectTime) {
            this.aquired = false;
            return true;
        } else {
            return false;
        }
    }

    public Boolean getAquiredStatus() {
        return this.aquired;
    }

    public void aquirePowerUp() {
        this.aquired = true;
    }

    public Long getStartTime(){
        return startTime;
    }

    public Long getEffectTime(){
        return effectTime;
    }
}

package com.neves6.piazzapanic.powerups;

public class TimeFreeze extends BasePowerUp{
    public TimeFreeze(Long effectTime) {
        super(effectTime);
    }

    public float getDelta(float delta){
        if (getAquiredStatus()){
            if (endTime() == false){
                setStartTime();
                return 0;
            }
        }
        return delta;
    }
}

package com.neves6.piazzapanic.powerups;

import com.neves6.piazzapanic.Chef;

import java.util.ArrayList;

public class DoubleSpeed extends BasePowerUp{

    public DoubleSpeed(Long effectTime) {
        super(effectTime);
    }

    public ArrayList<Chef> applyPowerUp(ArrayList<Chef> chefs) {
        if (chefs.size() < 1){
            throw new IllegalArgumentException("Chefs list must have at least two chefs in.");
        }
        if (this.aquired) {
            setStartTime();
            for (Chef chef : chefs) {
                chef.alterSpeed(2);
            }
        }
        return chefs;
    }

    public ArrayList<Chef> endPowerUp(ArrayList<Chef> chefs) {
        if (chefs.size() < 1){
            throw new IllegalArgumentException("Chefs list must have at least two chefs in.");
        }
        if (this.aquired && endTime()) {
            for (Chef chef : chefs) {
                chef.alterSpeed(1);
            }
        }
        return chefs;
    }
}


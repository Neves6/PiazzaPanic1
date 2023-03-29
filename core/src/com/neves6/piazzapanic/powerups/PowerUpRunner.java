package com.neves6.piazzapanic.powerups;

import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.people.Chef;

import java.util.ArrayList;
import java.util.Map;

import static java.lang.Math.random;

public class PowerUpRunner {
    DoubleSpeed doublespeed = new DoubleSpeed(30000L);
    ShorterMachineTime shorterMachineTime = new ShorterMachineTime(30000L);
    AutoCook autoCook = new AutoCook(30000L);
    TimeFreeze timeFreeze = new TimeFreeze(30000L);
    DoubleMoney doubleMoney = new DoubleMoney(30000L);
    ArrayList<Chef> chefs;
    Map<String, Machine> machines;
    Money money;


    public PowerUpRunner(ArrayList<Chef> chefs, Map<String, Machine> machines, Money money){
        this.chefs = chefs;
        this.machines = machines;
        this.money = money;
    }

    public void activateRandomPowerUp(){
        double random = random();
        if (random < 1 && random > 0){
            doublespeed.aquirePowerUp();}

        chefs = doublespeed.applyPowerUp(chefs);
        machines = shorterMachineTime.applyPowerUp(machines);
        doubleMoney.applyPowerUp(money);
    }

    public Float updateValues(Float delta){
        doublespeed.endPowerUp(chefs);
        shorterMachineTime.endPowerUp(machines);
        autoCook.applyPowerUp(machines);
        return timeFreeze.getDelta(delta);
    }
}

package com.neves6.piazzapanic.powerups;

import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.people.Chef;

import java.util.ArrayList;
import java.util.Map;

import static java.lang.Math.random;

public class PowerUpRunner {
    DoubleSpeed doublespeed = new DoubleSpeed(30000L, "x2 Speed for 30 seconds");
    ShorterMachineTime shorterMachineTime = new ShorterMachineTime(30000L, "1/2 machine time for 30 seconds");
    AutoCook autoCook = new AutoCook(30000L, "no wait on a machine");
    TimeFreeze timeFreeze = new TimeFreeze(30000L, "time freeze for 30 seconds");
    DoubleMoney doubleMoney = new DoubleMoney(30000L, "double money for 30 seconds");
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

    public String displayText(){
        return "Current Active Powerups: \n" +
                doublespeed.prettyPrint() +
                doubleMoney.prettyPrint() +
                shorterMachineTime.prettyPrint() +
                autoCook.prettyPrint() +
                timeFreeze.prettyPrint();
    }

}

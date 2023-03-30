package com.neves6.piazzapanic.powerups;

import com.neves6.piazzapanic.gamemechanisms.Machine;
import com.neves6.piazzapanic.gamemechanisms.Money;
import com.neves6.piazzapanic.people.Chef;

import java.util.ArrayList;
import java.util.Map;

import static java.lang.Math.random;

public class PowerUpRunner {
    CheaperMachineUnlock cheaperMachineUnlock = new CheaperMachineUnlock(30000L, "1/2 price machines for 30 seconds");
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
        int random = (int) Math.round(random() / 0.25);
        switch (random) {
            case 0:
                cheaperMachineUnlock.aquirePowerUp();
                break;
            case 1:
                shorterMachineTime.aquirePowerUp();
                break;
            case 2:
                doubleMoney.aquirePowerUp();
            case 3:
                autoCook.aquirePowerUp();
            case 4:
                timeFreeze.aquirePowerUp();
        }
        machines = shorterMachineTime.applyPowerUp(machines);
        doubleMoney.applyPowerUp(money);
        cheaperMachineUnlock.applyPowerUp(money.getUnlockDetails());
    }

    public Float updateValues(Float delta){
        shorterMachineTime.endPowerUp(machines);
        autoCook.applyPowerUp(machines);
        cheaperMachineUnlock.endPowerUp(money.getUnlockDetails());
        return timeFreeze.getDelta(delta);
    }

    public String displayText(){
        return "Current Active Powerups: \n" +
                cheaperMachineUnlock.prettyPrint() +
                doubleMoney.prettyPrint() +
                shorterMachineTime.prettyPrint() +
                autoCook.prettyPrint() +
                timeFreeze.prettyPrint();
    }

}

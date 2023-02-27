package com.neves6.piazzapanic.powerups;

import com.neves6.piazzapanic.Machine;

import java.util.ArrayList;

public class ShorterMachineTime extends BasePowerUp{
    public ShorterMachineTime(String name, Long effectTime) {
        super(effectTime);
    }

    public ArrayList<Machine> applyPowerUp(ArrayList<Machine> machines) {
        if (machines.size() < 1){
            throw new IllegalArgumentException("Chefs list must have at least two chefs in.");
        }
        setTimeOfActivation();
        for (Machine machine: machines) {
            machine.changeProcessingTime(machine.getProcessingTime() / 2);
        }
        return machines;
    }

    public ArrayList<Machine> endPowerUp(ArrayList<Machine> machines) {
        if (machines.size() < 1){
            throw new IllegalArgumentException("Chefs list must have at least two chefs in.");
        }
        setTimeOfActivation();
        for (Machine machine: machines) {
            machine.changeProcessingTime(machine.getProcessingTime() * 2);
        }
        return machines;
    }
}

package com.neves6.piazzapanic.powerups;

import com.neves6.piazzapanic.Machine;

import java.util.ArrayList;

public class AutoCook extends BasePowerUp{
    public AutoCook(Long effectTime) {
        super(effectTime);
    }

    public void applyPowerUp(ArrayList<Machine> machines){
        for (Machine machine: machines) {
            if (machine.getActive() == true) {
                machine.incrementRuntime(machine.getProcessingTime() - machine.getRuntime());
                this.aquired = false;
                return;
            }
        }
    }

}

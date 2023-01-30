package com.neves6.piazzapanic;

public class Machine {
    private final String type;
    private final String input;
    private final String output;
    private final float processingTime;
    private final Boolean sticky;
    private Boolean active;
    private float runtime;
    private Chef operator;

    public Machine(String type, String input, String output, float processingTime, Boolean sticky){
        this.type = type;
        this.input = input;
        this.output = output;
        this.processingTime = processingTime;
        this.sticky = sticky;
        this.active = false;
    }

    public void process(Chef chef){
        if (input == "" && processingTime == 0) {
            chef.addToInventory(output);
        } else if (chef.getInventory().peek() == input) {
            active = true;
            chef.getInventory().pop();
            chef.setIsStickied(sticky);
            operator = chef;
        }
    }

    public void attemptGetOutput(){
        Chef chef = operator;
        if (active && runtime >= processingTime) {
            chef.addToInventory(output);
            chef.setIsStickied(false);
            active = false;
            runtime = 0;
        }
    }

    public void incrementRuntime(float delta){
        this.runtime += delta;
    }

    public void resetRuntime(){
        this.runtime = 0;
    }

    public float getRuntime(){
        return runtime;
    }
    public boolean getActive(){
        return active;
    }
}

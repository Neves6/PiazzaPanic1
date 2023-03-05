package com.neves6.piazzapanic;

/**
 * Machine class.
 * Represents a machine or station in the game.
 */
public class Machine {
    private final String type;
    private final String input;
    private final String output;
    private float processingTime;
    private final Boolean sticky;
    private Boolean active;
    private float runtime;
    private Chef operator;
    private String unlockID;

    /**
     * Machine constructor.
     * @param type Type of machine.
     * @param input Input ingredient.
     * @param output Output ingredient.
     * @param processingTime Processing time.
     * @param sticky Whether or not the machine locks the chef in place during use.
     */
    public Machine(String type, String input, String output, float processingTime, Boolean sticky, String unlockID){
        this.type = type;
        this.input = input;
        this.output = output;
        this.processingTime = processingTime;
        this.sticky = sticky;
        this.active = false;
        this.unlockID = unlockID;
    }

    /**
     * Machine constructor.
     * @param type Type of machine.
     * @param input Input ingredient.
     * @param output Output ingredient.
     * @param processingTime Processing time.
     * @param sticky Whether or not the machine locks the chef in place during use.
     */
    public Machine(String type, String input, String output, float processingTime, Boolean sticky){
        this.type = type;
        this.input = input;
        this.output = output;
        this.processingTime = processingTime;
        this.sticky = sticky;
        this.active = false;
        this.unlockID = "auto";
    }

    /**
     * Begins the machine processing of the ingredient.
     * @param chef Which chef is using the machine.
     */
    public void process(Chef chef, Money currency){
        if (!(currency.isUnlocked(this.unlockID))){
            return;
        }
        if (input == "" && processingTime == 0) {
            chef.addToInventory(output);
        } else if (chef.getInventory().peek() == input) {
            active = true;
            chef.getInventory().pop();
            chef.setIsStickied(sticky);
            chef.setMachineInteractingWith(this);
            operator = chef;
        }
    }

    /**
     * Checks if the machine is done processing and adds the output to the chef's inventory if it is.
     * Handles unsticking the chef.
     */
    public void attemptGetOutput(){
        Chef chef = operator;
        if (active && runtime >= processingTime) {
            chef.addToInventory(output);
            chef.setIsStickied(false);
            chef.setMachineInteractingWith(null);
            active = false;
            runtime = 0;
        }
    }

    public void incrementRuntime(float delta){
        this.runtime += delta;
    }

    public float getRuntime(){
        return runtime;
    }
    public boolean getActive(){
        return active;
    }

    public float getProcessingTime(){
        return processingTime;
    }

    // Use this for auto cook and shorter cook.
    public void changeProcessingTime(float newTime){this.processingTime = newTime;}
}

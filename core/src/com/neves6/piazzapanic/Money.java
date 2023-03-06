package com.neves6.piazzapanic;

import java.util.*;

/***
 * Class to handle money which can be used to unlocked machines.
 */
public class Money {
    private int balance;
    private Map<String, ArrayList> unlockDetails;

    /***
     * Constructor where you always start with 0 balance.
     */
    public Money(){
        this.balance = 0;
        this.unlockDetails = new HashMap();
        // Anything that doesn't need unlocking.
        ArrayList<Integer> tempNull = new ArrayList<>();
        // First index represents the price and seconds represents a boolean value represented as an int.
        tempNull.add(0);
        tempNull.add(1);
        unlockDetails.put("auto", tempNull);
    }

    /***
     * Pretty print method to display the balance.
     * @return A message that states what the balance is.
     */
    public String displayBalance(){
        return "Balance: $" + this.balance;
    }

    /***
     * Increments balance by a fixed amount.
     */
    public void incrementBalance(){
        this.balance += 100;
    }

    public boolean addGroup(String key, Integer unlockFee){
        if (unlockDetails.containsKey(key)){
            return false;
        } else {
            ArrayList<Integer> tempList = new ArrayList<>();
            tempList.add(unlockFee);
            tempList.add(0);
            unlockDetails.put(key, tempList);
            return true;
        }
    }
    
    public boolean isUnlocked(String unlockID){
        ArrayList<Integer> i = unlockDetails.get(unlockID);
        return i.get(1) == 1;
    }

    public boolean unlockMachine(String unlockID){
        ArrayList<Integer> i = unlockDetails.get(unlockID);
        if (this.balance >= i.get(0)){
            this.balance -= i.get(0);
            i.set(1, 1);
            return true;
        } else{
            return false;
        }
    }
}

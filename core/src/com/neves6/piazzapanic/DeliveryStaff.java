package com.neves6.piazzapanic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class DeliveryStaff {
    private ArrayList<Integer> xSequence;
    private ArrayList<Integer> ySequence;
    private Stack<String> items;
    private int counter;
    private boolean collect;
    private Long time = 0L;

    public DeliveryStaff(ArrayList<Integer> xSequence, ArrayList<Integer> ySequence) {
        this.items = new Stack<>();
        this.xSequence = xSequence;
        this.ySequence = ySequence;
        this.counter = 0;
    }

    public void collectItem(String item){
        items.push(item);
        this.collect = true;
    }

    public Stack<String> getItems(){
        Stack<String> copy = (Stack<String>) items.clone();
        items.pop();
        return copy;
    }

    public ArrayList<Integer> getCoordInSeq(){
        if (counter + 1 >= xSequence.size()){
            counter = 0;
            this.collect = false;
        } else if (System.currentTimeMillis() - time > 500){
            counter ++;
            time = System.currentTimeMillis();
        }
        return new ArrayList<>(Arrays.asList(xSequence.get(counter), ySequence.get(counter)));
    }

    public boolean getCollect() {
        return this.collect;
    }

}

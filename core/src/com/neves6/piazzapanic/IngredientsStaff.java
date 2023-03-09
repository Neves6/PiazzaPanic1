package com.neves6.piazzapanic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class IngredientsStaff{
    private String currentRecipe;
    private Stack<String> stack;
    private boolean generate;
    private ArrayList<Integer> xSequence;
    private ArrayList<Integer> ySequence;
    private Integer counter;
    private Long fridgeTime = 0L;
    private boolean collect;

    public IngredientsStaff(ArrayList<Integer> xSequence, ArrayList<Integer> ySequence) {
        this.stack = new Stack<>();
        this.generate = true;
        this.xSequence = xSequence;
        this.ySequence = ySequence;
        this.counter = 0;
    }

    public void generateStack(){
        if (currentRecipe == "salad"){
            this.stack.push("onion");
            this.stack.push("tomato");
            this.stack.push("lettuce");
        } else if (currentRecipe == "jacket potato"){
            this.stack.push("beans");
            this.stack.push("potato");
        } else if (currentRecipe == "pizza"){
            this.stack.push("cheese");
            this.stack.push("tomato");
            this.stack.push("dough");
        } else if (currentRecipe == "hamburger"){
            this.stack.push("meat");
            this.stack.push("bun");
        } else{
            throw new IllegalArgumentException("Must be a valid recipe.");
        }
    }

    public void setCurrentRecipe(String recipe){
        if (this.generate) {
            this.currentRecipe = recipe;
            generateStack();
            this.collect = true;
            this.generate = false;
        }
    }

    public String collectItem(){
        if (this.stack.size() > 0){
            return this.stack.pop();
        } else{
            return null;
        }
    }

    public void setGenerate(boolean generate){
        this.generate = generate;

    }

    public ArrayList<Integer> getCoordInSeq(){
       if (counter + 1 >= xSequence.size()){
           counter = 0;
           this.collect = false;
       } else if (System.currentTimeMillis() - fridgeTime > 400){
           counter ++;
           fridgeTime = System.currentTimeMillis();
       }
       return new ArrayList<>(Arrays.asList(xSequence.get(counter), ySequence.get(counter)));
    }


    public boolean getCollect() {
        return this.collect;
    }
}

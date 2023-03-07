package com.neves6.piazzapanic;

import java.util.Stack;

public class IngredientsStaff{
    private String currentRecipe;
    private Stack<String> stack;
    private boolean generate;

    public IngredientsStaff() {
        this.stack = new Stack<>();
        this.generate = true;
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
}

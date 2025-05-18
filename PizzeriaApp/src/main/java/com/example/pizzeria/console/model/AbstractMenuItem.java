package com.example.pizzeria.console.model;

public abstract class AbstractMenuItem implements MenuItem{

    private final String labelText;

    protected AbstractMenuItem(String text){
        this.labelText = text;
    }

    @Override
    public String text(){
        return labelText;
    }

    @Override
    public abstract void execute();

}

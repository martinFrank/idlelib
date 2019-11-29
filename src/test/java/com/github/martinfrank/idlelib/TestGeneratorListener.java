package com.github.martinfrank.idlelib;

public class TestGeneratorListener implements GeneratorListener {

    private boolean hasBeenNotified = false;

    @Override
    public void notifyYield(Generator generator) {
        hasBeenNotified = true;
        System.out.println("hei - i am ready to yield...");
    }

    public void reset(){
        hasBeenNotified = false;
    }

    public boolean hasBeenNotified(){
        return hasBeenNotified;
    }
}

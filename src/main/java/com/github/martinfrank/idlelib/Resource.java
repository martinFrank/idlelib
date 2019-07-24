package com.github.martinfrank.idlelib;

public class Resource<T extends ResourceType> {

    private double amount;
    private T resourceType;

    public Resource(T resourceType){
        this.resourceType = resourceType;
    }

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public T getResourceType(){
        return resourceType;
    }
}

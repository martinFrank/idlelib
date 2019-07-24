package com.github.martinfrank.idlelib;

import java.util.ArrayList;
import java.util.List;

public class Site {

    private final ResourceManager resourceManager;
    private final List<TimedComponent> components = new ArrayList<>();

    public Site(ResourceManager resourceManager){
        this.resourceManager = resourceManager;
    }

    public void add(TimedComponent timedComponent, Location location){
        resourceManager.register(timedComponent);
    }

    public void remove(TimedComponent timedComponent){
        resourceManager.deregister(timedComponent);

    }

}

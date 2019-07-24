package com.github.martinfrank.idlelib;

import java.util.HashMap;
import java.util.Map;

public class Site<R extends Resource<? extends ResourceType> > {

    private final ResourceManager<R> resourceManager;

    private final Map<Location,TimedComponent<R>> components = new HashMap<>();


    public Site(ResourceManager<R> resourceManager){
        this.resourceManager = resourceManager;
    }

    public void add(TimedComponent<R> timedComponent, Location location){
        components.put(location, timedComponent);
        resourceManager.register(timedComponent);
    }

    public boolean isLocationAvailable(Location location){
        return true;
    }

    public void remove(TimedComponent<R> timedComponent){
        resourceManager.deregister(timedComponent);

    }

}

package com.github.martinfrank.idlelib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceManager<R extends Resource<? extends ResourceType> > implements ResourceManagerListener<R> {

    private final Timer timer;
    private Map<ResourceType,R> resources = new HashMap<>();

    public ResourceManager (Timer timer){
        this.timer = timer;
    }

    public void register(TimedComponent<R> timedComponent){
        timer.register(timedComponent);
        timedComponent.setResourceManagerListener(this);
    }

    public void deregister(TimedComponent<R> timedComponent){
        timer.deregister(timedComponent);
        timedComponent.removeResourceManagerListener();
    }

    public double getResource(ResourceType t){
        if (resources.get(t) != null){
            return resources.get(t).getAmount();
        }
        return 0;
    }


    @Override
    public void notifyYield(List<R> yieldedResources) {
        for (R yieldedResource: yieldedResources){
            ResourceType type = yieldedResource.getResourceType();
            R inStack = resources.get(type);
            if (inStack == null){
                resources.put(type, yieldedResource);
            }else{
                inStack.setAmount(inStack.getAmount()+yieldedResource.getAmount());
            }
        }
    }
}

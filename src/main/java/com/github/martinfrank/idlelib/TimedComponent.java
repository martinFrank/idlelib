package com.github.martinfrank.idlelib;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface TimedComponent <R extends Resource<? extends ResourceType>> {


    void tick(TimeUnit timeUnit, long timeAmount);

    boolean isComplete();

    List<R> yield();

    void setPause(boolean isPaused);

    boolean isPaused();

    void setResourceManagerListener(ResourceManager<R> resourceManager);

    void removeResourceManagerListener();
}

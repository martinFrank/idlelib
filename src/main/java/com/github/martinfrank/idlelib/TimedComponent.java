package com.github.martinfrank.idlelib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimedComponent<R extends Resource<? extends ResourceType>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimedComponent.class);
    private final TimeUnit timeUnit;
    private final long maximum;
    private final List<R> yield;
    private final Shape shape;
    private long current;
    private ResourceManager<R> resourceManager;
    private boolean isPaused;

    public TimedComponent(long maximum, TimeUnit timeUnit, List<R> yield, Shape shape) {
        this.maximum = maximum;
        this.timeUnit = timeUnit;
        this.yield = yield;
        this.shape = shape;
    }

    void tick(TimeUnit timeUnit, long timeAmount) {
        if (!isPaused) {
            current = current + this.timeUnit.convert(timeAmount, timeUnit);
            LOGGER.debug("value: {}/{}", current, maximum);
        } else {
            LOGGER.debug("value: paused!!! {}/{}", current, maximum);
        }
    }

    public boolean isComplete() {
        return current >= maximum;
    }

    public List<R> yield() {
        resourceManager.notifyYield(yield);
        resetCounter();
        return yield;
    }


    public Shape getShape() {
        return shape;
    }

    public void setPause(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    void setResourceManagerListener(ResourceManager<R> resourceManager) {
        this.resourceManager = resourceManager;
    }

    void removeResourceManagerListener() {
        resourceManager = null;
    }

    private void resetCounter() {
        current = 0;
    }
}

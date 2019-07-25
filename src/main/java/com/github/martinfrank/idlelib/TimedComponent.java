package com.github.martinfrank.idlelib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimedComponent<R extends Resource<? extends ResourceType>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimedComponent.class);
    private final TimeUnit timeUnit;
    private final long maximum;
    private final List<R> yield;
    private final Shape shape;
    private ResourceManagerListener<R> resourceManagerListener;
    private long current;
    private boolean isPaused;
    private boolean isAutoYield;
    private boolean isTicked;

    public TimedComponent(long maximum, TimeUnit timeUnit, List<R> yield, Shape shape) {
        this.maximum = maximum;
        this.timeUnit = timeUnit;
        this.yield = yield;
        this.shape = shape;
    }

    void autoClick(ClickValue clickValue) {
        if (isRunning() && isTicked()) {
            addTime(clickValue);
        } else {
            LOGGER.debug("autoClick - paused");
        }
    }

    public void click(ClickValue clickValue) {
        addTime(clickValue);
    }


    private void addTime(ClickValue clickValue) {
        long timeAmount = clickValue.getTimeAmount();
        TimeUnit timeUnit = clickValue.getTimeUnit();
        current = current + this.timeUnit.convert(timeAmount, timeUnit);
        LOGGER.debug("new value: {}/{} {}", current, maximum, timeUnit);
        if (isAutoYield() && isComplete()) {
            LOGGER.debug("auto yield");
            yield();
        }
    }

    private boolean isRunning() {
        return !isPaused;
    }

    public boolean isComplete() {
        return current >= maximum;
    }

    public List<R> yield() {
        if (isComplete() && hasStorage()) {
            resourceManagerListener.notifyYield(yield);
            resetCounter();
            return yield;
        }
        return Collections.emptyList();
    }

    private boolean hasStorage() {
        return resourceManagerListener.hasStorage(yield);
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

    void setResourceManagerListener(ResourceManagerListener<R> resourceManager) {
        this.resourceManagerListener = resourceManager;
    }

    void removeResourceManagerListener() {
        resourceManagerListener = null;
    }

    public boolean isTicked() {
        return isTicked;
    }

    public void setTicked(boolean ticked) {
        isTicked = ticked;
    }

    private void resetCounter() {
        current = 0;
    }

    public boolean isAutoYield() {
        return isAutoYield;
    }

    public void setAutoYield(boolean autoYield) {
        isAutoYield = autoYield;
    }

    public Progress getProgress() {
        return new Progress(current, maximum, timeUnit);
    }
}

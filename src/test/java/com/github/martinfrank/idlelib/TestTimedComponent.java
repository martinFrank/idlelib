package com.github.martinfrank.idlelib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestTimedComponent implements TimedComponent<TestResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestTimedComponent.class);
    private static final long MAXIMUM_IN_MILLIS = 750;
    private long currentInMillis;
    private ResourceManager<TestResource> resourceManager;

    @Override
    public void tick(TimeUnit timeUnit, long timeAmount) {
        currentInMillis = currentInMillis + timeUnit.toMillis(timeAmount);
        LOGGER.debug("value: {}/{}",currentInMillis,MAXIMUM_IN_MILLIS);
    }

    @Override
    public boolean isComplete() {
        return currentInMillis >= MAXIMUM_IN_MILLIS;
    }

    @Override
    public List<TestResource> yield() {
        List<TestResource> resources = calculateYield();
        resourceManager.notifyYield(resources);
        resetCounter();
        return resources;
    }

    private void resetCounter() {
        currentInMillis = 0;
    }


    @Override
    public void setPause(boolean isPaused) {

    }

    @Override
    public boolean isPaused() {
        return false;
    }

    @Override
    public void setResourceManagerListener(ResourceManager<TestResource> resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Override
    public void removeResourceManagerListener() {
        resourceManager = null;
    }

    private List<TestResource> calculateYield() {
        List<TestResource> resources = new ArrayList<>();
        TestResource timber = new TestResource(TestResourceType.TIMBER);
        timber.setAmount(1);
        resources.add(timber);
        return resources;
    }
}

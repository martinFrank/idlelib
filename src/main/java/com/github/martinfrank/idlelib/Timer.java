package com.github.martinfrank.idlelib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Timer{

    private static final Logger LOGGER = LoggerFactory.getLogger(Timer.class);

    private TimeUnit pollTimeUnit;
    private long pollTime;
    private final ScheduledExecutorService scheduledExecutorService;
    private final List<Generator> timedComponents = Collections.synchronizedList(new ArrayList<>());

    private Runnable createTickRunner() {
        return () -> {
            synchronized (timedComponents) {
                timedComponents.forEach(t -> t.tick(pollTime, pollTimeUnit));
            }
        };
    }

    public Timer(long pollTime, TimeUnit timeUnit) {
        scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        this.pollTimeUnit = timeUnit;
        this.pollTime = pollTime;
        scheduledExecutorService.scheduleAtFixedRate(createTickRunner(), 0, this.pollTime, pollTimeUnit);
        LOGGER.debug("timer is now running");
    }

    public Timer() {
        this(25, TimeUnit.MILLISECONDS);
    }


    public void stop(){
        LOGGER.debug("timer is now shutting down");
        scheduledExecutorService.shutdown();
    }

    public void register(Generator component){
        synchronized (timedComponents) {
            timedComponents.add(component);
        }
    }

    public void deregister(Generator component){
        synchronized (timedComponents) {
            timedComponents.remove(component);
        }
    }

}

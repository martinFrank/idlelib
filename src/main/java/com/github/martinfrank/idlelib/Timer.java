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
    private final List<TimedComponent> timedComponents = Collections.synchronizedList(new ArrayList<>());

    private Runnable createTickRunner() {
        return () -> {
            synchronized (timedComponents) {
                LOGGER.debug("tick");
                timedComponents.forEach(t -> t.tick(pollTime, pollTimeUnit));
            }
        };
    }

    public Timer() {
        scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        this.pollTimeUnit = TimeUnit.MILLISECONDS;
        this.pollTime = 25;
        scheduledExecutorService.scheduleAtFixedRate(createTickRunner(), 0,  pollTime , pollTimeUnit);
        LOGGER.debug("timer is now running");
    }

    public void setPolling(TimeUnit pollTimeUnit, long pollTime){
        this.pollTimeUnit = pollTimeUnit;
        this.pollTime = pollTime;
    }

    public void stop(){
        scheduledExecutorService.shutdown();
    }

    public void register(TimedComponent component){
        synchronized (timedComponents) {
            timedComponents.add(component);
        }
    }

    public void deregister(TimedComponent component){
        synchronized (timedComponents) {
            timedComponents.remove(component);
        }
    }

}

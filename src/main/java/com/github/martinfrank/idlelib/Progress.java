package com.github.martinfrank.idlelib;

import java.util.concurrent.TimeUnit;

public class Progress {

    private final long current;
    private final long maximum;
    private final TimeUnit timeUnit;

    public Progress(long current, long maximum, TimeUnit timeUnit) {
        this.current = current;
        this.maximum = maximum;
        this.timeUnit = timeUnit;
    }

    public double getPercent() {
        return (double) current / (double) maximum;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}

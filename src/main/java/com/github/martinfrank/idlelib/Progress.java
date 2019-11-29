package com.github.martinfrank.idlelib;

import java.util.concurrent.TimeUnit;

public class Progress {

    private long current;
    private final long maximum;
    private final TimeUnit timeUnit;

    public Progress(long current, long maximum, TimeUnit timeUnit) {
        this.current = current;
        this.maximum = maximum;
        this.timeUnit = timeUnit;
    }

    public double getPercent() {
        return getPercent(false);
    }

    public double getPercent(boolean hasLimit) {
        if (hasLimit) {
            return Math.min(100, (double) current / (double) maximum);
        } else {
            return (double) current / (double) maximum;
        }
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
    public long getCurrent() {
        return current;
    }
    public long getMaximum() {
        return maximum;
    }

    void add(long timeAmount, TimeUnit timeUnit) {
        current = current + this.timeUnit.convert(timeAmount, timeUnit);
    }

    public boolean isComplete() {
        return current >= maximum;
    }

     void reset() {
        current = 0;
    }

    @Override
    public String toString() {
        return ""+getCurrent()+"/"+getMaximum()+" "+getTimeUnit();
    }
}

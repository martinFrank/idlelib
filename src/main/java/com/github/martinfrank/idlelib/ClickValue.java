package com.github.martinfrank.idlelib;

import java.util.concurrent.TimeUnit;

public class ClickValue {

    private final long timeAmount;
    private final TimeUnit timeUnit;

    public ClickValue(long value, TimeUnit timeUnit) {
        this.timeAmount = value;
        this.timeUnit = timeUnit;
    }

    public long getTimeAmount() {
        return timeAmount;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}

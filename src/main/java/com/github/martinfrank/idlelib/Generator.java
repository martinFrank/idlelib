package com.github.martinfrank.idlelib;

import java.util.concurrent.TimeUnit;

public class Generator<R> {

    private GeneratorListener generatorListener;
    private final Output<R> output;
    private boolean isPaused;
    private final Progress progress;
    private boolean isListenerNotified = false;

    public Generator(long maximum, TimeUnit timeUnit, Output<R> output) {
        progress = new Progress(0, maximum, timeUnit);
        this.output = output;
    }

    void tick(long timeAmount, TimeUnit timeUnit) {
        addTime(timeAmount, timeUnit);
    }

    public void click(ClickValue clickValue) {
        addTime(clickValue.getTimeAmount(), clickValue.getTimeUnit());
    }

    private void addTime(long timeAmount, TimeUnit timeUnit) {
        if (!isPaused) {
            progress.add(timeAmount, timeUnit);
            if (isNotificationRequired()) {
                notifyListener();
            }
        }
    }

    private void notifyListener() {
        generatorListener.notifyYield(this);
        isListenerNotified = true;
    }

    public R yield() {
        R r = output.generateOutput(progress);
        progress.reset();
        isListenerNotified = false;
        return r;
    }

    public Progress getProgress() {
        return progress;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPause(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public void setGeneratorListener(GeneratorListener generatorListener) {
        this.generatorListener = generatorListener;
    }

    public void removeGeneratorListener() {
        generatorListener = null;
    }

    private boolean isNotificationRequired() {
        return progress.isComplete() && generatorListener != null && !isListenerNotified;
    }
}


package com.github.martinfrank.idlelib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Generator<R> {

    private GeneratorListener generatorListener;
    private final Output<R> output;
    private boolean isPaused;
    private final Progress progress;

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
        if (!isPaused ) {
            progress.add(timeAmount, timeUnit);
            if (progress.isComplete() && generatorListener != null) {
                generatorListener.notifyYield(this);
            }
        }
    }

    public R yield(){
        R r = output.generateOutput(progress);
        progress.reset();
        return r;
    }

    public Progress getProgress(){
        return progress;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPause(boolean isPaused) {
        this.isPaused = isPaused;
    }

    void setGeneratorListenerListener(GeneratorListener generatorListener) {
        this.generatorListener = generatorListener;
    }

    void removeGeneratorListenerListener() {
        generatorListener = null;
    }

}

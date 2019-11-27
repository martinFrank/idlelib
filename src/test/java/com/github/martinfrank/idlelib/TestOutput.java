package com.github.martinfrank.idlelib;

public class TestOutput implements Output<Double> {

    public static final double MAX = 1000;

    @Override
    public Double generateOutput(Progress progress) {
        return Math.min(1, progress.getPercent())*MAX;
    }
}

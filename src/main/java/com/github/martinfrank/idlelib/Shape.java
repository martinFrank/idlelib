package com.github.martinfrank.idlelib;

import com.github.martinfrank.geolib.GeoPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shape {

    private final List<GeoPoint> points = new ArrayList<>();

    public Shape(List<GeoPoint> points) {
        this.points.addAll(points);
        if (this.points.isEmpty()) {
            this.points.add(new GeoPoint(0, 0));
        }
    }

    public Shape(GeoPoint... points) {
        this(Arrays.asList(points));
    }

    public List<GeoPoint> getPoints() {
        return new ArrayList<>(points);
    }
}

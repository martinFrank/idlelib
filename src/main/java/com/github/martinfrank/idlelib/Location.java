package com.github.martinfrank.idlelib;

import com.github.martinfrank.geolib.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class Location {

    private final Shape shape;
    private final GeoPoint position;


    public Location(GeoPoint position, Shape shape) {
        this.position = position;
        this.shape = shape;
    }

    public List<GeoPoint> getLocationPoints() {
        List<GeoPoint> result = new ArrayList<>();
        for (GeoPoint point : shape.getPoints()) {
            result.add(new GeoPoint(position.getX() + point.getX(), position.getY() + point.getY()));
        }
        return result;
    }
}

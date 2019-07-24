package com.github.martinfrank.idlelib;

import com.github.martinfrank.geolib.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Site<R extends Resource<? extends ResourceType> > {

    private final ResourceManager<R> resourceManager;

    private final Map<Location,TimedComponent<R>> components = new HashMap<>();
    private final List<GeoPoint> sitePoints = new ArrayList<>();


    public Site(ResourceManager<R> resourceManager){
        this.resourceManager = resourceManager;
        //FIXME: constructor with predefined site size
        for (int dy = 0; dy < 10; dy++) {
            for (int dx = 0; dx < 10; dx++) {
                sitePoints.add(new GeoPoint(dx, dy));
            }
        }
    }

    public void add(TimedComponent<R> timedComponent, Location location){
        components.put(location, timedComponent);
        resourceManager.register(timedComponent);
    }

    public boolean isLocationAvailable(Location location){
        List<GeoPoint> free = new ArrayList<>(sitePoints);
        for (Location occupied : components.keySet()) {
            for (GeoPoint point : occupied.getLocationPoints()) {
                free.remove(point);
            }
        }
        for (GeoPoint target : location.getLocationPoints()) {
            if (!free.contains(target)) {
                return false;
            }
        }
        return true;
    }

    public void remove(TimedComponent<R> timedComponent){
        resourceManager.deregister(timedComponent);

    }

}

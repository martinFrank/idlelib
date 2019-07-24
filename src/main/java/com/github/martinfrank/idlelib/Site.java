package com.github.martinfrank.idlelib;

import com.github.martinfrank.geolib.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Site<R extends Resource<? extends ResourceType> > {

    private final ResourceManager<R> resourceManager;

    private final Map<Location,TimedComponent<R>> components = new HashMap<>();
    private final List<GeoPoint> openSlots = new ArrayList<>();


    public Site(ResourceManager<R> resourceManager, List<GeoPoint> openSlots) {
        this.resourceManager = resourceManager;
        this.openSlots.addAll(openSlots);

    }

    public void add(Location location, TimedComponent<R> timedComponent) {
        components.put(location, timedComponent);
        resourceManager.register(timedComponent);
    }

    public boolean isLocationAvailable(Location location){
        List<GeoPoint> free = new ArrayList<>(openSlots);
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

    public void remove(Location location) {
        TimedComponent<R> timedComponent = components.get(location);
        resourceManager.deregister(timedComponent);
        components.remove(location);

    }

}

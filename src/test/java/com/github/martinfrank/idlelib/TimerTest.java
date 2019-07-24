package com.github.martinfrank.idlelib;

import com.github.martinfrank.geolib.GeoPoint;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimerTest {

    @Test
    public void testYield() throws InterruptedException {

        Timer timer = new Timer();
        ResourceManager<TestResource> resourceManager = new ResourceManager<>(timer);
        Site<TestResource> site = new Site<>(resourceManager);
        TimedComponent<TestResource> timedComponent =
                new TimedComponent<>(750, TimeUnit.MILLISECONDS, calculateYield(), new Shape());
        Location location = new Location(new GeoPoint(0, 0), timedComponent.getShape());

        Assert.assertTrue(site.isLocationAvailable(location));
        site.add(timedComponent, location);
        Assert.assertFalse(site.isLocationAvailable(location));

        Assert.assertEquals(0, resourceManager.getResource(TestResourceType.TIMBER), 0.1 );
        TimeUnit.SECONDS.sleep(1);
        Assert.assertTrue(timedComponent.isComplete());
        Assert.assertEquals(0, resourceManager.getResource(TestResourceType.TIMBER), 0.1 );

        timedComponent.yield();

        Assert.assertFalse(timedComponent.isComplete());
        Assert.assertEquals(1, resourceManager.getResource(TestResourceType.TIMBER), 0.1 );

        timer.stop();
    }


    private List<TestResource> calculateYield() {
        List<TestResource> resources = new ArrayList<>();
        TestResource timber = new TestResource(TestResourceType.TIMBER);
        timber.setAmount(1);
        resources.add(timber);
        return resources;
    }
}

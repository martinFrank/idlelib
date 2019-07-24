package com.github.martinfrank.idlelib;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TimerTest {

    @Test
    public void testYield() throws InterruptedException {

        Timer timer = new Timer();
        ResourceManager<TestResource> resourceManager = new ResourceManager<>(timer);
        Site<TestResource> site = new Site<>(resourceManager);
        TestTimedComponent testTimedComponent = new TestTimedComponent();
        Location location = new Location();
        site.add(testTimedComponent, location);


        Assert.assertEquals(0, resourceManager.getResource(TestResourceType.TIMBER), 0.1 );
        TimeUnit.SECONDS.sleep(1);
        Assert.assertTrue(testTimedComponent.isComplete());
        Assert.assertEquals(0, resourceManager.getResource(TestResourceType.TIMBER), 0.1 );

        testTimedComponent.yield();

        Assert.assertFalse(testTimedComponent.isComplete());
        Assert.assertEquals(1, resourceManager.getResource(TestResourceType.TIMBER), 0.1 );

        timer.stop();
    }

}

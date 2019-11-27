package com.github.martinfrank.idlelib;

import com.github.martinfrank.geolib.GeoPoint;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.stream.Location;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.mockito.Mockito.mock;

public class GeneratorTest {

    @Test
    public void testYield() throws InterruptedException {

        Timer timer = new Timer();
        Output<Double> testOutput = new TestOutput();
        Generator<Double> testGenerator = new Generator<>(1000, TimeUnit.MILLISECONDS, testOutput);
        Assert.assertNotNull(testGenerator.getProgress().toString());
        Assert.assertEquals(0, testGenerator.yield(), 0.01 );

        timer.register(testGenerator);
        TimeUnit.MILLISECONDS.sleep(1000);
        Assert.assertTrue(testGenerator.getProgress().isComplete());
        Assert.assertEquals(1000, testGenerator.yield(), 0.01 );
        Assert.assertEquals(0, testGenerator.yield(), 0.01 );

        TimeUnit.MILLISECONDS.sleep(500);
        Assert.assertEquals(500, (double)testGenerator.yield(), 0.01 );

        timer.stop();
    }



    @Test
    public void testClickedYield() {
        ClickValue clickValue = new ClickValue(250, TimeUnit.MILLISECONDS);
        Output<Double> testOutput = new TestOutput();
        Generator<Double> testGenerator = new Generator<>(1000, TimeUnit.MILLISECONDS, testOutput);
        Assert.assertEquals(0, testGenerator.yield(), 0.01 );

        testGenerator.click(clickValue);

        Assert.assertEquals(250, testGenerator.yield(), 0.01 );
        Assert.assertEquals(0, testGenerator.yield(), 0.01 );

        IntStream.range(0, 4).forEach(i -> testGenerator.click(clickValue));
        Assert.assertEquals(1000, testGenerator.yield(), 0.01 );
        Assert.assertEquals(0, testGenerator.yield(), 0.01 );
    }

    @Test
    public void testPause() throws InterruptedException {
        ClickValue clickValue = new ClickValue(250, TimeUnit.MILLISECONDS);
        Output<Double> testOutput = new TestOutput();
        Generator<Double> testGenerator = new Generator<>(1000, TimeUnit.MILLISECONDS, testOutput);
        Assert.assertEquals(0, testGenerator.yield(), 0.01 );

        testGenerator.setPause(true);
        testGenerator.click(clickValue);
        Assert.assertEquals(0, testGenerator.yield(), 0.01 );

        testGenerator.setPause(false);
        testGenerator.click(clickValue);
        Assert.assertEquals(250, testGenerator.yield(), 0.01 );
    }

    @Test
    public void testListener() throws InterruptedException {
        Timer timer = new Timer();
        TestGeneratorListener listener = new TestGeneratorListener();
        Output<Double> testOutput = new TestOutput();
        Generator<Double> testGenerator = new Generator<>(1000, TimeUnit.MILLISECONDS, testOutput);
        Assert.assertNotNull(testGenerator.getProgress().toString());
        Assert.assertEquals(0, testGenerator.yield(), 0.01 );

        timer.register(testGenerator);
        testGenerator.setGeneratorListenerListener(listener);
        TimeUnit.MILLISECONDS.sleep(1000);
        Assert.assertEquals(1000, testGenerator.yield(), 0.01 );
        Assert.assertEquals(0, testGenerator.yield(), 0.01 );
        Assert.assertTrue(listener.hasBeenNotified());

        listener.reset();
        TimeUnit.MILLISECONDS.sleep(500);
        Assert.assertEquals(500, (double)testGenerator.yield(), 0.01 );
        Assert.assertFalse(listener.hasBeenNotified());

        timer.stop();
    }
//
//    @Test
//    public void testProgress() {
//        Timer timer = new Timer();
//        ResourceManager<TestResource> resourceManager = new ResourceManager<>(timer);
//        Site<TestResource> site = new Site<>(resourceManager, createOpenLocation());
//        Generator<TestResource> timedComponent =
//                new Generator<>(1000, TimeUnit.MILLISECONDS, calculateYield(), new Shape());
//        Location location = new Location(new GeoPoint(0, 0), timedComponent.getShape());
//        Assert.assertTrue(site.isLocationAvailable(location));
//        site.add(location, timedComponent);
//        Assert.assertFalse(site.isLocationAvailable(location));
//
//        Assert.assertEquals(0, resourceManager.getResource(TestResourceType.TIMBER), 0.1);
//        ClickValue clickValue = new ClickValue(250, TimeUnit.MILLISECONDS);
//        timedComponent.click(clickValue);
//        Progress progress = timedComponent.getProgress();
//        Assert.assertEquals(0.25, progress.getPercent(), 0.1);
//
//        timedComponent.click(clickValue);
//        progress = timedComponent.getProgress();
//        Assert.assertEquals(0.5, progress.getPercent(), 0.1);
//
//        timer.stop();
//    }
//
//    @Test
//    public void testPause() throws InterruptedException {
//
//        Timer timer = new Timer();
//        ResourceManager<TestResource> resourceManager = new ResourceManager<>(timer);
//        Site<TestResource> site = new Site<>(resourceManager, createOpenLocation());
//        Generator<TestResource> timedComponent =
//                new Generator<>(750, TimeUnit.MILLISECONDS, calculateYield(), new Shape());
//        timedComponent.setTicked(true);
//        Location location = new Location(new GeoPoint(0, 0), timedComponent.getShape());
//        Assert.assertTrue(site.isLocationAvailable(location));
//        site.add(location, timedComponent);
//        Assert.assertFalse(site.isLocationAvailable(location));
//
//        Assert.assertEquals(0, resourceManager.getResource(TestResourceType.TIMBER), 0.1);
//        timedComponent.setPause(true);
//        TimeUnit.SECONDS.sleep(1);
//        Assert.assertFalse(timedComponent.isComplete());
//        Assert.assertEquals(0, resourceManager.getResource(TestResourceType.TIMBER), 0.1);
//
//        timedComponent.setPause(false);
//        TimeUnit.SECONDS.sleep(1);
//        Assert.assertTrue(timedComponent.isComplete());
//        Assert.assertEquals(0, resourceManager.getResource(TestResourceType.TIMBER), 0.1);
//
//        timedComponent.yield();
//
//        Assert.assertFalse(timedComponent.isComplete());
//        Assert.assertEquals(1, resourceManager.getResource(TestResourceType.TIMBER), 0.1);
//
//        site.remove(location);
//        TimeUnit.SECONDS.sleep(2);
//        Assert.assertFalse(timedComponent.isComplete());
//        Assert.assertEquals(1, resourceManager.getResource(TestResourceType.TIMBER), 0.1);
//
//        timer.stop();
//    }
//
//
//    private Output createTestOutput() {
//        Output output = new Output() {
//
//            @Override
//            public Output generateOutput(Progress progress) {
//                return null;
//            }
//        }
//    }
//
//    private List<TestResource> calculateYield() {
//        List<TestResource> resources = new ArrayList<>();
//        TestResource timber = new TestResource(TestResourceType.TIMBER);
//        timber.setAmount(1);
//        resources.add(timber);
//        return resources;
//    }
//
//    private List<GeoPoint> createOpenLocation() {
//        List<GeoPoint> openSlots = new ArrayList<>();
//        for (int dy = 0; dy < 10; dy++) {
//            for (int dx = 0; dx < 10; dx++) {
//                openSlots.add(new GeoPoint(dx, dy));
//            }
//        }
//        return openSlots;
//    }

}

package com.github.martinfrank.idlelib;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class GeneratorTest {

    @Test
    public void testYield() throws InterruptedException {

        Timer timer = new Timer();
        Output<Double> testOutput = new TestOutput();
        Generator<Double> testGenerator = new Generator<>(1000, TimeUnit.MILLISECONDS, testOutput);
        Assert.assertNotNull(testGenerator.getProgress().toString());
        Assert.assertEquals(0, testGenerator.yield(), 0.01 );

        timer.register(testGenerator);
        TimeUnit.MILLISECONDS.sleep(1010);
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
        Assert.assertTrue(testGenerator.isPaused());
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
        testGenerator.setGeneratorListener(listener);
        TimeUnit.MILLISECONDS.sleep(1010);
        Assert.assertEquals(1000, testGenerator.yield(), 0.01 );
        Assert.assertEquals(0, testGenerator.yield(), 0.01 );
        Assert.assertTrue(listener.hasBeenNotified());

        listener.reset();
        TimeUnit.MILLISECONDS.sleep(500);
        Assert.assertEquals(500, (double)testGenerator.yield(), 0.01 );
        Assert.assertFalse(listener.hasBeenNotified());

        timer.stop();
    }

}

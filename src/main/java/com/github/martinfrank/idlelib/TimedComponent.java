package com.github.martinfrank.idlelib;

import java.util.List;

public interface TimedComponent {


    void tick();

    boolean isComplete();

    List<Resource> yield();

    void setPause(boolean isPaused);

    boolean isPaused();

}

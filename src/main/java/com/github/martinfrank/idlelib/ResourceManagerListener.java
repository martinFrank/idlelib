package com.github.martinfrank.idlelib;

import java.util.List;

public interface ResourceManagerListener<R extends Resource<? extends ResourceType>> {

    void notifyYield(List<R> resources);

}

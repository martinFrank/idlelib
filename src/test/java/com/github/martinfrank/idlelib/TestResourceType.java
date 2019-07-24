package com.github.martinfrank.idlelib;

public class TestResourceType implements ResourceType {


    public static final TestResourceType TIMBER = new TestResourceType("TIMBER");

    private final String name;

    public TestResourceType(String name) {
        super();
        this.name = name;
    }
}

package com.github.martinfrank.idlelib;

public class TestResourceType extends ResourceType {


    public static final TestResourceType TIMBER = new TestResourceType("TIMBER");

    private final String name;

    public TestResourceType(String name) {
        super();
        this.name = name;
    }
}

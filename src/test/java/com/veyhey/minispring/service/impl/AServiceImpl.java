package com.veyhey.minispring.service.impl;

import com.veyhey.minispring.service.AService;

public class AServiceImpl implements AService {

    private String name;
    private int level;
    private Integer property1;
    private String property2;

    public AServiceImpl() {
    }

    public AServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public void setProperty1(Integer property1) {
        this.property1 = property1;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public int getProperty1() {
        return property1;
    }

    public String getProperty2() {
        return property2;
    }

    @Override
    public String hello() {
        return String.format("Hello AService, name:%s level:%s", name, level);
    }
}

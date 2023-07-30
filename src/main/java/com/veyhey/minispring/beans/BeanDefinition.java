package com.veyhey.minispring.beans;

import java.util.List;

public class BeanDefinition {
    private String id;
    private String className;
    private List<ArgumentValue> constructorArgValues;
    private List<ArgumentValue> propertyArgValues;

    public BeanDefinition(String id, String className,
                          List<ArgumentValue> constructorArgValues,
                          List<ArgumentValue> propertyArgValues) {
        this.id = id;
        this.className = className;
        this.constructorArgValues = constructorArgValues;
        this.propertyArgValues = propertyArgValues;
    }

    public String getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public List<ArgumentValue> getConstructorArgValues() {
        return constructorArgValues;
    }

    public List<ArgumentValue> getPropertyArgValues() {
        return propertyArgValues;
    }
}

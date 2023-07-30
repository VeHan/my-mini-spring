package com.veyhey.minispring.beans;

import java.util.List;

public class BeanDefinition {
    private String id;
    private Class<?> clazz;
    private List<ArgumentValue> constructorArgValues;
    private List<ArgumentValue> propertyArgValues;

    public BeanDefinition(String id, String className,
                          List<ArgumentValue> constructorArgValues,
                          List<ArgumentValue> propertyArgValues) throws ClassNotFoundException {
        this.id = id;
        this.clazz = Class.forName(className);
        this.constructorArgValues = constructorArgValues;
        this.propertyArgValues = propertyArgValues;
    }

    public String getId() {
        return id;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public List<ArgumentValue> getConstructorArgValues() {
        return constructorArgValues;
    }

    public List<ArgumentValue> getPropertyArgValues() {
        return propertyArgValues;
    }
}

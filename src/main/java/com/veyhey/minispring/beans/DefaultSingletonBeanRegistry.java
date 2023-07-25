package com.veyhey.minispring.beans;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final ConcurrentHashMap<String, Object> singletons = new ConcurrentHashMap<>();

    @Override
    public void registrySingleton(String name, Object singletonObject) {
        singletons.put(name, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return singletons.containsKey(beanName);
    }

    @Override
    public Set<String> getSingletonNames() {
        return singletons.keySet();
    }
}

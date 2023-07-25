package com.veyhey.minispring.beans;

import java.util.Set;

public interface SingletonBeanRegistry {

    void registrySingleton(String name, Object singletonObject);

    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    Set<String> getSingletonNames();
}

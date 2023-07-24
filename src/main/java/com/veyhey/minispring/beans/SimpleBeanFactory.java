package com.veyhey.minispring.beans;

import com.veyhey.minispring.exception.BeanException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class SimpleBeanFactory implements BeanFactory {

    private final Map<String, BeanDefinition> beanDefinitions = new HashMap<>();
    private final Map<String, Object> singletons = new HashMap<>();

    @Override
    public Object getBean(String name) throws BeanException {
        final var bean = singletons.get(name);
        if (bean != null) {
            return bean;
        }
        final var beanDefinition = beanDefinitions.get(name);
        if (beanDefinition == null) {
            throw new BeanException(String.format("bean %s not registered", name));
        }
        try {
            final Object instance = Class.forName(beanDefinition.getClassName())
                    .getDeclaredConstructor()
                    .newInstance();
            singletons.put(name, instance);
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            throw new BeanException(e.getMessage(), e);
        }
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        beanDefinitions.put(beanDefinition.getId(), beanDefinition);
    }
}

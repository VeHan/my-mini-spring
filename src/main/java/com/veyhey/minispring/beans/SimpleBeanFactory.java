package com.veyhey.minispring.beans;

import com.veyhey.minispring.exception.BeanException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    private final Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    @Override
    public Object getBean(String name) throws BeanException {
        final var bean = this.getSingleton(name);
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
            this.registrySingleton(name, instance);
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            throw new BeanException(e.getMessage(), e);
        }
    }

    @Override
    public void registerBean(String name, Object bean) {
        super.registrySingleton(name, bean);
    }


    @Override
    public boolean containsBean(String name) {
        return super.containsSingleton(name);
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanDefinition.getId(), beanDefinition);
    }

}

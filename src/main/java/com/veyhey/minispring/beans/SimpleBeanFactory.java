package com.veyhey.minispring.beans;

import com.veyhey.minispring.exception.BeanException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            final Class<?> clazz = Class.forName(beanDefinition.getClassName());
            final Object instance = clazz
                    .getDeclaredConstructor(getParamClassList(beanDefinition))
                    .newInstance(getParamValueList(beanDefinition));
            injectBySetter(instance, clazz, beanDefinition);
            this.registrySingleton(name, instance);
            return instance;
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException
                | ClassNotFoundException e) {
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

    private void injectBySetter(Object instance, Class<?> clazz, BeanDefinition beanDefinition)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, BeanException {
        for (ArgumentValue propertyArgValue : beanDefinition.getPropertyArgValues()) {
            final var propertyName = propertyArgValue.getName();
            final var setter = clazz.getDeclaredMethod(getSetterName(propertyName), propertyArgValue.getType());
            final var value = propertyArgValue.getValue() == null ?
                    this.getBean(propertyArgValue.getRef()) : propertyArgValue.getValue();
            setter.invoke(instance, value);
        }
    }

    private String getSetterName(String propertyName) {
        final var firstChar = String.valueOf(propertyName.charAt(0));
        return "set" + propertyName.replaceFirst(
                firstChar, firstChar.toUpperCase());
    }

    private Object[] getParamValueList(BeanDefinition beanDefinition) throws BeanException {
        List<Object> objects = new ArrayList<>(beanDefinition.getConstructorArgValues().size());
        for (ArgumentValue argumentValue : beanDefinition.getConstructorArgValues()) {
            if (argumentValue.getValue() != null) {
                objects.add(argumentValue.getValue());
                continue;
            }
            objects.add(getRefBean(argumentValue.getRef()));
        }
        return objects.toArray();
    }

    private Object getRefBean(String ref) throws BeanException {
        return this.getBean(ref);
    }

    private Class[] getParamClassList(BeanDefinition beanDefinition) {
        return beanDefinition.getConstructorArgValues().stream()
                .map(ArgumentValue::getType)
                .toArray(i -> new Class[beanDefinition.getConstructorArgValues().size()]);
    }


}

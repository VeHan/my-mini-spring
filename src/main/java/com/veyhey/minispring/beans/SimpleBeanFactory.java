package com.veyhey.minispring.beans;

import com.veyhey.minispring.exception.BeanException;
import com.veyhey.minispring.exception.ConstructorCircularDependencyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    private final Map<String, BeanDefinition> beanDefinitions = new HashMap<>();
    private final ConcurrentHashMap<String, Object> earlySingletons = new ConcurrentHashMap<>();
    private final Set<String> creatingSingletonNames = new HashSet<>();

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
        return doCreateBean(name, beanDefinition);
    }

    private Object doCreateBean(String name, BeanDefinition beanDefinition) throws BeanException {
        final Object instance = instance(beanDefinition);
        injectBySetter(instance, beanDefinition);
        this.registrySingleton(name, instance);
        return instance;
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

    private Object instance(BeanDefinition beanDefinition) throws BeanException {
        creatingSingletonNames.add(beanDefinition.getId());
        try {
            return beanDefinition.getClazz()
                    .getDeclaredConstructor(getParamClassList(beanDefinition))
                    .newInstance(getParamValueList(beanDefinition));
        }  catch (Exception e) {
            throw BeanException.wrapper(e);
        } finally {
            creatingSingletonNames.remove(beanDefinition.getId());
        }
    }

    private void injectBySetter(Object instance, BeanDefinition beanDefinition)
            throws BeanException {
        earlySingletons.put(beanDefinition.getId(), instance);
        try {
            Class<?> clazz = beanDefinition.getClazz();
            for (ArgumentValue propertyArgValue : beanDefinition.getPropertyArgValues()) {
                final var propertyName = propertyArgValue.getName();
                final var setter = clazz.getDeclaredMethod(getSetterName(propertyName), propertyArgValue.getType());
                final Object value;
                try {
                    value = getArgValue(propertyArgValue);
                } catch (ConstructorCircularDependencyException e) {
                    throw new BeanException(String.format("Constructor Circular Dependency Found: %s -> %s", beanDefinition.getId(),
                            e.getRef()), e);
                }
                setter.invoke(instance, value);
            }
        } catch (Exception e) {
            throw BeanException.wrapper(e);
        } finally {
            earlySingletons.remove(beanDefinition.getId());
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
            try {
                objects.add(getArgValue(argumentValue));
            } catch (ConstructorCircularDependencyException e) {
                throw new BeanException(String.format("Constructor Circular Dependency Found: %s -> %s", beanDefinition.getId(),
                        argumentValue.getRef()), e);
            }
        }
        return objects.toArray();
    }

    private Class[] getParamClassList(BeanDefinition beanDefinition) {
        return beanDefinition.getConstructorArgValues().stream()
                .map(ArgumentValue::getType)
                .toArray(i -> new Class[beanDefinition.getConstructorArgValues().size()]);
    }

    private Object getArgValue(ArgumentValue propertyArgValue) throws BeanException, ConstructorCircularDependencyException {
        if (propertyArgValue.getValue() != null) {
            return propertyArgValue.getValue();
        }
        final var ref = propertyArgValue.getRef();
        if (creatingSingletonNames.contains(ref)) {
            throw new ConstructorCircularDependencyException(ref);
        }
        if (earlySingletons.containsKey(ref)) {
            return earlySingletons.get(ref);
        }
        return this.getBean(ref);
    }

}

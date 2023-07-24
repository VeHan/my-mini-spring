package com.veyhey.minispring.beans;

import com.veyhey.minispring.exception.BeanException;

public interface BeanFactory {

    Object getBean(String name) throws BeanException;
    void registerBeanDefinition(BeanDefinition beanDefinition);
}

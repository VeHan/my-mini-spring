package com.veyhey.minispring.context;

import com.veyhey.minispring.beans.ClassPathXmlResource;
import com.veyhey.minispring.beans.SimpleBeanFactory;
import com.veyhey.minispring.beans.XmlBeanDefinitionReader;
import com.veyhey.minispring.exception.BeanException;
import com.veyhey.minispring.exception.ResourceInitException;

import java.io.IOException;

public class ClassPathXmlApplicationContext {

    private final SimpleBeanFactory beanFactory = new SimpleBeanFactory();

    public ClassPathXmlApplicationContext(String xmlPath) throws IOException, ResourceInitException {
        final var resource = new ClassPathXmlResource(xmlPath);
        new XmlBeanDefinitionReader(beanFactory).loadResource(resource);
    }

    public Object getBean(String name) throws BeanException {
        return beanFactory.getBean(name);
    }

    public void  registerBean(String name, Object bean){
        beanFactory.registerBean(name, bean);
    }

    public boolean containsBean(String name){
        return beanFactory.containsBean(name);
    }

}

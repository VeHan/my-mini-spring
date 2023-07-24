package com.veyhey.minispring.context;

import com.veyhey.minispring.beans.FilePathXmlResource;
import com.veyhey.minispring.beans.SimpleBeanFactory;
import com.veyhey.minispring.beans.XmlBeanDefinitionReader;
import com.veyhey.minispring.exception.BeanException;
import com.veyhey.minispring.exception.ResourceInitException;

import java.io.File;
import java.io.IOException;

public class FilePathXmlApplicationContext {

    private final SimpleBeanFactory beanFactory = new SimpleBeanFactory();

    public FilePathXmlApplicationContext(String xmlPath) throws IOException, ResourceInitException {
        final var resource = new FilePathXmlResource(new File(xmlPath));
        new XmlBeanDefinitionReader(beanFactory).loadResource(resource);
    }

    public Object getBean(String name) throws BeanException {
        return beanFactory.getBean(name);
    }

}

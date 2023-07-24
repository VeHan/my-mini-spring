package com.veyhey.minispring.beans;


import org.dom4j.Element;

public class XmlBeanDefinitionReader {

    private final BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadResource(XmlResource xmlResource) {
        while (xmlResource.hasNext()) {
            Element element = xmlResource.next();

            beanFactory.registerBeanDefinition(
                    new BeanDefinition(
                            element.attributeValue("id"),
                            element.attributeValue("class")
                    )
            );
        }
    }
}

package com.veyhey.minispring.beans;


import com.veyhey.minispring.exception.XMLParseException;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class XmlBeanDefinitionReader {

    private final SimpleBeanFactory beanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadResource(XmlResource xmlResource) throws XMLParseException {
        while (xmlResource.hasNext()) {
            Element element = xmlResource.next();

            try {
                beanFactory.registerBeanDefinition(
                        new BeanDefinition(
                                element.attributeValue("id"),
                                element.attributeValue("class"),
                                parseConstructorArgs(element),
                                parsePropertyArgs(element)
                        )
                );
            } catch (ClassNotFoundException e) {
                throw new XMLParseException(e);
            }
        }
    }

    private List<ArgumentValue> parseConstructorArgs(Element element) throws XMLParseException {
        return parseArgs(element, "constructor-arg");
    }

    private List<ArgumentValue> parsePropertyArgs(Element element) throws XMLParseException {
        return parseArgs(element, "property");
    }

    private List<ArgumentValue> parseArgs(Element element, String property) throws XMLParseException {
        final List<Element> constructorArgs = element.elements(property);
        final var argumentValues = new ArrayList<ArgumentValue>();
        for (Element constructorArg : constructorArgs) {
            argumentValues.add(new ArgumentValue(
                    constructorArg.attributeValue("type"),
                    constructorArg.attributeValue("name"),
                    constructorArg.attributeValue("value"),
                    constructorArg.attributeValue("ref")
            ));
        }
        return argumentValues;
    }
}

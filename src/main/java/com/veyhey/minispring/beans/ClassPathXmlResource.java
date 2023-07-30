package com.veyhey.minispring.beans;


import com.veyhey.minispring.exception.XMLParseException;

import java.io.IOException;

public class ClassPathXmlResource extends XmlResource{

    public ClassPathXmlResource(String xmlPath) throws IOException, XMLParseException {
        super(ClassPathXmlResource.class.getClassLoader()
                .getResource(xmlPath)
                .openStream());
    }
}

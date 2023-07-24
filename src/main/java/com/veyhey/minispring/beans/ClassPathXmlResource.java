package com.veyhey.minispring.beans;


import com.veyhey.minispring.exception.ResourceInitException;

import java.io.IOException;

public class ClassPathXmlResource extends XmlResource{

    public ClassPathXmlResource(String xmlPath) throws ResourceInitException, IOException {
        super(ClassPathXmlResource.class.getClassLoader()
                .getResource(xmlPath)
                .openStream());
    }
}

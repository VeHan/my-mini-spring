package com.veyhey.minispring.beans;

import com.veyhey.minispring.exception.ResourceInitException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FilePathXmlResource extends XmlResource{

    public FilePathXmlResource(File xmlFile) throws FileNotFoundException, ResourceInitException {
        super(new FileInputStream(xmlFile));
    }
}

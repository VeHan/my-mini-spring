package com.veyhey.minispring.beans;

import com.veyhey.minispring.exception.ResourceInitException;
import com.veyhey.minispring.exception.XMLParseException;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Iterator;

public abstract class XmlResource implements Resource<Element> {

    private final Iterator elementIterator;

    protected XmlResource(InputStream inputStream) throws XMLParseException {
        try {
            this.elementIterator = this.parseXml(inputStream);
        } catch (DocumentException e) {
            throw new XMLParseException(e);
        }
    }

    private Iterator parseXml(InputStream inputStream) throws XMLParseException, DocumentException {
        SAXReader saxReader = new SAXReader();

        final var document = saxReader.read(inputStream);
        final var rootElement = document.getRootElement();
        if (!rootElement.getName().equals("beans")) {
            throw new XMLParseException("root element name should be beans");
        }
        return rootElement.elementIterator("bean");
    }

    @Override
    public boolean hasNext() {
        return elementIterator.hasNext();
    }

    @Override
    public Element next() {
        return (Element) elementIterator.next();
    }
}

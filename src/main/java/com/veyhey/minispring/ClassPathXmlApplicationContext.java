package com.veyhey.minispring;

import com.veyhey.minispring.exception.XMLException;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassPathXmlApplicationContext {

    private final List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private final Map<String, Object> singletons = new HashMap<>();

    public ClassPathXmlApplicationContext(String xmlPath) throws Exception {
        this.parseXml(xmlPath);
        this.initBeans();
    }

    public Object getBean(String id) {
        return singletons.get(id);
    }

    private void parseXml(String xmlPath) throws DocumentException, XMLException {
        SAXReader saxReader = new SAXReader();

        final var document = saxReader.read(this.getClass().getClassLoader().getResource(xmlPath));
        final var rootElement = document.getRootElement();
        if (!rootElement.getName().equals("beans")) {
            throw new XMLException("root element name should be beans");
        }
        beanDefinitions.addAll((List<BeanDefinition>) rootElement.elements("bean")
                .stream()
                .map(o -> {
                    Element element = (Element) o;
                    return new BeanDefinition(
                            element.attributeValue("id"),
                            element.attributeValue("class")
                    );
                })
                .collect(Collectors.toList()));
    }

    private void initBeans() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            singletons.put(
                    beanDefinition.getId(),
                    Class.forName(beanDefinition.getClassName())
                            .getDeclaredConstructor()
                            .newInstance()
            );
        }
    }


}

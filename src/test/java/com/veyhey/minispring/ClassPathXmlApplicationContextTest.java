package com.veyhey.minispring;

import com.veyhey.minispring.context.ClassPathXmlApplicationContext;
import com.veyhey.minispring.exception.BeanException;
import com.veyhey.minispring.service.AService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClassPathXmlApplicationContextTest {

    @Test
    void should_get_aService() throws Exception {
        final var classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
        final var aService = (AService) classPathXmlApplicationContext.getBean("aService");
        aService.hello();
    }

    @Test
    void should_throw_BeanException_get_aServiceNoFound() throws Exception {
        final var classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Assertions.assertThrows(BeanException.class,
                () -> classPathXmlApplicationContext.getBean("aServiceNoFound"));
    }
}

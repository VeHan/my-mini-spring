package com.veyhey.minispring;

import com.veyhey.minispring.service.AService;
import org.junit.jupiter.api.Test;

public class ClassPathXmlApplicationContextTest {


    @Test
    void should_get_aService() throws Exception {
        final var classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
        final var aService = (AService) classPathXmlApplicationContext.getBean("aService");
        aService.hello();
    }
}

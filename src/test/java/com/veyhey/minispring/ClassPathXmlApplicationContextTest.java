package com.veyhey.minispring;

import com.veyhey.minispring.context.ClassPathXmlApplicationContext;
import com.veyhey.minispring.exception.BeanException;
import com.veyhey.minispring.service.AService;
import com.veyhey.minispring.service.BService;
import com.veyhey.minispring.service.CService;
import com.veyhey.minispring.service.impl.AServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClassPathXmlApplicationContextTest {

    static class Simple {
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

    static class Inject {
        @Test
        void should_get_aService_injected_by_constructor() throws Exception {
            final var classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
            final var aService = (AService) classPathXmlApplicationContext.getBean("aServiceByConstructor");
            Assertions.assertEquals("Hello AService, name:abc level:3", aService.hello());
        }

        @Test
        void should_get_aService_injected_by_setter() throws Exception {
            final var classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
            final var aService = (AServiceImpl) classPathXmlApplicationContext.getBean("aServiceBySetter");
            Assertions.assertEquals(1, aService.getProperty1());
            Assertions.assertEquals("property2", aService.getProperty2());
        }

        @Test
        void should_get_aService_injected_by_constructor_with_other_bean() throws Exception {
            final var classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
            final var bService = (BService) classPathXmlApplicationContext.getBean("bServiceByOtherBean");
            Assertions.assertNotNull(bService.getAService());
        }

        @Test
        void should_get_aService_injected_by_setter_with_other_bean() throws Exception {
            final var classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
            final var bService = (BService) classPathXmlApplicationContext.getBean("bServiceInjectBySetterWithOtherBean");
            Assertions.assertNotNull(bService.getAService());
        }

        @Test
        void should_get_cService_circular_dependency_with_dService() throws Exception {
            final var classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
            final var cService = (CService) classPathXmlApplicationContext.getBean("cService");
            Assertions.assertNotNull(cService);
        }

        @Test
        void should_throw_exception_when_get_cService_constructor_circular_dependency_with_dService() throws Exception {
            final var classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
            final var cService = (CService) classPathXmlApplicationContext.getBean("cServiceByConstructor2");
            Assertions.assertNotNull(cService);
        }
    }


}

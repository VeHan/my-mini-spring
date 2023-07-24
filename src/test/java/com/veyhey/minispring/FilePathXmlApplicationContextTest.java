package com.veyhey.minispring;

import com.veyhey.minispring.context.FilePathXmlApplicationContext;
import com.veyhey.minispring.service.AService;
import org.junit.jupiter.api.Test;

public class FilePathXmlApplicationContextTest {

    @Test
    void should_get_aService() throws Exception {
        final var xmlPath = FilePathXmlApplicationContext.class
                .getClassLoader()
                .getResource("beans.xml")
                .getPath();
        final var filePathXmlApplicationContext = new FilePathXmlApplicationContext(xmlPath);
        final var aService = (AService) filePathXmlApplicationContext.getBean("aService");
        aService.hello();
    }

}

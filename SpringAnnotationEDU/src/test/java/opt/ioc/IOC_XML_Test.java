package opt.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOC_XML_Test {

    @Test
    public void testXml() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            System.out.println("注入的Bean:"+beanName);
        }

    }
}

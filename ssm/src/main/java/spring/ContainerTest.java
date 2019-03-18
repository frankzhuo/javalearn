package spring;

import beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import carrot.service.UserService;

@Component
public class ContainerTest {

    @Autowired
    User u;

    public static void main(String[] args) {
//        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("beans.xml"));
//
//        Test test = (Test)beanFactory.getBean("test");
//
//        System.out.println(test);
//        System.out.println(test.foo);
//
//        ContainerTest containerTest = (ContainerTest)beanFactory.getBean("containerTest");
//        System.out.println(containerTest.u);
//        System.out.println(beanFactory.getBean("user"));

        ApplicationContext context =new ClassPathXmlApplicationContext("beans.xml");
//        ContainerTest containerTest1 = (ContainerTest)context.getBean("userService");
//        System.out.println(containerTest1.u);
//        System.out.println(context.getBean("beanLifeTest"));
//        System.out.println("ApplicationContext close!");
//        ((ClassPathXmlApplicationContext) context).close();

//        UserService userService = (UserService) beanFactory.getBean("userService");
//
//        System.out.println(userService);
        UserService userService = (UserService)context.getBean("userService");
        System.out.println(userService.getClass());
        userService.getUser();
        userService.printUser();
    }
}

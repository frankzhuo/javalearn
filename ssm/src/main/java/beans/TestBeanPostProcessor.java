package beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class TestBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("beanLifeTest")){
            System.out.println("05：TestBeanPostProcessor : postProcessBeforeInitialization "+ beanName);
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("beanLifeTest")){
            System.out.println("08：TestBeanPostProcessor : postProcessAfterInitialization "+ beanName);
        }
        return bean;
    }
}

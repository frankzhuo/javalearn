package beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContextAware;


public class BeanLife implements BeanNameAware,  BeanFactoryAware, InitializingBean, DisposableBean {

    private int i=1;
    {
        System.out.println("01: init carrot.bean instance");
    }
    public int getI() {
        return i;
    }


    public void setI(int i) {
        System.out.println("02: populate properties");
        this.i = i;
    }

    public void setBeanName(String name) {
        System.out.println("03: Myname is "+name);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("04: Get Instance "+beanFactory);
    }

    public void afterPropertiesSet() throws BeansException {
        System.out.println("06: afterPropertiesSet ");
    }

    public void initMethod(){
        System.out.println("07: init-method ");
    }

    public void destroy() throws BeansException {
        System.out.println("09: destroy ");
    }

    public void destroyMethod(){
        System.out.println("10: destroy-method ");
    }
}

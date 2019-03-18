package t5_frame;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyInvocationHandler implements InvocationHandler {
    private Object target; //被代理者。

    MyInvocationHandler() {
        super();
    }

    MyInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    //第一个参数是代理者，第二个是被执行的方法，第三个是执行该方法所需的参数。
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        //把我们想要通过代理者给被代理者追加的操作都写在invoke方法里面
        if("getName".equals(method.getName())){
            System.out.println("++++++before " + method.getName() + "++++++");
            Object result = method.invoke(target, args);//反射
            System.out.println("++++++after " + method.getName() + "++++++");
            return result;
        }else{
            Object result = method.invoke(target, args);
            return result;
        }
    }

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        //创建一个InvocationHandler，描述我们希望代理者执行哪些操作
        InvocationHandler invocationHandler = new MyInvocationHandler(userService);
        //通过刚才创建的InvocationHandler，创建真正的代理者。第一个参数是类加载器，第二个参数是这个代理者实现哪些接口(与被代理者实现的是相同的接口)
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(),
                userService.getClass().getInterfaces(), invocationHandler);
        System.out.println(userServiceProxy.getName());
        System.out.println(userServiceProxy.getAge());
    }
//    https://blog.csdn.net/Dome_/article/details/82427386(原理)
}



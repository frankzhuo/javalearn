package t5_frame;

public class Topic5 {
    /*
        Spring Boot、Spring MVC
            1. Spring Boot+Spring MVC试用
                （1）创建maven项目

                （2）增加maven依赖

            2. Spring Boot能力、原理
                Spring Boot 推崇"约定大于配置"，避免了Spring冗杂的配置，为我们提供了常用的使用方式。

                (1)@SpringBootApplication
                   @SpringBootApplication 是三个注解的组合：
                    a.@Configuration
                        1.会把此类注册进入容器
                        2.此类内的@bean，会被注册进容器

                        听着像@Component  ->  https://blog.csdn.net/isea533/article/details/78072133  （二者区别）
                        （@Configuration注册的bean实际上是一个代理）

                        @Configuration的作用类似于xml配置中的beans标签

                    b.@EnableAutoConfiguration
                        Springboot根据你添加的jar包来配置你项目的默认配置
                        "EnableAutoConfiguration"实现了自动装配

                        自动装配原理（了解此原理，有助于我们安装组件，甚至我们编写自己的自动装配类）

                        扫描项目路径下所有的jar包，将jar包"META-INF/spring.factories"配置中配置类注入的容器中.

                        mybatis-spring-boot-autoconfigure-1.3.2.jar!/META-INF/spring.factories

                        org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
                        自动配置Mybatis相关类。

                        org.mybatis.spring.boot.autoconfigure.MybatisProperties
                        自动配置Mybatis相关的配置属性。

                        类似的可以查看DataSource。

                    c.@ComponentScan
                        会自动扫描该类同级包及其同级包的子包所有的注解。
                        （被@ComponentScan注解的类必须在一个包下）
                        与<context:component-scan />功能类似。

                (2)SpringApplication
                    SpringApplication的run方法

                    org.springframework.boot.SpringApplication.run(java.lang.String...)

                     1.加载Listeners，用于扩展SpringBoot应用
                       默认注册的是SpringApplicationRunListener实例 ——  EventPublishingRunListener
                       该listener用于发布SpringBoot各个生命周期对应的事件。

                     2.创建并配置当前应用将要使用的Environment

                     3.打印酷酷的商标Banner

                     4.创建ApplicationContext

                     5.初始化pplicationContext

                     6.调用ApplicationContext的refresh()方法，完成IoC容器可用的最后一道工序。
                       没错，就是前面所说的那个refresh方法。

                     7.查找当前context中是否注册有CommandLineRunner和ApplicationRunner，如果有则遍历执行它们。

                     8.执行所有SpringApplicationRunListener的started()方法。

                     9.执行所有的 ApplicationRunner 和 CommandLineRunner
                       https://blog.csdn.net/gebitan505/article/details/55047819

                     10.执行所有SpringApplicationRunListener的running()方法。

                     https://juejin.im/entry/5b94cf495188255c3a2d26f8(扩展阅读，有兴趣，有时间才看)



            3. Spring MVC
                web框架

                具体的开发细节较多，在此不一一赘述，虽说不可或缺，但面试时候不会问，一般使用时，依葫芦画瓢，知道他们的作用即可。

                讲一下web开发最基本的一个流程：
                    需求分析 -> 设计模块（包括数据库设计、业务流程设计）-> 代码开发 -> 测试 -> 验收

                一个项目最核心的是业务逻辑，但数据库的设计，也十分重要，它决定了业务逻辑的耦合结构，后续的可维护性。

                数据库设计中常用的方法有，表与表之间的关联，int字段标示状态等。

            4.Spring MVC原理
                https://blog.csdn.net/wdehxiang/article/details/77619512

                如图所示：
                    Spring MVC原理有一个核心组件：DispatchServlet
                    重要的组件还有：HandlerMapping，HandlerAdapter，Handler，ViewResolver

                    请求处理过程：一个请求进来后，由DispatchServlet接受，向HandlerMapping查询处理该请求的Handler，
                                通过HandlerAdapter调用对应的Handler处理该请求，返回ModelAndView，交给ViewResolver解析渲染，
                                最后将view返回给浏览器。

                    将流程化、固定的过程以组件的方式组合起来，让程序员只去实现变化的部分（业务逻辑）。

                思考，Spring MVC与线程的关系？

                题外话（会考）：
                    cookie、session
                    cookie是Http协议的一部分
                    session是我们自己实现的，session可以基于cookie实现。
                    具体说一说：
                      Cookie：
                        客户第一次请求时，服务端返回一个setCookie的响应头，其值是一个键值对形式的字符串，
                        客户再次请求时，会在请求头的cookie中包含此键值对，这就是cookie

                        我们看到cookie是不可靠的，可以认为篡改的。

                        演示"浏览器开发工具的使用方法"

                      Session：
                        客户请求时，会带上它的唯一标示，我们根据此标示，找到它对应的Session对象，
                        Session对象保存了该用户的信息，这样用户敏感的身份信息只会在服务端保存。

                        我们可以使用cookie携带身份信息。

                    cookie、Session区别（会考）
                        上面我们讲了他们的实现，具体的区别，下来自己看下，并"理解"记忆
                    https://www.cnblogs.com/shiyangxt/articles/1305506.html
     */

}

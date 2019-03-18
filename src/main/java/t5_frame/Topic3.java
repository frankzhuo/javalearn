package t5_frame;

public class Topic3 {
    /*
        Spring 概念多，体系复杂，但实际工作中最常用的功能却并不复杂，所以，我们在这里抓主要矛盾。

        Spring IOC
        （1）意义
            控制反转/依赖注入（Spring的作用，Spring IOC的作用）
            控制反转（IOC）：将对象的创建、初始化交给容器；
            依赖注入（DI）：将依赖的注入，也就是属性的赋值，交给容器

            IOC 和 DI，是从两个方面描述了同一问题。

            为什么这样做？
                解耦，
                    1.将应用的创建过程从代码中分离，我们的代码只关注业务，做到职责单一;
                    2.通过面向接口变成，便于我们替换实现类；
                    （开闭原则：对扩展开放，对修改关闭）

            https://www.cnblogs.com/Mr-Rocker/p/7721824.html

        （2）使用
               1.Spring 容器 BeanFactory vs ApplicationContext
                 BeanFactory
                    BeanFactory 是 Spring 的“心脏”，它就是 Spring IoC 容器的实际实现者。
                    Spring 使用 BeanFactory 来实例化、配置和管理 Bean。

                 ApplicationContext（应用上下文）
                    ApplicationContext 由BeanFactory派生而来，提供了更多面向实际应用的功能。
                    （1）更方便地加载资源
                    （2）提供了事件发布、监听机制
                    （3）支持国际化
                    （4）提供了父子上下文的概念

                 BeanFactory、ApplicationContext区别：
                    （1）BeanFactory在启动的时候不会去实例化Bean，从容器中拿Bean的时候才会去实例化；
                         ApplicationContext在启动的时候就把所有的Bean全部实例化了。它还可以为Bean配置lazy-init=true来让Bean延迟实例化；
                    （2）ApplicationContext功能更强大（如上），一般情况下我们都使用ApplicationContext

                2.几个概念：
                    Bean：任意一个Java对象，一个组件

                    Bean的注册：将Bean注册到容器中
                               有xml文件配置，注解两种方式

                    属性的注入：将属性（基本值，引用）注入给对象

                    Bean的作用域
                        实例的作用范围：
                        singleton（默认）：每次从容器中取的都是同一个实例（单例模式），
                        prototype：每次从容器中取的都是一个新的实例，
                        request、session、global session
                        https://baijiahao.baidu.com/s?id=1610298792072480906&wfr=spider&for=pc

                3.Bean创建的方式
                    构造函数
                    静态工厂方法
                    实例工厂方法
                    https://blog.csdn.net/magicianjun/article/details/78737840

                4.注入方式
                    基于构造函数
                    基于setter方法
                    基于注解（常用）

                5.自动装配
                    bytype
                         @Autowired是根据类型自动装配的，加上 @Qualifier 则可以根据byName的方式自动装配
                    byname
                         @Resource如有指定的name属性，先按该属性进行byName方式查找装配；其次再进行默认的byName方式进行装配；如果以上都不成功，则按byType的方式自动装配。都不成功，则报异常。

                    @Autowired较常用，@Qualifier也用过

                6.Bean的注册及Bean装配的定义： xml方式 vs 注解方式
                    https://www.cnblogs.com/yq12138/p/7210274.html
                    xml配置：
                        优点：
                            1. XML配置方式进一步降低了耦合，使得应用更加容易扩展，
                               即使对配置文件进一步修改也不需要工程进行修改和重新编译。
                            2. 在处理复杂的业务的时候，用XML配置应该更加好一些。
                               因为XML更加清晰的表明了各个对象之间的关系，各个业务类之间的调用。
                               同时spring的相关配置也能一目了然。
                        缺点：
                            1. 麻烦，配置较复杂，不宜阅读
                            2. 与代码脱离，不方便开发

                    注解配置：
                        优点：
                            1.开发效率高
                            2.不需要复杂的xml文件

                        缺点：
                            1.组件之间的关系没有xml清晰
                            2.一定程度上引入了耦合，不利于修改维护

                    总结：
                        简单固定的部分，我们往往倾向于使用"注解配置"
                        对于比较复杂的组件，我们可以使用xml文件配置。

                        另外，对于组件中的配置项，如数据库地址、密码等可变配置，我们通常采用property或yaml文件配置（@value）。

                7.xml配置
                    任何一个对象都可以通过bean标签，注册到Spring容器中。
                    对于Map、Set、List等集合，有简便的配置方式，用的时候去网上搜搜就好了。

                8.注解配置
                    默认情况下，Spring 容器中未打开注解装配。因此，您需要在使用它之前在 Spring 配置文件中启用它。
                        <beans>
                            <context:annotation-config/>
                            <!-- carrot.bean definitions go here -->
                        </beans>

                    常用注解：
                        组件类注解：
                            Spring配置文件中，要配置<context:component-scan>

                            @Component：向Spring容器注册一个普通的Bean

                            @Service：标注一个Service组件类

                            @Repository：标注一个DAO组件类

                            @Controller：标注一个Controller组件类

                            题外话：MVC架构

                        注入类注解：
                            @Autowired（BeanFactory不支持）
                                @Autowired是根据类型自动装配的

                            @Qualifier
                                @Autowired加上@Qualifier则可以根据byName的方式自动装配，@Qualifier不能单独使用。

                            @Resource
                                @Resource默认按照ByName自动注入

                        其它注解：
                            @Value
                                <context:property-placeholder location="classpath:jdbc.properties"/>

                                @Value("#{prop.file.uploadpath}")
	                            private String uploadPath;

                Spring的用法很多，很灵活，可能不同的团队也会有不同的习惯，在此不一一列举，我们需要理解的是其内部的原理。


        （3）IOC 容器原理 & 初始化步骤

            Spring提供了一个Bean容器，这个Bean容器可以将配置中的Bean实例化，放入容器并管理Bean之间的依赖关系，
            最后，Bean容器提供给我们按照一定规则获取Bean的能力。

            org.springframework.context.support.AbstractApplicationContext.refresh

            简而言之，我们可以将Bean容器的工作分为两部分：
              （1）Bean容器的初始化：包括资源文件的加载，BeanDefinition的解析，BeanDefinition的注册；
                    1. 加载
                        fileName -> Resource
                    2. 解析
                        Resource -> Document (一棵树)-> Elements -> BeanDefinitons
                    3. 注册
                        BeanDefiniton注册到 BeanDefinitionRegistry（DefaultListFactory）上
                        （注册在ConcurrentHashMap里，key是beanname，value是BeanDefinition）。

                    ApplicationContext的核心还是BeanFactory

                    可以以 ClassPathXmlApplicationContext 为例尝试阅读上述流程。
                    org.springframework.context.support.AbstractApplicationContext.refresh
                    入口函数：org.springframework.context.support.AbstractApplicationContext.obtainFreshBeanFactory

                    https://www.cnblogs.com/chenjunjie12321/p/6124649.html

              （2）Bean的装配：初始化Bean，装配Bean，放入容器，最后返回Bean；
                    org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization
                  Bean的生命周期
                    1.初始化bean（调用构造方法）
                    2.注入bean的属性
                    3.调用BeanPostProcessor的postProcessBeforeInitialization方法
                    4.调用afterPropertiesSet方法（InitializingBean）
                    5.调用init-method方法
                    6.调用BeanPostProcessor的postProcessAfterInitialization方法
                    7.调用destroy方法（DisposableBean）
                    8.调用destroy-method方法

                  侵入 和 非侵入

                  BeanPostProcessor对所有bean生效

                  https://www.cnblogs.com/kenshinobiy/p/4652008.html

                  BeanFactoryPostProcessor

     */

}

package t5_frame;

public class Topic4 {
    /*
        Spring AOP
        （1）意义
            AOP（Aspect Oriented Programming），面向切面编程。
            将公共的操作抽离到"切面"，比如：事务、权限验证。
            与继承相比，继承是纵向的，切面是横向的，切面更灵活。
            类似的，AOP将相同的处理逻辑独立出来，对代码进行解耦，提高代码重用性，方便开发、维护。

        （2）基本概念
            Aspect（切面）：
                Aspect 声明类似于 Java 中的类声明，在 Aspect 中会包含着一些 "Pointcut" 以及相应的 "Advice"。
            Joint point（连接点）：
                表示在"程序中明确定义的点"，典型的包括方法调用，对类成员的访问以及异常处理程序块的执行等等，
                它自身还可以嵌套其它 joint point。
            Pointcut（切点）：
                表示一组 joint point，这些 joint point 或是通过逻辑关系组合起来，
                或是通过通配、正则表达式等方式集中起来，它定义了相应的 Advice 将要发生的地方。
            Advice（增强）：
                Advice 是在 Pointcut 里面定义的程序，定义切面具体要做的操作，
                它通过 before、after 和 around 来区别是在每个 joint point 之前、之后还是环绕执行。
            Target（目标对象）：
                织入 Advice 的目标对象。
            Weaving（织入）：
                将 Aspect 和其他对象连接起来, 并创建 Adviced object 的过程

            https://www.cnblogs.com/liuruowang/p/5711563.html

         (3)使用步骤
            1. 启用@AsjectJ支持：
                <aop:aspectj-autoproxy />
                注意  xmlns:aop="http://www.springframework.org/schema/aop"

            2.完成切面（连接点，增强）的业务逻辑：

            3.定义AOP组件：
                定义Advie（增强）和Pointcut（切点）形成Aspect（切面）。
                Pointcut：
                    @Pointcut

                Advie：
                    @Before
                    @After
                    @AfterReturning
                    @AfterThrowing
                    @Around

                    注意 连接点（JoinPoint）的处理

                https://www.cnblogs.com/liuruowang/p/5711563.html（例子）
                http://blog.51cto.com/5914679/2092253（如何定义切点）



        （4）原理
            a.动态代理模式
                代理模式：为其他对象提供一种代理以控制对这个对象的访问。

                代理模式结构图如图。

                代理模式存在"静态代理"、"动态代理"。
                静态代理可以通过继承来实现，编译期，代理类就已经确定，所以是静态的。

                https://www.cnblogs.com/daniels/p/8242592.html

                动态代理：
                    代理类在运行时才会确定，故称之为动态代理。
                    动态代理有2种实现方式：
                        jdk动态代理：基于反射基于接口实现；生成代理类更快。
                        cglib：基于asm（动态改变字节码），基于继承实现；执行更快。

                        https://blog.csdn.net/happy_wu/article/details/78842371

                    jdk动态代理实现：
                        例子

            b.AOP原理
                核心实现：
                    ProxyFactoryBean
                    如图，可以看到AOP代理类的生成过程，
                    最终会由JdkDynamicAopProxy或者CglibProxyFactory的getProxy方法生成动态代理实例

                    Advice数组会存在一个list中，在执行一个方法时，会查看Advisor（切点+增强，类似aspect，aspect不包括切入方式）列表，
                    查看每个Advisor中的增强是否该执行，并处理。

                    https://www.jianshu.com/p/40f79da0cdef

        （5）事务管理器的实现
             声明式（代理实现）：
                注解方式：
                    @Transactional

                xml配置：

             编程式：
                TransactionTemplate
                    TransactionTemplate 实例的execute方法传入 TransactionCallback （接口） 实例。

            commit、rollback场景：
                方法内没有任何没catch的异常，事务commit
                方法内有没catch的异常，事务rollback

             原理：

                 JDBC事务实现：
                    try{
                         con.setAutoCommit(false);//开启事务
                         ......
                         con.commit();//try的最后提交事务
                    } catch（） {
                        con.rollback();//回滚事务
                    }

                org.springframework.jdbc.datasource.DataSourceTransactionManager.doBegin （设置autocommit）

             思考：
                1.代理实现事务有什么问题没有？(AOP需要注意的问题,TransactionTemplate解决)
                2.Datasource是线程安全的吗？ ThreadLocal来保存这个connection

             事务的传播特性（考点）
                一个事务方法内部调用另一个事务方法时的处理。
                根据业务场景来选择。


        （6）AOP需要注意的问题
                Spring AOP能生效是因为，我们是从容器中取出来的代理类实例，通过代理实例调用方法，调用的实际是代理方法。
                如果在本类实例的方法中调用本实例的另一个方法，则不会经过代理。

     */
}

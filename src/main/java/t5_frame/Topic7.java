package t5_frame;

public class Topic7 {
    /*
      实践指南

        1. Spring Boot web项目创建流程：
            1.创建一个maven项目

            2.pom文件中加入依赖：
                spring-boot-starter-web
              (此时，包含了基本的Spring组件以及Spring MVC，可以开发一个基于Sping MVC的web项目了)

            3.加入我们需要的组件（以 Mybatis 为例，其它组件的引入方式是类似的）
                （1）添加依赖
                    mybatis-spring-boot-starter，使用的"数据库加载器依赖"

                    扩展：mybatis-spring-boot-starter这个组件的加载过程

                        mybatis-spring-boot-starter-1.3.2.jar!/META-INF/spring.provides
                        MybatisAutoConfiguration
                            引入DataSource依赖，并在DataSource实例化之后实例化。

                （2）扫描mapper
                    @Configuration 类添加 @MapperScan
                    （实际上这就是原本xml配置文件 beans标签 下面的配置）

                （3）在resources文件夹下创建application.properties文件
                    这里会设置我们的配置信息
                    组件在初始化的过程中，会自动到这个地方寻找配置信息 ->  "约定大于配置"

                    思考：怎么知道这些配置项的名字？

             4. 配置文件的工程化（扩展）
                这是一个很工程的问题（可以理解为不影响功能，性能，但实际上对我们的开发效率，开发测试上线等过程中出错的概率影响很大）

                环境：
                    dev，test，sandbox，prod

                Spring Boot的解决方案：
                    profile
                    maven中也有同样的概念
                    在CICD系统中，我们可以使用脚本命令的方式固定在特定环境下的profile。

                微服务体系下的配置文件
                    Consul，Spring Config，gitlab等

         2. Mybatis使用
            （1）配置SqlSessionFactoryBean
            （2）配置mapper位置
                构建sql方式（体现了mybatis的灵活性）
                1.xml形式
                2.注解形式
                3.使用example构建sql

            多数据源mybatis配置要点：
            （1）配置数据源
            （2）根据数据源配置事务管理器
            （3）根据数据源配置SqlSessionFactory

          3.Spring MVC的使用
            定义Controller类，装入容器，定义接口方法。

     */
}

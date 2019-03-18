package t5_frame;

public class Topic6 {
    /*
        Mybatis及设计模式，JDK与设计模式

        1.主要结构
          宏观上，Mybatis由以下四个主要组件：
            (1)SqlSessionFactoryBuilder
              SqlSessionFactoryBuilder采用构造者模式，根据xml配置文件得到Configuration对象（包含所有配置信息），
              生成SqlSessionFactory。SqlSessionFactoryBuilder只是一个构造器，
              用于隔离SqlSessionFactory的创建和使用，封装屏蔽复杂的构建过程。

            (2)SqlSessionFactory
              SqlSessionFactory用于生产SqlSession。

            (3)SqlSession
               SqlSession从SQL Mapper获取sql语句，并执行，最后返回sql执行的结果。

            (4)SQLMapper
              用于描述结构化的SQL语句，将SQL语句抽象为方法，并定义入参、结果，以及他们和POJO（Plain Ordinary Java Object）的映射关系。

        2.SqlSessionFactoryBuilder、SqlSessionFactory、SqlSession的生命周期
            SqlSessionFactoryBuilder用于创建SqlSessionFactory，之后就可以被丢弃，只使用一次。

            SqlSessionFactory用于创建SqlSession，一直存在在应用中，每一个数据源对应一个SqlSessionFactory，它的生命周期是应用范围内的。

            SqlSession的生命周期是方法/请求范围内，每个SqlSession对应一个JDBC连接，用于完成JDBC操作，
            SqlSession并不是线程安全的，所以SqlSession的作用范围应该局限于一个HTTP请求内。

        3.SqlSession核心组件
            SqlSession是Mybatis完成SQL操作的核心组件，是一个门面类，提供了统一的使用接口（屏蔽内部组件的复杂性）。
            实际工作由Executor、StatementHandler、ParameterHandler、ResultSetHandler完成。
            Configuration是在SqlSessionFactoryBuilder中生成的，由SqlSessionFactory传递给每一个生成的SqlSession。
            Configuration包含了该数据源在Mybatis中的所有配置信息，其中就包括SQL Statement的Map。
            Executor是SQL真正的执行者，它由Transaction（包含JDBC Connection），localCache等


        4.Executor
            Mapper中的语句实际上是由Executor执行的。
            Mybatis中，Executor是基础接口，BaseExecutor采用模版模式，是定义了CRUD方法的抽象类，
            基础操作（具体的更新、查询等）由Simple、Reuse、Batch三种Executor实现，CachingExecutor是实现了缓存功能的装饰器。
            1. SimpleExecutor
                默认的Executor，提供基本的update、query操作，没有过多的封装，没执行一次，生成一个Statement，用完就立即销毁；
            2. ReuseExecutor
                ReuseExecutor的实现其实和SimpleExecutor的类似，只不过内部维护了一个map来缓存statement。
                因为不同的sqlSession肯定有不同的executor，所以不同的executor即使有map缓存也没有作用。
                所以只有在同一个sqlSession的时候ReuseExecutor才有作用
                （在spring事务中可以使用，因为事务中是用的同一个sqlSession）。其他时候使用和SimpleExecutor无差别。

            3. BatchExecutor
                BatchExecutor的设计主要是用于做批量更新操作的。其底层会调用Statement的executeBatch()方法实现批量操作。

                Executor使用：
                默认情况下Mybatis的全局配置cachingEnabled=”true”，
                这就意味着默认情况下我们就会使用一个CachingExecutor来包装我们真正使用的Executor；
                一般的包括Spring集成，我们会使用默认的Executor。

            Executor的主要作用：
            通过Configuration来构建StatementHandler，然后借助StatementHandler
            1. 生成JDBC Statement，并对其进行预处理
            2. 执行sql操作
            3. 将结果合适的返回

        5.StatementHandler、ParameterHandler、ResultSetHandler
            我们知道JDBC分5步：加载驱动程序、获得连接、创建Statement对象（JDBC Statement用于执行一条SQL语句，并返回执行结果。），执行SQL，处理结果。后三步在Mybatis中由StatementHandler完成。（会考）
            Executor通过Configuration创建RoutingStatementHandler，RoutingStatementHandler会根据MappedStatement的具体类型的到真正的StatementHandler实例，
            并将操作委托给该实例，默认地，StatementHandler类型为PreparedStatementHandler（也就是说我们的SQL默认是通过PreparedStatement提交到JDBC，
            (下面是考点)
            不过要使用#，而不是$（直接拼接字符串，有sql注入的风险），但对于表名则需要使用$，进行字符串拼接，此时要注意代码层面的安全控制）。
            StatementHandler也是采用了模版模式。
            StatementHandler中借助ParameterHandler对参数进行处理，借助ResultSetHandler对返回值进行处理。

        6. Mybatis的缓存处理
            Mybatis有两级缓存：
                1. 一级缓存：存储作用域为SqlSession，默认开启；
                2. 二级缓存：存储作用域为Mapper，默认不开启，并且可以自定义缓存实现。

            Mybatis两级缓存机制如下：
               开启缓存后，Mybatis会试图先从二级缓存获取数据，再去查询一级缓存。

            一级缓存实现：
                BaseExecutor内部包含一个PerpetualCache（就是一个HashMap）实例localCache，
                一级缓存的生命周期与SqlSession相同。

            二级缓存实现：
                CachingExecutor装饰Executor实现类，并从MappedStatement中获取该Mapper对应的cache实例，
                而cache实例，也是由一系列装饰器实现，如：
                * SynchronizedCache: 同步Cache，实现比较简单，直接使用synchronized修饰方法。
                * LoggingCache: 日志功能，装饰类，用于记录缓存的命中率，如果开启了DEBUG模式，则会输出命中率日志。
                * SerializedCache: 序列化功能，将值序列化后存到缓存中。该功能用于缓存返回一份实例的Copy，用于保存线程安全。
                * LruCache: 采用了Lru算法的Cache实现，移除最近最少使用的key/value。（LRU，least recently used）
                * PerpetualCache: 作为为最基础的缓存类，底层实现比较简单，直接使用了HashMap。
                这样做一方面使代码结构更清晰明了，另一方面方便功能的组合装配。

            Mybatis缓存使用指南：
                一级缓存：
                1. Mybatis一级缓存的生命周期和SqlSession一致；
                2. Mybatis的缓存是一个粗粒度的缓存，没有更新缓存和缓存过期的概念，
                   同时只是使用了默认的hashmap，也没有做容量上的限定；
                3. Mybatis的一级缓存最大范围是SqlSession内部，有多个SqlSession或者分布式的环境下，
                   有操作数据库写的话，会引起脏数据，建议是把一级缓存的默认级别设定为Statement，即不使用一级缓存

                二级缓存：
                1. Mybatis的二级缓存相对于一级缓存来说，实现了SqlSession之间缓存数据的共享，
                   同时粒度更加的细，能够到Mapper级别，通过Cache接口实现类不同的组合，对Cache的可控性也更强。
                2. Mybatis在多表查询时，极大可能会出现脏数据，有设计上的缺陷，安全使用的条件比较苛刻。
                3. 在分布式环境下，由于默认的Mybatis Cache实现都是基于本地的，分布式环境下必然会出现读取到脏数据，
                   需要使用集中式缓存将Mybatis的Cache接口实现，有一定的开发成本，不如直接用Redis，Memcache实现业务上的缓存就好了。

                综上，在分布式环境下，Mybatis的缓存机制并不可靠。

                互联网公司更常用的是redis实现缓存




        7.Mybatis在Spring中的使用
            MyBatis-Spring 会帮助你将 MyBatis 代码无缝地整合到 Spring 中。
            其主要组成由：
                SqlSessionFactoryBean
                SqlSessionTemplate
                SqlSessionDaoSupport
                MapperFactoryBean
                MapperScannerConfigurer

            1. SqlSessionFactoryBean
                替代了Mybatis中SqlSessionFactoryBuilder的功能，实现FactoryBean接口，注入Datasource，为容器提供SqlSessionFactory。
            2. SqlSessionTemplate
                实现SqlSession接口，替代Mybatis中DefaultSqlSession。
            3. SqlSessionDaoSupport
                SqlSessionDaoSupport是一个抽象类，通过继承它可以得到SqlSession实例。
            4. MapperFactoryBean
                MapperFactoryBean是实现Mapper接口的代理类。
            5. MapperScannerConfigurer
                通过配置MapperScannerConfigurer，可以自动配置Mapper接口的实现类。

            使用时，配置 SqlSessionFactoryBean 和 MapperScannerConfigurer 即可。

            事务配置
                将之前传入SqlSessionFactoryBean的Datasource引用传入DataSourceTransactionManager，
                并注入容器即可。当有多个数据源时，使用@Transanctional注解时要指定对应的事务管理器。


        设计模式
             设计模式提问点：
                1.你知道哪些设计模式（说个最熟悉的四五种足够了）
                2.**设计模式，你说下？它有什么好处、缺点？它和XX设计模式的区别？
                3.设计模式的原则你说下？（Solid原则你说下？）
                4.你用过哪些设计模式？
                5.JDK中有哪些设计模式？
                6.单例模式系列问答。

             学习时，有时间的可以学会看设计模式的UML图


             设计模式六大原则（Solid原则，爱考）
             https://www.cnblogs.com/HouJiao/p/5459022.html
               Single Responsibility Principle　 : 单一职责原则

            　　Liskov Substitution Principle     : 里氏替换原则

            　　Dependence Inversion Principle ：依赖倒置原则

            　　Interface Segregation Principle  : 接口隔离原则

            　　Law of Demeter　　　　           : 迪米特法则(最少知道原则)

            　　Open Closed Principle               : 开闭原则

            ￼构造者模式

            ￼工厂模式（爱考）
                简单工厂模式（静态工厂）、工厂方法模式、抽象工厂模式，
                https://www.cnblogs.com/zailushang1996/p/8601808.html
                https://www.cnblogs.com/toutou/p/4899388.html
                简单工厂模式：
                    概念：在工厂类的静态方法中，根据参数判断产品类型，构建并返回
                    优点：
                        1.外界不必关系对象如何被创建，省去了逻辑判断等与业务无关的逻辑（全在工厂方法内实现）
                        2.方法名更加清晰
                    缺点：
                        1.违背了开闭原则，当新增产品类型时，需要改变原有的方法
                    使用场景：产品比较固定的场景
                    jdk中，Executors创建线程池，用名字来区分不同的产品类型

                工厂方法模式：
                    概念：定义工厂接口，由工厂接口的实现类来具体生成某一类型的产品
                        “工厂模式”是“简单工厂模式”的进一步抽象和推广。
                    优点：
                        克服了简单工厂违背开放-封闭原则的缺点
                        保留了封装对象创建过程的优点，
                        典型的解耦框架。高层模块只需要知道产品的抽象类，其他的实现类都不需要关心，符合迪米特法则，符合依赖倒置原则，符合里氏替换原则。
                        (降低客户端和工厂的耦合性)
                    缺点：
                        每增加一个产品，相应的也要增加一个子工厂，加大了额外的开发量。

                抽象工厂模式：
                    概念：定义抽象工厂接口，该工厂能够生成一组产品；实现该接口
                         抽象工厂模式和工厂方法模式作用域不同的场景。
                    优点：
                        1.抽象工厂模式隔离了具体类的生产，使得客户并不需要知道什么被创建。
                        2.当一个产品族中的多个对象被设计成一起工作时，它能保证客户端始终只使用同一个产品族中的对象。
                        3.增加新的具体工厂和产品族很方便，无须修改已有系统，符合“开闭原则”。

            ￼门面模式

            ￼装饰器模式

            ￼模版模式

            ￼动态代理模式（爱考）

             单例模式（爱考）

        JDK中的设计模式：

            https://www.cnblogs.com/zhousysu/p/5483862.html

            装饰器模式：（我最爱说的，要说明它和继承的优劣）
                （1）java.io包
                （2）java.util.Collections#synchronizedList(List)
            ... 很多，不一一列举了，掌握自己觉得理解最好的五六个即可，要理解！

     */
}

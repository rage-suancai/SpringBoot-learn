## 日志系统介绍
SpringBoot为我们提供了丰富的日志系统 它几乎是开箱即用的 我们在之学习SSM时 如果不配置日志 就会报错
但是到了SpringBoot阶段之后似乎这个问题就不见了 日志打印得也非常统一 这是为什么呢?

### 日志门面和日志实现
我们首先要区分一下 什么是日志门面(Facade) 什么是日志实现 我们之前学习的JUL实际上就是一种日志实现 我们可以直接使用JUL为我们提供的日志框架来规范化打印日志

而日志门面 如slf4j 是把不同的日志系统的实现进行了具体的抽象化 只提供了统一的日志使用接口 使用时只需要按照其提供的接口方法进行调用即可
由于它只是一个接口 并不是一个具体的可以直接单独使用的日志框架 所以最终日志的格式, 记录级别, 输出方式等都要通过接口绑定的具体的日志系统来实现
这些具体的日志系统就有log4j, logback, java.util.logging等 它们才实现了具体的日志系统的功能

日志门面和日志实现就像JDBC和数据库驱动一样 一个是画大饼的 一个是真的去做饼的

<img src="https://image.itbaima.net/markdown/2023/03/06/MGg1EHxtuvswV8d.png"/>

但是现在有一个问题就是 不同的框架可能使用了不同的日志框架 如果这个时候出现众多日志框架并存的情况 我们现在希望的是所有的框架一律使用日志门面(Slf4j)进行日志打印
这时该怎么去解决? 我们不可能将其他框架依赖的日志框架替换掉 直接更换为Slf4j吧 这样显然不现实

这时 可以采取类似于偷梁换柱的做法 只保留不同日志框架的接口和类定义等关键信息 而将实现全部定向为Slf4j调用
相当于有着和原有日志框架一样的外壳 对于其他框架来说依然可以使用对应的类进行操作 而具体如何执行 真正的内心已经是Slf4j的了

<img src="https://image.itbaima.net/markdown/2023/03/06/o1bMPITBcgetVYa.png"/>

所以 SpringBoot为了统一日志框架的使用 做了这些事情:
- 直接将其他依赖以前的日志框架剔除
- 导入对应日志框架的Slf4j中间包
- 导入自己官方指定的日志实现 并作为Slf4j的日志实现层

### 打印项目日志信息
SpringBoot使用的是slf4j作为日志门面 Logback(Logback是log4j框架的作者开发的新一代日志框架 它效率更高 能够适应诸多的运行环境 同时天然支持slf4j)作为日志实现 对应的依赖为:

```xml
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-logging</artifactId>
                    </dependency>
```

此依赖已经被包含了 所以我们如果需要打印日志 可以像这样:

```java
                    @ResponseBody
                    @GetMapping("/test")
                    public User test() {
    
                        Logger logger = LoggerFactory.getLogger(TestController.class);
                        logger.info("用户访问了一次测试数据");
                        return mapper.findUserById(1);
                        
                    }
```

因为我们使用了Lombok 所以直接一个注解也可以搞定哦:

```java
                    @Slf4j
                    @Controller
                    public class MainController {
                    
                      	@ResponseBody
                    	@GetMapping("/test")
                        public User test() {
                              
                            log.info("用户访问了一次测试数据");
                            return mapper.findUserById(1);
                            
                        }
                      
                      	...
```

日志级别从低到高分为`TRACE < DEBUG < INFO < WARN < ERROR < FATAL` SpringBoot默认只会打印INFO以上级别的信息 效果如下 也是使用同样的格式打印在控制台的:

<img src="https://image.itbaima.net/markdown/2023/07/16/HCZQndu2YPwINoS.png"/>

### 配置Logback日志
Logback官网: https://logback.qos.ch

和JUL一样 Logback也能实现定制化 我们可以编写对应的配置文件 SpringBoot推荐将配置文件名称命名为logback-spring.xml
表示这是SpringBoot下Logback专用的配置 可以使用SpringBoot的高级Proﬁle功能 它的内容类似于这样:


























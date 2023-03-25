多环境配置

在日常开发中 我们项目会有多个环境 例如开发环境(develop) 也就是我们研发过程中疯狂敲代码修BUG阶段 生产环境(production) 项目开发得差不多了 可以放在服务器上跑了
不同的环境下 可能我们的配置文件也存在不同 但是我们不可能切换环境的时候又去重新写一次配置文件 所以我们 所以我们可以将多个环境的配置文件提前写好 进行自由切换🔄

由于SpringBoot只会读取application.properties或是application.yaml文件
那么怎么才能实现自由切换呢? SpringBoot给我们提供了一种方式 我们可以通过配置文件指定:

                    spring:
                      profiles:
                        active: dev

接着我们分别创建两个环境的配置文件 application-dev.yaml和application-prod.yaml分别表示开发环境和生产环境的配置文件
比如开发环境我们使用的服务器端口为8080 而生产环境下可能就就需要设置为80或是443端口 那么这个时候就需要不同环境下的配置文件进行区分:

                    server:
                      port: 8080

                    server:
                      port: 80

这样我们就可以灵活切换生产环境和开发环境下的配置文件了

而SpringBoot自带的Logback日志系统也是支持多环境配置的 比如我们想在开发环境下输出日志到控制台
而生产环境下只需要输出到文件即可 这时就需要进行环境配置:

                    <springProfile name="dev">
                        <root level="INFO">
                            <appender-ref ref="CONSOLE"/>
                            <appender-ref ref="FILE"/>
                        </root>
                    </springProfile>
                    
                    <springProfile name="prod">
                        <root level="INFO">
                            <appender-ref ref="FILE"/>
                        </root>
                    </springProfile>

注意: springProfile是区分大小写的⭕

那如果我们希望生产环境中不要打包开发环境下的配置文件呢? 我们目前虽然可以切换开发环境 但是打包的时候依然是所有配置文件全部打包
这样总是感觉还欠缺一点完美 因此 打包的问题就只能找Maven解决了 Maven也可以设置多环境:

                    <!-- 分别设置开发, 生产环境 -->
                    <profiles>
                        <!-- 开发环境 -->
                        <profile>
                            <id>dev</id>
                            <activation>
                                <activeByDefault>true</activeByDefault>
                            </activation>
                            <properties>
                                <environment>dev</environment>
                            </properties>
                        </profile>
                        <!-- 生产环境 -->
                        <profile>
                            <id>prod</id>
                            <activation>
                                <activeByDefault>false</activeByDefault>
                            </activation>
                            <properties>
                                <environment>prod</environment>
                            </properties>
                        </profile>
                    </profiles>

接着 完美需要根据环境的不同 排除其他环境的配置文件:

                    <resources>
                        <!-- 排除配置文件 -->
                        <resource>
                            <directory>src/main/resources</directory>
                            <!-- 先排除所有的配置文件 -->
                            <excludes>
                                <!-- 使用通配符 当然可以定义多个exclude标签进行排除 -->
                                <exclude>application*.yaml</exclude>
                            </excludes>
                        </resource>
                    
                        <!-- 根据激活条件引入打包所需的配置和文件 -->
                        <resource>
                            <directory>src/main/resources</directory>
                            <!-- 引入所需环境的配置文件 -->
                            <filtering>true</filtering>
                            <includes>
                                <include>application.yaml</include>
                                <!-- 根据maven选择环境导入配置文件 -->
                                <include>application-${environment}.yml</include>
                            </includes>
                        </resource>
                    </resources>

接着 我们可以直接将Maven中的environment属性 传递给SpringBoot的配置文件 在构建时替换对应的值:

                    spring:
                      profiles:
                      active: '@environment@'  # 注意YAML配置文件需要加单引号 否则会报错

这样 根据我们Maven环境的切换 Spring的配置文件也会进行对应的切换

最后我们打开Maven栏目 就可以自由切换了 直接勾选即可 注意切换环境之后要重新加载一下Maven项目 不然不会生效

----

打包运行

现在我们的SpringBoot项目编写完成了 那么如何打包运行呢? 非常简单 只需要点击Maven生命周期中的package即可
它会自动将其打包为可以直接运行的jar包 第一次打包可能会花费一些时间下载部分依赖的源码一起打包进jar文件

我们发现在打包的过程中还会完整的将项目跑一遍进行测试 如果我们不想测试直接打包 可以手动使用以下命令:
                    
                    mvn package -DskipTests

打包后 我们会的得到一个名为java -jar springboot-study-0.0.1-SNAPSHOT.jar的文件 这时在CMD窗口中输命令:

                    java -jar springboot-study-0.0.1-SNAPSHOT.jar

输入后 可以看到我们的java项目成功运行起来了 如果手动关闭窗口会导致整个项目终止运行